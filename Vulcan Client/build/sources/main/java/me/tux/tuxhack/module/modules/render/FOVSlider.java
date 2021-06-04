package me.tux.tuxhack.module.modules.render;

import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.module.ModuleManager;
import me.tux.tuxhack.setting.Setting;

public class FOVSlider extends Module {
    public FOVSlider() {
        super("FOVSlider","Lets you change your FOV" , Category.Render);
    }
    Setting.Double fov;
    // Setting.Boolean DynamicFOV;

    public boolean setup() {
        fov = registerDouble("FOV", "fov", 115, 25, 180);
      //DynamicFOV = registerBoolean("DynamicFOV", "DynamicFOV", false);
        return false;
    }
    @Override


    public void onRender() {
        if (!ModuleManager.isModuleEnabled("Zoom") && !(mc.gameSettings.fovSetting == ((float) fov.getValue()))){
            mc.gameSettings.fovSetting = ((float) fov.getValue());
        }

    }
}
//
//DynamicFOV is an optifine setting lol im a retard
