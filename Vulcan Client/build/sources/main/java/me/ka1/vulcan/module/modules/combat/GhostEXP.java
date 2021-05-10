package me.ka1.vulcan.module.modules.combat;

import me.ka1.vulcan.module.Module;
import me.ka1.vulcan.setting.Setting;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;

/**
 * @author Madmegsox1
 * @since 30/04/2021
 */

public class GhostEXP extends Module {
    public GhostEXP() {
        super("GhostEXP", "EXP's with your third arm.", Category.Combat);
    }

    Setting.Integer takeOffVal;
    Setting.Integer lookPitch;
    Setting.Boolean allowTakeOff;
    Setting.Integer delay;

    public void setup() {
        takeOffVal = registerInteger("ArmourHealth","ArmourHP",15, 1, 90);
        lookPitch = registerInteger("Pitch","Pitch",90, 10, 360);
        allowTakeOff = registerBoolean("RemoveArmour", "RemoveArmour", false);
        delay = registerInteger("delay","delay",1, 0, 4);
    }

    private int delay_count;
    int prvSlot;

    @Override
    public void onEnable(){
        delay_count = 0;
    }

    @Override
    public int onUpdate(){
            int oldPitch = (int)mc.player.rotationPitch;
            prvSlot = mc.player.inventory.currentItem; //TODO add better rotations
            mc.player.connection.sendPacket(new CPacketHeldItemChange(findExpInHotbar()));
            mc.player.rotationPitch = lookPitch.getValue();
            mc.player.connection.sendPacket(new CPacketPlayer.Rotation(mc.player.rotationYaw, lookPitch.getValue(), true));
            mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            mc.player.rotationPitch = oldPitch;
            mc.player.inventory.currentItem = prvSlot;
            mc.player.connection.sendPacket(new CPacketHeldItemChange(prvSlot));
            if (allowTakeOff.getValue()) {
                takeArmorOff();
            }
        return oldPitch;
    }

    private int findExpInHotbar() {
        int slot = 0;
        for (int i = 0; i < 9; i++) {
            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.EXPERIENCE_BOTTLE) {
                slot = i;
                break;
            }
        }
        return slot;
    }

    private ItemStack getArmor(int first) {
        return mc.player.inventoryContainer.getInventory().get(first);
    }

    private void takeArmorOff() {
        int slot = 5;
        while (slot <= 8) {
            ItemStack item;
            item = getArmor(slot);
            double max_dam = item.getMaxDamage();
            double dam_left = item.getMaxDamage() - item.getItemDamage();
            double percent = (dam_left / max_dam) * 100;

            if (percent >= takeOffVal.getValue() && !item.equals(Items.AIR)) {
                if (!notInInv(Items.AIR)) {
                    return;
                }
                if (delay_count < delay.getValue()) {
                    delay_count++;
                    return;
                }
                delay_count = 0;

                mc.playerController.windowClick(0, slot, 0, ClickType.QUICK_MOVE, mc.player);

            }
            slot++;
        }
    }

    public Boolean notInInv(Item itemOfChoice) {
        int n;
        n = 0;
        if (itemOfChoice == mc.player.getHeldItemOffhand().getItem()) return true;

        for (int i = 35; i >= 0; i--) {
            Item item = mc.player.inventory.getStackInSlot(i).getItem();
            if (item == itemOfChoice) {
                return true;

            } else if (item != itemOfChoice) {
                n++;
            }
        }
        if (n >= 35) {

            return false;
        }
        return true;
    }
}