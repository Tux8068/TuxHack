package me.tux.tuxhack.module.modules.combat;

import java.util.List;
import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.setting.Setting;
import me.tux.tuxhack.util.BlockUtil;
import me.tux.tuxhack.util.Wrapper;
import me.tux.tuxhack.util.entities.EntityUtil;
import me.tux.tuxhack.util.friend.Friends;
import net.minecraft.block.BlockWeb;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.util.EnumHand;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;

public class WebAura extends Module
{
    Setting.Boolean rotate;
    Setting.Double range;
    Setting.Integer bpt;
    Setting.Boolean spoofRotations;
    Setting.Boolean spoofHotbar;
    private final Vec3d[] offsetList;
    private boolean slowModeSwitch;
    private int playerHotbarSlot;
    private EntityPlayer closestTarget;
    private int lastHotbarSlot;
    private int offsetStep;

    public WebAura() {
        super("WebAura", Category.Combat);
        this.offsetList = new Vec3d[] { new Vec3d(0.0, 1.0, 0.0), new Vec3d(0.0, 0.0, 0.0) };
        this.slowModeSwitch = false;
        this.playerHotbarSlot = -1;
        this.lastHotbarSlot = -1;
        this.offsetStep = 0;
    }

    @Override
    public boolean setup() {
        this.rotate = this.registerBoolean("Rotate", "Rotate", false);
        this.range = this.registerDouble("Range", "Range", 4.0, 0.0, 6.0);
        this.bpt = this.registerInteger("Blocks Per Tick", "BlocksPerTick", 8, 1, 15);
        this.spoofRotations = this.registerBoolean("Spoof Rotations", "SpoofRotations", false);
        this.spoofHotbar = this.registerBoolean("Silent Switch", "SilentSwitch", false);
        return false;
    }

    @Override
    public int onUpdate() {
        if (this.closestTarget == null) {
            return 0;
        }
        if (this.slowModeSwitch) {
            this.slowModeSwitch = false;
            return 0;
        }
        for (int i = 0; i < (int)Math.floor(this.bpt.getValue()); ++i) {
            if (this.offsetStep >= this.offsetList.length) {
                this.endLoop();
                return i;
            }
            final Vec3d offset = this.offsetList[this.offsetStep];
            this.placeBlock(new BlockPos(this.closestTarget.getPositionVector()).down().add(offset.x, offset.y, offset.z));
            ++this.offsetStep;
        }
        this.slowModeSwitch = true;
        return 0;
    }

    private void placeBlock(final BlockPos blockPos) {
        if (!Wrapper.getWorld().getBlockState(blockPos).getMaterial().isReplaceable()) {
            return;
        }
        if (!BlockUtil.checkForNeighbours(blockPos)) {
            return;
        }
        this.placeBlockExecute(blockPos);
    }

    public void placeBlockExecute(final BlockPos pos) {
        final Vec3d eyesPos = new Vec3d(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + Wrapper.getPlayer().getEyeHeight(), Wrapper.getPlayer().posZ);
        final EnumFacing[] values = EnumFacing.values();
        final int length = values.length;
        final int n = 0;
        if (0 >= length) {
            return;
        }
        final EnumFacing side = values[0];
        final BlockPos neighbor = pos.offset(side);
        final EnumFacing side2 = side.getOpposite();
        final Vec3d hitVec = new Vec3d((Vec3i)neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
        if (this.spoofRotations.getValue()) {
            BlockUtil.faceVectorPacketInstant(hitVec);
        }
        boolean needSneak = false;
        final Block blockBelow = WebAura.mc.world.getBlockState(neighbor).getBlock();
        if (BlockUtil.blackList.contains(blockBelow) || BlockUtil.shulkerList.contains(blockBelow)) {
            needSneak = true;
        }
        if (needSneak) {
            WebAura.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)WebAura.mc.player, CPacketEntityAction.Action.START_SNEAKING));
        }
        final int obiSlot = this.findWebInHotBar();
        if (obiSlot == -1) {
            this.disable();
            return;
        }
        if (this.lastHotbarSlot != obiSlot) {
            if (this.spoofHotbar.getValue()) {
                WebAura.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(obiSlot));
            }
            else {
                Wrapper.getPlayer().inventory.currentItem = obiSlot;
            }
            this.lastHotbarSlot = obiSlot;
        }
        WebAura.mc.playerController.processRightClickBlock(Wrapper.getPlayer(), WebAura.mc.world, neighbor, side2, hitVec, EnumHand.MAIN_HAND);
        WebAura.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
        if (needSneak) {
            WebAura.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)WebAura.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
    }

    private int findWebInHotBar() {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = Wrapper.getPlayer().inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock) {
                final Block block = ((ItemBlock)stack.getItem()).getBlock();
                if (block instanceof BlockWeb) {
                    slot = i;
                    break;
                }
            }
        }
        return slot;
    }

    private void findTarget() {
        final List<EntityPlayer> playerList = (List<EntityPlayer>)Wrapper.getWorld().playerEntities;
        for (final EntityPlayer target : playerList) {
            if (target == WebAura.mc.player) {
                continue;
            }
            if (Friends.isFriend(target.getName())) {
                continue;
            }
            if (!EntityUtil.isLiving((Entity)target)) {
                continue;
            }
            if (target.getHealth() <= 0.0f) {
                continue;
            }
            final double currentDistance = Wrapper.getPlayer().getDistance((Entity)target);
            if (currentDistance > this.range.getValue()) {
                continue;
            }
            if (this.closestTarget == null) {
                this.closestTarget = target;
            }
            else {
                if (currentDistance >= Wrapper.getPlayer().getDistance((Entity)this.closestTarget)) {
                    continue;
                }
                this.closestTarget = target;
            }
        }
    }

    private void endLoop() {
        this.offsetStep = 0;
        if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
            Wrapper.getPlayer().inventory.currentItem = this.playerHotbarSlot;
            this.lastHotbarSlot = this.playerHotbarSlot;
        }
        this.findTarget();
    }

    @Override
    protected void onEnable() {
        if (WebAura.mc.player == null) {
            this.disable();
            return;
        }
        this.playerHotbarSlot = Wrapper.getPlayer().inventory.currentItem;
        this.lastHotbarSlot = -1;
        this.findTarget();
    }

    @Override
    protected int onDisable() {
        if (WebAura.mc.player == null) {
            return 0;
        }
        if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
            if (this.spoofHotbar.getValue()) {
                WebAura.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.playerHotbarSlot));
            }
            else {
                Wrapper.getPlayer().inventory.currentItem = this.playerHotbarSlot;
            }
        }
        this.playerHotbarSlot = -1;
        this.lastHotbarSlot = -1;
        return 0;
    }
}

