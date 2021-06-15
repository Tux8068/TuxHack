package me.tux.tuxhack.module.modules.client;

import me.tux.tuxhack.module.Module;

public class LoadConfig extends Module {
    public LoadConfig() {
        super("LoadConfig", "loadConfig", Category.CLIENT);
    }

    public void onEnable() {
        if (mc.world != null && mc.world != null) {
            // load
            this.disable();
        }
    }
}