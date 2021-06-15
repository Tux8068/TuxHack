package me.tux.tuxhack.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.tux.tuxhack.TuxHack;
import me.tux.tuxhack.command.Command;
import me.tux.tuxhack.util.config.Stopper;

public class ConfigCommand extends Command {

    @Override
    public String[] getAlias() {
        return new String[]{
                "config", "configs", "c" };
    }

    @Override
    public String getSyntax() {
        return "config < save > ";
    }

    @Override
    public void onCommand(String command, String[] args) {
        if (args[0].equalsIgnoreCase("load")){
            TuxHack.loadConfiguration.loadEnabled();
            TuxHack.loadConfiguration.loadBinds();
            TuxHack.loadConfiguration.loadGUI();
            TuxHack.loadConfiguration.loadFont();
            TuxHack.loadConfiguration.loadFriends();
            TuxHack.loadConfiguration.loadPrefix();
            TuxHack.loadModules.loadHud();
            TuxHack.loadModules.loadClient();
            TuxHack.loadModules.loadMisc();
            TuxHack.loadModules.loadCombat();
            TuxHack.loadModules.loadMovement();
            TuxHack.loadModules.loadPLAYER();
            TuxHack.loadModules.loadRender();
            Command.sendClientMessage(ChatFormatting.BOLD + "Loaded Config");
        }else if (args[0].equalsIgnoreCase("save")) {
                Stopper.saveConfig();
                sendClientMessage(ChatFormatting.BOLD + "Saved Config");
            } else sendClientMessage(getSyntax());
        }
    }



