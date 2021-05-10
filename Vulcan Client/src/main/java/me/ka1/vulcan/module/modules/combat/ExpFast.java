package me.ka1.vulcan.module.modules.combat;

import me.ka1.vulcan.module.Module;
import net.minecraft.init.Items;


public class ExpFast extends Module {
    public ExpFast() {
        super("FastEXP", "Makes you place experience fast." , Category.Combat);
    }

    public int onUpdate() {
        if (mc.player == null || mc.world == null)
            return 0;

        if (mc.player.getHeldItemOffhand().getItem()  == Items.EXPERIENCE_BOTTLE || mc.player.getHeldItemMainhand().getItem() == Items.EXPERIENCE_BOTTLE)
            mc.rightClickDelayTimer = 0;
        else
            return 0;
        return 0;
    }
}