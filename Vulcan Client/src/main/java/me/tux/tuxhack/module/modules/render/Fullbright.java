package me.tux.tuxhack.module.modules.render;

import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.setting.Setting;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.util.ArrayList;

public class Fullbright extends Module {
    public Fullbright() {
        super("Fullbright", "Brightens up your world", Category.RENDER);
    }

    Setting.Mode fullbrightMode;

    @Override
    public boolean setup() {
        ArrayList<String> Modes = new ArrayList<>();
        Modes.add("Potion");
        Modes.add("Gamma");

      fullbrightMode = registerMode("Mode", "fullbrightMode", Modes, "Potion");
        return false;
    }

    @Override
    public void onRender() {
        if (fullbrightMode.getValue().equals("Potion")) {
            final PotionEffect NightVis = new PotionEffect(Potion.getPotionById(16), 696969696, 5);
            NightVis.setPotionDurationMax(true);
            mc.player.addPotionEffect(NightVis);
        } else if (fullbrightMode.getValue().equals("Gamma")) {
            mc.gameSettings.gammaSetting = 420f;
            mc.player.removePotionEffect(Potion.getPotionById(16));
        }
    }

}

