package me.tux.tuxhack.module.modules.combat;

import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.setting.Setting;
import me.tux.tuxhack.util.friend.Friends;
import net.minecraft.util.EnumHand;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.item.ItemStack;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;


public class DesyncAura extends Module
{
    Setting.Double hitRange;
    Setting.Integer delay;
    Setting.Boolean switchTo32k;
    Setting.Boolean onlyUse32k;
    private int hasWaited;

    public DesyncAura() {
        super("DesyncAura", Category.Combat);
        this.hasWaited = 0;
    }

    @Override
    public boolean setup() {
        this.hitRange = this.registerDouble("Hit Range", "HitRange", 4.0, 1.0, 6.0);
        this.delay = this.registerInteger("Delay", "Delay", 6, 0, 10);
        this.switchTo32k = this.registerBoolean("Switch To 32k", "SwitchTo32k", true);
        this.onlyUse32k = this.registerBoolean("Only 32k", "Only32k", true);
        return false;
    }

    @Override
    public int onUpdate() {
        if (this.isEnabled() && !DesyncAura.mc.player.isDead && DesyncAura.mc.world != null) {
            if (this.hasWaited >= this.delay.getValue()) {
                this.hasWaited = 0;
                for (final Entity entity : DesyncAura.mc.world.loadedEntityList) {
                    if (entity instanceof EntityLivingBase && entity != DesyncAura.mc.player && DesyncAura.mc.player.getDistance(entity) <= this.hitRange.getValue() && ((EntityLivingBase)entity).getHealth() > 0.0f && entity instanceof EntityPlayer && (!(entity instanceof EntityPlayer) || !Friends.isFriend(entity.getName())) && (this.checkSharpness(DesyncAura.mc.player.getHeldItemMainhand()) || !this.onlyUse32k.getValue())) {
                        this.attack(entity);
                    }
                }
                return 0;
            }
            ++this.hasWaited;
        }
        return 0;
    }

    private boolean checkSharpness(final ItemStack item) {
        if (item.getTagCompound() == null) {
            return false;
        }
        final NBTTagList enchants = (NBTTagList)item.getTagCompound().getTag("ench");
        if (enchants == null) {
            return false;
        }
        int i = 0;
        while (i < enchants.tagCount()) {
            final NBTTagCompound enchant = enchants.getCompoundTagAt(i);
            if (enchant.getInteger("id") == 16) {
                final int lvl = enchant.getInteger("lvl");
                if (lvl >= 42) {
                    return true;
                }
                break;
            }
            else {
                ++i;
            }
        }
        return false;
    }

    public void attack(final Entity e) {
        boolean holding32k = false;
        if (this.checkSharpness(DesyncAura.mc.player.getHeldItemMainhand())) {
            holding32k = true;
        }
        if (this.switchTo32k.getValue() && !holding32k) {
            int newSlot = -1;
            for (int i = 0; i < 9; ++i) {
                final ItemStack stack = DesyncAura.mc.player.inventory.getStackInSlot(i);
                if (stack != ItemStack.EMPTY && this.checkSharpness(stack)) {
                    newSlot = i;
                    break;
                }
            }
            if (newSlot != -1) {
                DesyncAura.mc.player.inventory.currentItem = newSlot;
                holding32k = true;
            }
        }
        if (!this.onlyUse32k.getValue() || holding32k) {
            DesyncAura.mc.playerController.attackEntity((EntityPlayer)DesyncAura.mc.player, e);
            DesyncAura.mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }
}

