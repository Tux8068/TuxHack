package me.ka1.vulcan.module.modules.player;

import me.ka1.vulcan.Vulcan;
import me.ka1.vulcan.module.Module;

public class Suicide extends Module {
    public Suicide() {
        super("Suicide", "/kills you lol", Category.Player);
    }

    public void onEnable() {
        this.disable();
        mc.player.sendChatMessage("/kill");


    }

}
