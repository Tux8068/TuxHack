package me.ka1.vulcan.module.modules.chat;

import me.ka1.vulcan.module.Module;

public class AutoTPA extends Module {
    public AutoTPA() {
        super("AutoTPA", "automatically /tpa's you to AnarchyBOT", Category.Player);
    }

    public void onEnable() {
        this.disable();
        mc.player.sendChatMessage("/tpa AnarchyB0T");


    }

}
