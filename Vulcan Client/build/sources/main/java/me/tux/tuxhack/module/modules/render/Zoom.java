package me.tux.tuxhack.module.modules.render;

import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.setting.Setting;

public class Zoom extends Module {
    public Zoom() {
        super("Zoom", "Zooms In", Category.Render);
    }

    Setting.Double fov;
    Setting.Boolean smooth;
    public float oldVal;

    @Override
    public boolean setup() {
        fov = registerDouble("FOV", "fov", 25, 10, 100);
        smooth = registerBoolean("Cinematic", "cinematic", true);
        return false;
    }

    @Override
    protected void onEnable() {
        oldVal = mc.gameSettings.fovSetting;
        mc.gameSettings.smoothCamera = smooth.getValue();
        mc.gameSettings.fovSetting = ((float) fov.getValue());
        super.onEnable();
    }

    @Override
    protected int onDisable() {
        mc.gameSettings.fovSetting = oldVal;
        mc.gameSettings.smoothCamera = false;
        super.onDisable();
        return 0;
    }

}
