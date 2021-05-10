package me.ka1.vulcan.module.modules.player;

import me.ka1.vulcan.module.Module;
import net.minecraft.init.Items;


public class FastCrystal extends Module {
    public FastCrystal() {
        super("FastCrystal", "mc.rightClickDelayTimer = 0;", Category.Player);
    }



    public int onUpdate() {
        if (mc.player == null || mc.world == null)
            return 0;

        if (mc.player.getHeldItemOffhand().getItem()  == Items.END_CRYSTAL || mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL)
            mc.rightClickDelayTimer = 0;
        else
            return 0;
        return 0;
    }
}