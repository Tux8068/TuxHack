package me.ka1.vulcan.module.modules.render;

import me.ka1.vulcan.module.Module;
import me.ka1.vulcan.setting.Setting;

public class Zoom extends Module {
    public Zoom() {
        super("Zoom", "Zooms In", Category.Render);
    }

    Setting.Double fov;
    Setting.Boolean smooth;
    public float oldVal;

    @Override
    public void setup() {
        fov = registerDouble("FOV", "fov", 25, 10, 100);
        smooth = registerBoolean("Cinematic", "cinematic", true);
    }

    @Override
    protected void onEnable() {
        oldVal = mc.gameSettings.fovSetting;
        mc.gameSettings.smoothCamera = smooth.getValue();
        mc.gameSettings.fovSetting = ((float) fov.getValue());
        super.onEnable();
    }

    @Override
    protected void onDisable() {
        mc.gameSettings.fovSetting = oldVal;
        mc.gameSettings.smoothCamera = false;
        super.onDisable();
    }

}
