package me.tux.tuxhack.module.modules.misc;


import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.setting.Setting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemExpBottle;

public class FastPlace extends Module {
	public FastPlace(){super("FastPlace", "Increases your place speed", Category.Misc);}

	Setting.Boolean exp;
	Setting.Boolean crystals;
	Setting.Boolean breaking;
	Setting.Boolean placing;
	Setting.Boolean Echest;

	private Object BlockEnderChest;

	public boolean setup(){
		exp = registerBoolean("Exp", "Exp", false);
		crystals = registerBoolean("Crystals", "Crystals", false);
		breaking = registerBoolean("Breaking","Breaking",false);
		placing = registerBoolean("Placing","Placing",false);
		Echest = registerBoolean("EChest","EChest",false);
		return false;
	}

	public int onUpdate(){

		Item main = mc.player.getHeldItemMainhand().getItem();
		Item off = mc.player.getHeldItemOffhand().getItem();


		boolean main_exp = main instanceof ItemExpBottle;
		boolean off_exp = off instanceof ItemExpBottle;
		boolean main_cry = main instanceof ItemEndCrystal;
		boolean off_cry = off instanceof ItemEndCrystal;

		if (main_exp | off_exp && exp.getValue()){
			mc.rightClickDelayTimer = 0;
		}

		if (main_cry | off_cry && crystals.getValue()){
			mc.rightClickDelayTimer = 0;
		}

		if (placing.getValue()){
			mc.rightClickDelayTimer = 0;
		}

		if (breaking.getValue()) {
			mc.playerController.blockHitDelay = 0;
		}

		if (Echest.getValue() || mc.player.getHeldItemMainhand() == BlockEnderChest || mc.player.getHeldItemOffhand() == BlockEnderChest) {
			mc.rightClickDelayTimer = 0;
		}
		return 0;
	}
}