package me.tux.tuxhack.module.modules.chat;

import me.tux.tuxhack.module.Module;

public class AutoTPA extends Module {
    public AutoTPA() {
        super("AutoTPA", "automatically /tpa's you to AnarchyBOT", Category.Chat);
    }

    public void onEnable() {
        this.disable();
        mc.player.sendChatMessage("/tpa AnarchyB0T");


    }

}
