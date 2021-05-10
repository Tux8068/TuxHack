package me.ka1.vulcan.module.modules.player;

import me.ka1.vulcan.command.Command;
import me.ka1.vulcan.module.Module;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AbstractChestHorse;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import java.util.Comparator;

public class OldFagDupe extends Module {
    public OldFagDupe() {
        super("OldFagDupe", "allows you to dupe saddles on oldfag.org", Module.Category.Player);
    }

    public void setup() {

    }

    Entity donkey;

    public int onUpdate() {
        donkey = mc.world.loadedEntityList.stream()
                .filter(this::isValidEntity)
                .min(Comparator.comparing(p_Entity -> mc.player.getDistance(p_Entity)))
                .orElse(null);

        putChestOn();
        Command.sendRawMessage("Chest on donk");
        this.toggle();

        return 0;
    }

    public void putChestOn() {
        mc.player.inventory.currentItem = findAirInHotbar();
        mc.player.inventory.currentItem = findChestInHotbar();
        mc.playerController.interactWithEntity(mc.player, donkey, EnumHand.MAIN_HAND);
    }

    private int findChestInHotbar() {

        // search blocks in hotbar
        int slot = -1;
        for (int i = 0; i < 9; i++) {

            // filter out non-block items
            ItemStack stack = mc.player.inventory.getStackInSlot(i);

            if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBlock)) {
                continue;
            }

            Block block = ((ItemBlock) stack.getItem()).getBlock();
            if (block instanceof BlockChest) {
                slot = i;
                break;
            }
        }
        return slot;
    }

    private int findAirInHotbar() {

        // search blocks in hotbar
        int slot = -1;
        for (int i = 0; i < 9; i++) {

            // filter out non-block items
            ItemStack stack = mc.player.inventory.getStackInSlot(i);

            if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBlock)) {
                continue;
            }

            Block block = ((ItemBlock) stack.getItem()).getBlock();
            if (block instanceof BlockAir) {
                slot = i;
                break;
            }
        }
        return slot;
    }

    private boolean isValidEntity(Entity entity) {
        if (entity instanceof AbstractChestHorse) {
            AbstractChestHorse donkey = (AbstractChestHorse) entity;

            return !donkey.isChild() && donkey.isTame();
        }
        return false;
    }
}
