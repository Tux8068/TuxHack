package me.tux.tuxhack.module.modules.misc;

import me.tux.tuxhack.command.Command;
import me.tux.tuxhack.event.events.DamageBlockEvent;
import me.tux.tuxhack.event.events.RenderEvent;
import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.util.GeometryMasks;
import me.tux.tuxhack.util.RenderUtil;
import me.tux.tuxhack.util.Timer;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.text.DecimalFormat;

public class InstantMine extends Module {

    private BlockPos renderBlock;
    private BlockPos lastBlock;
    private IBlockState blockState;
    private boolean breakable = false;

    private Timer timer = new Timer();
    private final Timer breakTimer = new Timer();
    private EnumFacing direction;

    public static float TPS = 20.0F;
    private long lastUpdate = -1L;
    private final float[] tpsCounts = new float[10];
    private final DecimalFormat format = new DecimalFormat("##.00#");

    public InstantMine(){
        super("InstantMine", "InstantMines blocks using packets", Category.MISC);
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
                    breakTimer.reset();
                }
            }
        }

        try {
            mc.playerController.blockHitDelay = 0;
        } catch (Exception e) {

        }

    }

    @Override
    public int onUpdate() {
        if(renderBlock != null) {
            if(breakTimer.passedMs(85)) {
                if(mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() == Items.DIAMOND_PICKAXE) return 0;
                    if (mc.world.getBlockState(renderBlock).getBlock() != Blocks.AIR) {
                        mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK,
                                renderBlock, direction));
                    }
            }
        }
        return 0;
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
            if(lastBlock != null && (event.getPos().x != lastBlock.x ||
                    event.getPos().y != lastBlock.y||
                    event.getPos().z != lastBlock.z)) {
                Command.sendRawMessage("Sending start packet");
                mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK,
                        event.getPos(), event.getDirection()));
            }
            mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK,
                    event.getPos(), event.getDirection()));
            mc.player.swingArm(EnumHand.MAIN_HAND);

            renderBlock = event.getPos();
            lastBlock = event.getPos();
            blockState = mc.world.getBlockState(event.getPos());
            breakable = false;
            direction = event.getDirection();
            breakTimer.reset();
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
