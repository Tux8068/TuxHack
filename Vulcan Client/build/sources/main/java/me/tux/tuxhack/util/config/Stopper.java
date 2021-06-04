package me.tux.tuxhack.util.config;

import me.tux.tuxhack.TuxHack;

public class Stopper extends Thread {

    @Override
    public void run(){
        saveConfig();
    }

    public static void saveConfig(){

        TuxHack.getInstance().saveModules.saveModules();

        SaveConfiguration.saveBinds();
        SaveConfiguration.saveDrawn();
        SaveConfiguration.saveEnabled();
        SaveConfiguration.saveEnemies();
        SaveConfiguration.saveFont();
        SaveConfiguration.saveFriends();
        SaveConfiguration.saveGUI();
        SaveConfiguration.saveMacros();
        SaveConfiguration.saveMessages();
        SaveConfiguration.savePrefix();
    }
}
