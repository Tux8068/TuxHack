package me.ka1.vulcan.module.modules.misc;

import me.ka1.vulcan.command.Command;
import me.ka1.vulcan.event.events.DamageBlockEvent;
import me.ka1.vulcan.event.events.PacketEvent;
import me.ka1.vulcan.event.events.RenderEvent;
import me.ka1.vulcan.module.Module;
import me.ka1.vulcan.util.GeometryMasks;
import me.ka1.vulcan.util.RenderUtil;
import me.ka1.vulcan.util.Timer;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.text.DecimalFormat;

public class PacketMine extends Module {

    private BlockPos renderBlock;
    private IBlockState blockState;
    private boolean breakable = false;

    private Timer timer = new Timer();

    public static float TPS = 20.0F;
    private long lastUpdate = -1L;
    private final float[] tpsCounts = new float[10];
    private final DecimalFormat format = new DecimalFormat("##.00#");

    public PacketMine(){
        super("PacketMine", "Mines blocks using packets", Category.Misc);
    }

    public static float getTpsFactor() { return 20.0F / TPS; }

    @Override
    public void onWorldRender(RenderEvent event) {
        if (renderBlock != null) {
            if(breakable) drawBlock(renderBlock, 0, 255, 0);
            else drawBlock(renderBlock, 255, 0, 0);
        }

        long currentTime = System.currentTimeMillis();
        if (this.lastUpdate == -1L) {
            this.lastUpdate = currentTime;
            return;
        }
        long timeDiff = currentTime - this.lastUpdate;
        float tickTime = (float)timeDiff / 20.0F;
        if (tickTime == 0.0F) {
            tickTime = 50.0F;
        }
        float tps = 1000.0F / tickTime;
        if (tps > 20.0F) {
            tps = 20.0F;
        }
        System.arraycopy(this.tpsCounts, 0, this.tpsCounts, 1, this.tpsCounts.length - 1);
        this.tpsCounts[0] = tps;
        double total = 0.0D;
        for (float f : this.tpsCounts) {
            total += f;
        }
        total /= this.tpsCounts.length;

        if (total > 20.0D) {
            total = 20.0D;
        }

        this.TPS = Float.parseFloat(this.format.format(total));
        this.lastUpdate = currentTime;

        if(renderBlock != null) {
            if(timer.passedMs((long) (2000.0F * getTpsFactor()))) {
                breakable = true;
                if(!mc.world.getBlockState(this.renderBlock).equals(this.blockState) || mc.world.getBlockState(this.renderBlock).getBlock() == Blocks.AIR) {
                    renderBlock = null;
                }
            }
        }

        try {
            mc.playerController.blockHitDelay = 0;
        } catch (Exception e) {

        }

    }

    private void drawBlock(BlockPos blockPos, int r, int g, int b) {
        Color color = new Color(r, g, b, 40);
        RenderUtil.prepare(GL11.GL_QUADS);
        RenderUtil.drawBox(blockPos, color.getRGB(), GeometryMasks.Quad.ALL);
        RenderUtil.release();
    }
    @EventHandler
    private final Listener<DamageBlockEvent> damageBlockEventListener = new Listener<>(event -> {
        if (canBreak(event.getPos())) {
            mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK,
                    event.getPos(), event.getDirection()));
            mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK,
                    event.getPos(), event.getDirection()));
            mc.player.swingArm(EnumHand.MAIN_HAND);

            renderBlock = event.getPos();
            blockState = mc.world.getBlockState(event.getPos());
            breakable = false;
            timer.reset();
            event.cancel();
        }
    });

    private boolean canBreak(BlockPos pos) {
        final IBlockState blockState = mc.world.getBlockState(pos);
        final Block block = blockState.getBlock();

        return block.getBlockHardness(blockState, mc.world, pos) != -1;
    }

}
