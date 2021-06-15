//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.tux.tuxhack.module.modules.misc;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import me.tux.tuxhack.util.RenderUtil;
import java.awt.Color;
import net.minecraft.init.Blocks;
import me.tux.tuxhack.event.events.RenderEvent;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Predicate;
import net.minecraft.util.EnumHand;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import me.zero.alpine.listener.EventHandler;
import me.tux.tuxhack.event.events.DamageBlockEvent;
import me.zero.alpine.listener.Listener;
import me.tux.tuxhack.setting.Setting;
import java.text.DecimalFormat;
import me.tux.tuxhack.util.Timer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import me.tux.tuxhack.module.Module;

public class PacketMine extends Module
{
    private BlockPos renderBlock;
    private IBlockState blockState;
    private boolean breakable;
    private Timer timer;
    public static float TPS;
    private long lastUpdate;
    private final float[] tpsCounts;
    private final DecimalFormat format;
    private Setting.Mode BlockColour;
    private Setting.Mode BlockPOPColour;
    @EventHandler
    private final Listener<DamageBlockEvent> damageBlockEventListener;
    
    public PacketMine() {
        super("PacketMine", "Mines blocks using packets", Category.MISC);
        this.breakable = false;
        this.timer = new Timer();
        this.lastUpdate = -1L;
        this.tpsCounts = new float[10];
        this.format = new DecimalFormat("##.00#");
        this.damageBlockEventListener = new Listener<DamageBlockEvent>(event -> {
            if (this.canBreak(event.getPos())) {
                PacketMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, event.getPos(), event.getDirection()));
                PacketMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.getPos(), event.getDirection()));
                PacketMine.mc.player.swingArm(EnumHand.MAIN_HAND);
                this.renderBlock = event.getPos();
                this.blockState = PacketMine.mc.world.getBlockState(event.getPos());
                this.breakable = false;
                this.timer.reset();
                event.cancel();
            }
        }, (Predicate<DamageBlockEvent>[])new Predicate[0]);
    }
    
    public static float getTpsFactor() {
        return 20.0f / PacketMine.TPS;
    }
    
    @Override
    public boolean setup() {
        final List<String> modes = new ArrayList<String>();
        modes.add("blue");
        modes.add("red");
        modes.add("green");
        modes.add("yellow");
        modes.add("purple");
        modes.add("pink");
        modes.add("white");
        modes.add("black");
        this.BlockColour = this.registerMode("StartBreaking", "Break", modes, modes.get(1));
        this.BlockPOPColour = this.registerMode("EndBreaking", "Break", modes, modes.get(1));
        return false;
    }
    
    @Override
    public void onWorldRender(final RenderEvent event) {
        if (this.renderBlock != null) {
            if (this.breakable) {
                if (this.BlockPOPColour.getValue().equals("blue")) {
                    this.drawBlock(this.renderBlock, 0, 174, 255);
                }
                if (this.BlockPOPColour.getValue().equals("red")) {
                    this.drawBlock(this.renderBlock, 255, 0, 0);
                }
                if (this.BlockPOPColour.getValue().equals("green")) {
                    this.drawBlock(this.renderBlock, 0, 255, 0);
                }
                if (this.BlockPOPColour.getValue().equals("yellow")) {
                    this.drawBlock(this.renderBlock, 255, 179, 0);
                }
                if (this.BlockPOPColour.getValue().equals("purple")) {
                    this.drawBlock(this.renderBlock, 119, 0, 255);
                }
                if (this.BlockPOPColour.getValue().equals("pink")) {
                    this.drawBlock(this.renderBlock, 255, 0, 255);
                }
                if (this.BlockPOPColour.getValue().equals("white")) {
                    this.drawBlock(this.renderBlock, 255, 255, 255);
                }
                if (this.BlockPOPColour.getValue().equals("black")) {
                    this.drawBlock(this.renderBlock, 0, 0, 0);
                }
            }
            else {
                if (this.BlockColour.getValue().equals("blue")) {
                    this.drawBlock(this.renderBlock, 0, 174, 255);
                }
                if (this.BlockColour.getValue().equals("red")) {
                    this.drawBlock(this.renderBlock, 255, 0, 0);
                }
                if (this.BlockColour.getValue().equals("green")) {
                    this.drawBlock(this.renderBlock, 0, 255, 0);
                }
                if (this.BlockColour.getValue().equals("yellow")) {
                    this.drawBlock(this.renderBlock, 255, 179, 0);
                }
                if (this.BlockColour.getValue().equals("purple")) {
                    this.drawBlock(this.renderBlock, 119, 0, 255);
                }
                if (this.BlockColour.getValue().equals("pink")) {
                    this.drawBlock(this.renderBlock, 255, 0, 255);
                }
                if (this.BlockColour.getValue().equals("white")) {
                    this.drawBlock(this.renderBlock, 255, 255, 255);
                }
                if (this.BlockColour.getValue().equals("black")) {
                    this.drawBlock(this.renderBlock, 0, 0, 0);
                }
            }
        }
        final long currentTime = System.currentTimeMillis();
        if (this.lastUpdate == -1L) {
            this.lastUpdate = currentTime;
            return;
        }
        final long timeDiff = currentTime - this.lastUpdate;
        float tickTime = timeDiff / 20.0f;
        if (tickTime == 0.0f) {
            tickTime = 50.0f;
        }
        float tps = 1000.0f / tickTime;
        if (tps > 20.0f) {
            tps = 20.0f;
        }
        System.arraycopy(this.tpsCounts, 0, this.tpsCounts, 1, this.tpsCounts.length - 1);
        this.tpsCounts[0] = tps;
        double total = 0.0;
        for (final float f : this.tpsCounts) {
            total += f;
        }
        total /= this.tpsCounts.length;
        if (total > 20.0) {
            total = 20.0;
        }
        PacketMine.TPS = Float.parseFloat(this.format.format(total));
        this.lastUpdate = currentTime;
        if (this.renderBlock != null && this.timer.passedMs((long)(2000.0f * getTpsFactor()))) {
            this.breakable = true;
            if (!PacketMine.mc.world.getBlockState(this.renderBlock).equals(this.blockState) || PacketMine.mc.world.getBlockState(this.renderBlock).getBlock() == Blocks.AIR) {
                this.renderBlock = null;
            }
        }
        try {
            PacketMine.mc.playerController.blockHitDelay = 0;
        }
        catch (Exception ex) {}
    }
    
    private void drawBlock(final BlockPos blockPos, final int r, final int g, final int b) {
        final Color color = new Color(r, g, b, 40);
        RenderUtil.prepare(7);
        RenderUtil.drawBox(blockPos, color.getRGB(), 63);
        RenderUtil.release();
    }
    
    private boolean canBreak(final BlockPos pos) {
        final IBlockState blockState = PacketMine.mc.world.getBlockState(pos);
        final Block block = blockState.getBlock();
        return block.getBlockHardness(blockState, (World)PacketMine.mc.world, pos) != -1.0f;
    }
    
    static {
        PacketMine.TPS = 20.0f;
    }
}
