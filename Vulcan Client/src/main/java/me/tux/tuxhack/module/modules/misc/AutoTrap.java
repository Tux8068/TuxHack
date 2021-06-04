package me.tux.tuxhack.module.modules.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.tux.tuxhack.command.Command;
import me.tux.tuxhack.util.entities.EntityUtil;
import me.tux.tuxhack.util.friend.Friends;
import net.minecraft.block.BlockObsidian;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import java.util.Iterator;
import net.minecraft.block.Block;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3i;
import me.tux.tuxhack.util.BlockInteractionHelper;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockAir;
import net.minecraft.util.math.BlockPos;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.util.math.Vec3d;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import java.util.List;
import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;
import me.tux.tuxhack.setting.Setting;
import me.tux.tuxhack.module.Module;

public class AutoTrap extends Module {
    private Setting.Integer range;
    private Setting.Integer blocksPerTick;
    private Setting.Integer tickDelay;
    private Setting.Boolean rotate;
    private EntityPlayer closestTarget;
    private String lastTickTargetName;
    private int playerHotbarSlot;
    private int lastHotbarSlot;
    private int delayStep;
    private boolean isSneaking;
    private int offsetStep;
    private boolean firstRun;
    private int delayTimeout;
    Setting.Mode mode;
    Setting.Boolean step;
    Setting.Boolean say;
    private Setting.Boolean triggerable;
    private Setting.Integer timeoutTicks;

    public AutoTrap() {
        super("AutoTrap", "Traps Players In Obsidian", Category.Combat);
        this.playerHotbarSlot = -1;
        this.lastHotbarSlot = -1;
        this.delayStep = 0;
        this.isSneaking = false;
        this.offsetStep = 0;
    }

    @Override
    public boolean setup() {
        final ArrayList<String> modes = new ArrayList<String>();
        modes.add("Trap");
        modes.add("TrapFullRoof");
        modes.add("Cheap");
        modes.add("Feet");
        this.mode = this.registerMode("MODES", "mode", modes, "Cheap");
        this.rotate = this.registerBoolean("Rotate", "Rotate", true);
        this.range = this.registerInteger("range", "range", 5, 0, 6);
        this.blocksPerTick = this.registerInteger("BlocksPerTick", "BPT", 4, 0, 8);
        this.tickDelay = this.registerInteger("tickdelay", "td", 2, 0, 20);
        this.timeoutTicks = this.registerInteger("timeoutTicks", "timeoutticks", 10, 0, 20);
        this.triggerable = this.registerBoolean("triggerable", "trigger", true);
        this.step = this.registerBoolean("Anti-step", "antistep", false);
        this.say = this.registerBoolean("Say", "Say", false);
        return false;
    }

    @Override
    protected void onEnable() {
        this.delayTimeout = 0;
        if (AutoTrap.mc.player == null) {
            this.disable();
            return;
        }
        this.firstRun = true;
        this.playerHotbarSlot = AutoTrap.mc.player.inventory.currentItem;
        this.lastHotbarSlot = -1;
    }

