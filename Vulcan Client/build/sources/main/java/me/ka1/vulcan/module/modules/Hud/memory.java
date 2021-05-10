package me.ka1.vulcan.module.modules.Hud;

import me.ka1.vulcan.Vulcan;
import me.ka1.vulcan.module.Module;
import me.ka1.vulcan.setting.Setting;
import org.lwjgl.Sys;

import java.util.ArrayList;

public class memory extends Module {
    public memory() {
        super( "Memory", "Displays memory usage", Category.Hud);
    }

    public void onRender() {
    }
}
