package me.ka1.vulcan.module.modules.render;

import me.ka1.vulcan.module.Module;
import me.ka1.vulcan.setting.Setting;

public class FpsLimiter extends Module {
   Setting.Integer maxFps;

   /*
   * @author _KA1 12 feb 2021
    */

    public FpsLimiter() {
        super("FPSLimiter", "fpsLimiter", Category.Client);
    }

    public void setup() {
        maxFps = registerInteger("Max Fps", "fpsLimiterMax", 240, 5, 1000);
    }

    public int onUpdate() {
        mc.gameSettings.limitFramerate = maxFps.getValue();
        return 0;
    }
    // why tf did i even make this lol
}
