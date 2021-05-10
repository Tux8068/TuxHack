package me.ka1.vulcan.module.modules.player;

import me.ka1.vulcan.module.Module;
import net.minecraft.init.Items;

public class FastEXP extends Module {
    public FastEXP() {
        super("FastEXP", "Allows you to place exp at a higher rate", Category.Player);
    }



    public int onUpdate() {
        if (mc.player == null || mc.world == null)
            return 0;

        if (mc.player.inventory.getStackInSlot(mc.player.inventory.currentItem).getItem() == Items.EXPERIENCE_BOTTLE)
            mc.rightClickDelayTimer = 0;
        else
            return 0;
        return 0;
    }
}
