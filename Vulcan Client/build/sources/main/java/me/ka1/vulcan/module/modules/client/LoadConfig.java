package me.ka1.vulcan.module.modules.client;

import me.ka1.vulcan.Vulcan;
import me.ka1.vulcan.module.Module;
import me.ka1.vulcan.util.config.LoadConfiguration;

public class LoadConfig extends Module {
    public LoadConfig() {
        super("LoadConfig", "loadConfig", Category.Client);
    }

    public void onEnable() {
        if (mc.world != null && mc.world != null) {
            // load
            this.disable();
        }
    }
}