package me.tux.tuxhack.module.modules.render;

import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.setting.Setting;

public class FpsLimiter extends Module {
   Setting.Integer maxFps;

   /*
   * @author _KA1 12 feb 2021
    */

    public FpsLimiter() {
        super("FPSLimiter", "fpsLimiter", Category.Client);
    }

    public boolean setup() {
        maxFps = registerInteger("Max Fps", "fpsLimiterMax", 240, 5, 1000);
        return false;
    }

    public int onUpdate() {
        mc.gameSettings.limitFramerate = maxFps.getValue();
        return 0;
    }
    // why tf did i even make this lol
}