    @Override
    protected int onDisable() {
        this.delayTimeout = 0;
        if (AutoTrap.mc.player == null) {
            return 0;
        }
        if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
            AutoTrap.mc.player.inventory.currentItem = this.playerHotbarSlot;
        }
        if (this.isSneaking) {
            AutoTrap.mc.player.connection.sendPacket((Packet) new CPacketEntityAction((Entity) AutoTrap.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            this.isSneaking = false;
        }
        this.playerHotbarSlot = -1;
        this.lastHotbarSlot = -1;
        return 0;
    }

    @Override
    public int onUpdate() {
        ++this.delayTimeout;
        if (AutoTrap.mc.player == null) {
            return 0;
        }
        if (this.triggerable.getValue() && this.delayTimeout >= this.timeoutTicks.getValue()) {
            this.disable();
        }
        if (!this.firstRun) {
            if (this.delayStep < this.tickDelay.getValue()) {
                ++this.delayStep;
                return 0;
            }
            this.delayStep = 0;
        }
        this.findClosestTarget();
        if (this.closestTarget == null) {
            if (this.firstRun) {
                this.firstRun = false;
            }
            return 0;
        }
        if (this.firstRun) {
            this.firstRun = false;
            this.lastTickTargetName = this.closestTarget.getName();
        } else if (!this.lastTickTargetName.equals(this.closestTarget.getName())) {
            this.lastTickTargetName = this.closestTarget.getName();
            this.offsetStep = 0;
        }
        final List<Vec3d> placeTargets = new ArrayList<Vec3d>();
        if (this.mode.getValue().equalsIgnoreCase("Trap")) {
            Collections.addAll(placeTargets, Offsets.TRAP);
        }
        if (this.mode.getValue().equalsIgnoreCase("TrapFullRoof")) {
            Collections.addAll(placeTargets, Offsets.TRAPFULLROOF);
        }
        if (this.mode.getValue().equalsIgnoreCase("Cheap")) {
            Collections.addAll(placeTargets, Offsets.Cheap);
        }
        if (this.mode.getValue().equalsIgnoreCase("Feet")) {
            Collections.addAll(placeTargets, Offsets.Feet);
        }
        if (step.getValue() == true)
            Collections.addAll(placeTargets, Offsets.step);
        int blocksPlaced = 0;
        while (blocksPlaced < this.blocksPerTick.getValue()) {
            if (this.offsetStep >= placeTargets.size()) {
                this.offsetStep = 0;
                break;
            }
            final BlockPos offsetPos = new BlockPos((Vec3d) placeTargets.get(this.offsetStep));
            final BlockPos targetPos = new BlockPos(this.closestTarget.getPositionVector()).down().add(offsetPos.getX(), offsetPos.getY(), offsetPos.getZ());
            if (this.placeBlockInRange(targetPos, this.range.getValue())) {
                ++blocksPlaced;
            }
            ++this.offsetStep;
        }
        if (say.getValue() == true);
        Command.sendClientMessage(ChatFormatting.AQUA + this.closestTarget.getName() + ChatFormatting.WHITE + " Was Just boxxed like a fish!");

        if (blocksPlaced > 0) {
            if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
                AutoTrap.mc.player.inventory.currentItem = this.playerHotbarSlot;
                this.lastHotbarSlot = this.playerHotbarSlot;
            }
            if (this.isSneaking) {
                AutoTrap.mc.player.connection.sendPacket((Packet) new CPacketEntityAction((Entity) AutoTrap.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                this.isSneaking = false;
            }
        }
        return 0;
    }

    private boolean placeBlockInRange(final BlockPos pos, final double range) {
        final Block block = AutoTrap.mc.world.getBlockState(pos).getBlock();
        if (!(block instanceof BlockAir) && !(block instanceof BlockLiquid)) {
            return false;
        }
        for (final Entity entity : AutoTrap.mc.world.getEntitiesWithinAABBExcludingEntity((Entity) null, new AxisAlignedBB(pos))) {
            if (!(entity instanceof EntityItem) && !(entity instanceof EntityXPOrb)) {
                return false;
            }
        }
        final EnumFacing side = BlockInteractionHelper.getPlaceableSide(pos);
        if (side == null) {
            return false;
        }
        final BlockPos neighbour = pos.offset(side);
        final EnumFacing opposite = side.getOpposite();
        if (!BlockInteractionHelper.canBeClicked(neighbour)) {
            return false;
        }
        final Vec3d hitVec = new Vec3d((Vec3i) neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        final Block neighbourBlock = AutoTrap.mc.world.getBlockState(neighbour).getBlock();
        if (AutoTrap.mc.player.getPositionVector().distanceTo(hitVec) > range) {
            return false;
        }
        final int obiSlot = this.findObiInHotbar();
        if (obiSlot == -1) {
            this.disable();
        }
        if (this.lastHotbarSlot != obiSlot) {
            AutoTrap.mc.player.inventory.currentItem = obiSlot;
            this.lastHotbarSlot = obiSlot;
        }
        if ((!this.isSneaking && BlockInteractionHelper.blackList.contains(neighbourBlock)) || BlockInteractionHelper.shulkerList.contains(neighbourBlock)) {
            AutoTrap.mc.player.connection.sendPacket((Packet) new CPacketEntityAction((Entity) AutoTrap.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            this.isSneaking = true;
        }
        if (this.rotate.getValue()) {
            BlockInteractionHelper.faceVectorPacketInstant(hitVec);
        }
        AutoTrap.mc.playerController.processRightClickBlock(AutoTrap.mc.player, AutoTrap.mc.world, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
        AutoTrap.mc.player.swingArm(EnumHand.MAIN_HAND);
        return true;
    }


    private int findObiInHotbar() {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = AutoTrap.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock) {
                final Block block = ((ItemBlock) stack.getItem()).getBlock();
                if (block instanceof BlockObsidian) {
                    slot = i;
                    break;
                }
            }
        }
        return slot;
    }

    private void findClosestTarget() {
        final List<EntityPlayer> playerList = (List<EntityPlayer>) AutoTrap.mc.world.playerEntities;
        this.closestTarget = null;
        for (final EntityPlayer target : playerList) {
            if (target == AutoTrap.mc.player) {
                continue;
            }
            if (Friends.isFriend(target.getName())) {
                continue;
            }
            if (!EntityUtil.isLiving((Entity) target)) {
                continue;
            }
            if (target.getHealth() <= 0.0f) {
                continue;
            }
            if (this.closestTarget == null) {
                this.closestTarget = target;
            } else {
                if (AutoTrap.mc.player.getDistance((Entity) target) >= AutoTrap.mc.player.getDistance((Entity) this.closestTarget)) {
                    continue;
                }
                this.closestTarget = target;
            }
        }
    }

    @Override
    public String getHudInfo() {
        return "\u00c2§7[\u00c2§f" + this.mode.getValue() + "\u00c2§7]";
    }

    private static class Offsets {
        private static Vec3d[] TRAP;
        private static Vec3d[] TRAPFULLROOF;
        private static Vec3d[] Cheap;
        private static Vec3d[] Feet;
        private static Vec3d[] step;

        static {
            Offsets.TRAP = new Vec3d[0];
            Offsets.TRAPFULLROOF = new Vec3d[0];
            Offsets.Cheap = new Vec3d[0];
            Offsets.Feet = new Vec3d[0];
            Offsets.TRAP = new Vec3d[]{new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, 1.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(0.0, 2.0, -1.0), new Vec3d(1.0, 2.0, 0.0), new Vec3d(0.0, 2.0, 1.0), new Vec3d(-1.0, 2.0, 0.0), new Vec3d(0.0, 3.0, -1.0), new Vec3d(0.0, 3.0, 0.0)};
            Offsets.TRAPFULLROOF = new Vec3d[]{new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, 1.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(1.0, 2.0, 0.0), new Vec3d(0.0, 2.0, 1.0), new Vec3d(-1.0, 2.0, 0.0), new Vec3d(0.0, 2.0, -1.0), new Vec3d(1.0, 3.0, 0.0), new Vec3d(-1.0, 3.0, 0.0), new Vec3d(0.0, 3.0, 1.0), new Vec3d(0.0, 2.0, 1.0), new Vec3d(-1.0, 2.0, 0.0), new Vec3d(0.0, 3.0, -1.0), new Vec3d(0.0, 3.0, 0.0)};
            Offsets.Cheap = new Vec3d[]{new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, 1.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(0.0, 2.0, -1.0), new Vec3d(0.0, 3.0, -1.0), new Vec3d(0.0, 3.0, 0.0)};
            Offsets.step = new Vec3d[]{new Vec3d(0.0, 3.0, 0.0), new Vec3d(0.0, 4.0,0.0), new Vec3d(0.0, 3.0,0.0)};
            Offsets.Feet = new Vec3d[]{new Vec3d(0.0, 0.0, -1.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(1.0, 1.0, 0.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(-1.0, 2.0, 0.0), new Vec3d(-1.0, 2.0, 1.0), new Vec3d(-1.0, 3.0, 0.0), new Vec3d(0.0, 2.0, 1.0), new Vec3d(0.0, 3.0, 0.0)};
        }
        }
    }


