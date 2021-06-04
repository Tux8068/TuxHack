//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.tux.tuxhack.module.modules.combat;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import me.tux.tuxhack.util.friend.Friends;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.network.Packet;
import net.minecraft.util.EnumFacing;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumHand;
import me.tux.tuxhack.module.ModuleManager;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import me.tux.tuxhack.command.Command;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import java.util.List;
import java.util.ArrayList;
import me.tux.tuxhack.setting.Setting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import me.tux.tuxhack.module.Module;

public class PacketAutoCity extends Module
{
    private boolean firstRun;
    private BlockPos mineTarget;
    private EntityPlayer closestTarget;
    Setting.Double range;
    Setting.Boolean announceUsage;
    Setting.Mode BlockColour;
    Setting.Mode BlockPOPColour;
    
    public PacketAutoCity() {
        super("PacketCity", "automatically cities enimies using packets", Category.Combat);
        this.mineTarget = null;
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
        this.range = this.registerDouble("Range", "range", 5.0, 1.0, 6.0);
        this.announceUsage = this.registerBoolean("announceUsage", "announce", false);
        return false;
    }
    
    public void onEnable() {
        if (PacketAutoCity.mc.player == null) {
            this.toggle();
            return;
        }
        MinecraftForge.EVENT_BUS.register((Object)this);
        this.firstRun = true;
    }
    
    public int onDisable() {
        if (PacketAutoCity.mc.player == null) {
            return 0;
        }
        MinecraftForge.EVENT_BUS.unregister((Object)this);
        Command.sendRawMessage(ChatFormatting.RED.toString() + " Disabled");
        return 0;
    }
    
    @Override
    public int onUpdate() {
        if (PacketAutoCity.mc.player == null) {
            return 0;
        }
        this.findClosestTarget();
        if (this.closestTarget == null) {
            if (this.firstRun) {
                this.firstRun = false;
                if (this.announceUsage.getValue()) {
                    Command.sendRawMessage(ChatFormatting.WHITE.toString() + "Enabled" + ChatFormatting.RESET.toString() + ", no one to city!");
                }
            }
            this.toggle();
            return 0;
        }
        if (this.firstRun && this.mineTarget != null) {
            this.firstRun = false;
            if (this.announceUsage.getValue()) {
                Command.sendRawMessage(" Trying to mine: " + ChatFormatting.AQUA.toString() + this.closestTarget.getName());
            }
        }
        this.findCityBlock();
        if (this.mineTarget != null) {
            int newSlot = -1;
            for (int i = 0; i < 9; ++i) {
                final ItemStack stack = PacketAutoCity.mc.player.inventory.getStackInSlot(i);
                if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemPickaxe) {
                    newSlot = i;
                    break;
                }
            }
            if (newSlot != -1) {
                PacketAutoCity.mc.player.inventory.currentItem = newSlot;
            }
            final boolean wasEnabled = ModuleManager.getModuleByName("PacketMine").isEnabled();
            if (!wasEnabled) {
                ModuleManager.getModuleByName("PacketMine").toggle();
            }
            PacketAutoCity.mc.player.swingArm(EnumHand.MAIN_HAND);
            PacketAutoCity.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.mineTarget, EnumFacing.DOWN));
            PacketAutoCity.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.mineTarget, EnumFacing.DOWN));
            if (!wasEnabled) {
                ModuleManager.getModuleByName("PacketMine").toggle();
            }
            this.toggle();
        }
        else {
            Command.sendRawMessage(TextFormatting.BLUE + "] No city blocks to mine!");
            this.toggle();
        }
        return 0;
    }
    
    public BlockPos findCityBlock() {
        final Double dist = this.range.getValue();
        final Vec3d vec = this.closestTarget.getPositionVector();
        if (PacketAutoCity.mc.player.getPositionVector().distanceTo(vec) <= dist) {
            final BlockPos targetX = new BlockPos(vec.add(1.0, 0.0, 0.0));
            final BlockPos targetXMinus = new BlockPos(vec.add(-1.0, 0.0, 0.0));
            final BlockPos targetZ = new BlockPos(vec.add(0.0, 0.0, 1.0));
            final BlockPos targetZMinus = new BlockPos(vec.add(0.0, 0.0, -1.0));
            if (this.canBreak(targetX)) {
                this.mineTarget = targetX;
            }
            if (!this.canBreak(targetX) && this.canBreak(targetXMinus)) {
                this.mineTarget = targetXMinus;
            }
            if (!this.canBreak(targetX) && !this.canBreak(targetXMinus) && this.canBreak(targetZ)) {
                this.mineTarget = targetZ;
            }
            if (!this.canBreak(targetX) && !this.canBreak(targetXMinus) && !this.canBreak(targetZ) && this.canBreak(targetZMinus)) {
                this.mineTarget = targetZMinus;
            }
            if ((!this.canBreak(targetX) && !this.canBreak(targetXMinus) && !this.canBreak(targetZ) && !this.canBreak(targetZMinus)) || PacketAutoCity.mc.player.getPositionVector().distanceTo(vec) > dist) {
                this.mineTarget = null;
            }
        }
        return this.mineTarget;
    }
    
    private boolean canBreak(final BlockPos pos) {
        final IBlockState blockState = PacketAutoCity.mc.world.getBlockState(pos);
        final Block block = blockState.getBlock();
        return block.getBlockHardness(blockState, (World)PacketAutoCity.mc.world, pos) != -1.0f;
    }
    
    private void findClosestTarget() {
        final List<EntityPlayer> playerList = (List<EntityPlayer>)PacketAutoCity.mc.world.playerEntities;
        this.closestTarget = null;
        for (final EntityPlayer target : playerList) {
            if (target == PacketAutoCity.mc.player) {
                continue;
            }
            if (Friends.isFriend(target.getName())) {
                continue;
            }
            if (!isLiving((Entity)target)) {
                continue;
            }
            if (target.getHealth() <= 0.0f) {
                continue;
            }
            if (this.closestTarget == null) {
                this.closestTarget = target;
            }
            else {
                if (PacketAutoCity.mc.player.getDistance((Entity)target) >= PacketAutoCity.mc.player.getDistance((Entity)this.closestTarget)) {
                    continue;
                }
                this.closestTarget = target;
            }
        }
    }
    
    public static boolean isLiving(final Entity e) {
        return e instanceof EntityLivingBase;
    }
}
