package me.tux.tuxhack.module.modules.player;

import me.tux.tuxhack.module.Module;

public class Suicide extends Module {
    public Suicide() {
        super("Suicide", "/kills you lol", Category.Player);
    }

    public void onEnable() {
        this.disable();
        mc.player.sendChatMessage("/kill");


    }

}
