package me.tux.tuxhack.module.modules.movement;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.tux.tuxhack.TuxHack;
import me.tux.tuxhack.event.events.PlayerMoveEvent;
import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.module.ModuleManager;
import me.tux.tuxhack.setting.Setting;
import me.tux.tuxhack.util.EntityUtil;
import me.tux.tuxhack.util.MotionUtils;
import me.tux.tuxhack.util.Timer;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.block.Block;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.block.BlockAir;
import java.util.ArrayList;
import net.minecraft.client.entity.EntityPlayerSP;
import java.util.function.Predicate;
import net.minecraft.block.BlockLiquid;
import net.minecraft.init.MobEffects;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.block.BlockPackedIce;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.BlockIce;
import me.zero.alpine.listener.EventHandler;

public class Speed extends Module
{
    int waitCounter;
    int forward;
    private double moveSpeed;
    public static boolean doSlow;
    public Timer waitTimer;
    Setting.Boolean ice;
    Setting.Mode Mode;
    Setting.Double speed;
    @EventHandler
    private final Listener<PlayerMoveEvent> listener;

    public Speed() {
        super("Speed", Category.Movement);
        this.forward = 1;
        this.waitTimer = new Timer();
        final boolean b = false;
        final boolean[] icee = new boolean[1];
        final EntityPlayerSP[] player = new EntityPlayerSP[1];
        final double[] motionY = new double[1];
        final double[] moveSpeed = new double[1];
        final Object[] o = new Object[1];
        final Object moveSpeed2 = null;
        final Object o2 = null;
        final double[][] dir = new double[1][1];
        this.listener = new Listener<PlayerMoveEvent>(event -> {
            Label_0126_1: {
                if (this.ice.getValue()) {
                    if (!(Speed.mc.world.getBlockState(new BlockPos(Speed.mc.player.posX, Speed.mc.player.posY - 1.0, Speed.mc.player.posZ)).getBlock() instanceof BlockIce)) {
                        if (!(Speed.mc.world.getBlockState(new BlockPos(Speed.mc.player.posX, Speed.mc.player.posY - 1.0, Speed.mc.player.posZ)).getBlock() instanceof BlockPackedIce)) {
                            break Label_0126_1;
                        }
                    }
                    break Label_0126_1;
                }
            }
            icee[0] = b;
            if (!icee[0]) {
                if (!Speed.mc.player.isInLava() && !Speed.mc.player.isInWater() && !Speed.mc.player.isOnLadder()) {
                    if (this.Mode.getValue().equalsIgnoreCase("Strafe")) {
                        motionY[0] = 0.41999998688697815;
                        if (Speed.mc.player.onGround && MotionUtils.isMoving((EntityLivingBase)Speed.mc.player) && this.waitTimer.reach(300L)) {
                            if (Speed.mc.player.isPotionActive(MobEffects.JUMP_BOOST)) {
                                motionY[0] += (Speed.mc.player.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1f;
                            }
                            player[0] = Speed.mc.player;
                            event.setY(player[0].motionY = motionY[0]);
                            this.moveSpeed = MotionUtils.getBaseMoveSpeed() * ((EntityUtil.isColliding(0.0, -0.5, 0.0) instanceof BlockLiquid && !EntityUtil.isInLiquid()) ? 0.9 : 1.901);
                            Speed.doSlow = true;
                            this.waitTimer.reset();
                        }
                        else if (Speed.doSlow || Speed.mc.player.collidedHorizontally) {
                            moveSpeed[0] = this.moveSpeed;
                            if (EntityUtil.isColliding(0.0, -0.8, 0.0) instanceof BlockLiquid && !EntityUtil.isInLiquid()) {
                                o[0] = 0.4;
                            }
                            else {
                                MotionUtils.getBaseMoveSpeed();
                                this.moveSpeed = (double) moveSpeed2;
                            }
                            this.moveSpeed -= this.moveSpeed / 159.0;
                        }
                        this.moveSpeed = Math.max(this.moveSpeed, MotionUtils.getBaseMoveSpeed());
                        dir[0] = MotionUtils.forward(this.moveSpeed);
                        event.setX(dir[0][0]);
                        event.setZ(dir[0][1]);
                    }
                }
            }
        }, (Predicate<PlayerMoveEvent>[])new Predicate[0]);
    }

    @Override
    public boolean setup() {
        this.ice = this.registerBoolean("Ice", "Ice", true);
        final ArrayList<String> modes = new ArrayList<String>();
        modes.add("Strafe");
        modes.add("YPort");
        modes.add("Packet");
        modes.add("Packet2");
        modes.add("FakeStrafe");
        this.speed = this.registerDouble("Speed", "Speed", 8.0, 0.0, 10.0);
        this.Mode = this.registerMode("Modes", "Modes", modes, "Strafe");
        return false;
    }

    public void onEnable() {
        this.moveSpeed = MotionUtils.getBaseMoveSpeed();
        TuxHack.EVENT_BUS.subscribe(this);
    }

    @Override
    public int onUpdate() {
        final boolean icee = this.ice.getValue() && (Speed.mc.world.getBlockState(new BlockPos(Speed.mc.player.posX, Speed.mc.player.posY - 1.0, Speed.mc.player.posZ)).getBlock() instanceof BlockIce || Speed.mc.world.getBlockState(new BlockPos(Speed.mc.player.posX, Speed.mc.player.posY - 1.0, Speed.mc.player.posZ)).getBlock() instanceof BlockPackedIce);
        if (icee) {
            MotionUtils.setSpeed((EntityLivingBase)Speed.mc.player, MotionUtils.getBaseMoveSpeed() + (Speed.mc.player.isPotionActive(MobEffects.SPEED) ? ((Speed.mc.player.ticksExisted % 2 == 0) ? 0.7 : 0.1) : 0.4));
        }
        if (!icee) {
            if ((this.Mode.getValue().equalsIgnoreCase("Packet") || this.Mode.getValue().equalsIgnoreCase("Packet2")) && MotionUtils.isMoving((EntityLivingBase)Speed.mc.player) && Speed.mc.player.onGround) {
                final boolean step = ModuleManager.isModuleEnabled("Step");
                final double posX = Speed.mc.player.posX;
                final double posY = Speed.mc.player.posY;
                final double posZ = Speed.mc.player.posZ;
                final boolean ground = Speed.mc.player.onGround;
                final double[] dir1 = MotionUtils.forward(0.5);
                final BlockPos pos = new BlockPos(posX + dir1[0], posY, posZ + dir1[1]);
                final Block block = Speed.mc.world.getBlockState(pos).getBlock();
                if (step && !(block instanceof BlockAir)) {
                    MotionUtils.setSpeed((EntityLivingBase)Speed.mc.player, 0.0);
                    return 0;
                }
                if (Speed.mc.world.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ())).getBlock() instanceof BlockAir) {
                    return 0;
                }
                for (double x = 0.0625; x < this.speed.getValue(); x += 0.262) {
                    final double[] dir2 = MotionUtils.forward(x);
                    Speed.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(posX + dir2[0], posY, posZ + dir2[1], ground));
                }
                if (this.Mode.getValue().equalsIgnoreCase("Packet2")) {
                    MotionUtils.setSpeed((EntityLivingBase)Speed.mc.player, 2.0);
                }
                Speed.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(posX + Speed.mc.player.motionX, (Speed.mc.player.posY <= 10.0) ? 255.0 : 1.0, posZ + Speed.mc.player.motionZ, ground));
            }
            if (this.Mode.getValue().equalsIgnoreCase("YPort")) {
                if (!MotionUtils.isMoving((EntityLivingBase)Speed.mc.player) || (Speed.mc.player.isInWater() && Speed.mc.player.isInLava()) || Speed.mc.player.collidedHorizontally) {
                    return 0;
                }
                if (Speed.mc.player.onGround) {
                    EntityUtil.setTimer(1.15f);
                    Speed.mc.player.jump();
                    final boolean ice = Speed.mc.world.getBlockState(new BlockPos(Speed.mc.player.posX, Speed.mc.player.posY - -0.9, Speed.mc.player.posZ)).getBlock() instanceof BlockIce || Speed.mc.world.getBlockState(new BlockPos(Speed.mc.player.posX, Speed.mc.player.posY -0.9, Speed.mc.player.posZ)).getBlock() instanceof BlockPackedIce;
                    MotionUtils.setSpeed((EntityLivingBase)Speed.mc.player, MotionUtils.getBaseMoveSpeed() + (ice ? 0.3 : 0.06));
                }
                else {
                    Speed.mc.player.motionY = -0.9;
                    EntityUtil.resetTimer();
                }
            }
        }
        return 0;
    }

    public int onDisable() {
        TuxHack.EVENT_BUS.unsubscribe(this);
        EntityUtil.resetTimer();
        return 0;
    }

    @Override
    public String getHudInfo() {
        String t = "";
        if (this.Mode.getValue().equalsIgnoreCase("Strafe")) {
            t = "[" + ChatFormatting.WHITE + "Strafe" + ChatFormatting.GRAY + "]";
        }
        if (this.Mode.getValue().equalsIgnoreCase("YPort")) {
            t = "[" + ChatFormatting.WHITE + "YPort" + ChatFormatting.GRAY + "]";
        }
        if (this.Mode.getValue().equalsIgnoreCase("Packet")) {
            t = "[" + ChatFormatting.WHITE + "Packet" + ChatFormatting.GRAY + "]";
        }
        if (this.Mode.getValue().equalsIgnoreCase("Packet2")) {
            t = "[" + ChatFormatting.WHITE + "Packet2" + ChatFormatting.GRAY + "]";
        }
        if (this.Mode.getValue().equalsIgnoreCase("FakeStrafe")) {
            t = "[" + ChatFormatting.WHITE + "Strafe" + ChatFormatting.GRAY + "]";
        }
        return t;
    }
}
