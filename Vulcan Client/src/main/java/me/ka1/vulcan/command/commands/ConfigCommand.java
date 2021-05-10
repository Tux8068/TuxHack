package me.ka1.vulcan.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.ka1.vulcan.Vulcan;
import me.ka1.vulcan.command.Command;
import me.ka1.vulcan.util.config.LoadConfiguration;
import me.ka1.vulcan.util.config.Stopper;

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
            Vulcan.loadConfiguration.loadEnabled();
            Vulcan.loadConfiguration.loadBinds();
            Vulcan.loadConfiguration.loadGUI();
            Vulcan.loadConfiguration.loadFont();
            Vulcan.loadConfiguration.loadFriends();
            Vulcan.loadConfiguration.loadPrefix();
            Vulcan.loadModules.loadHud();
            Vulcan.loadModules.loadClient();
            Vulcan.loadModules.loadMisc();
            Vulcan.loadModules.loadCombat();
            Vulcan.loadModules.loadMovement();
            Vulcan.loadModules.loadPlayer();
            Vulcan.loadModules.loadRender();
            Command.sendClientMessage(ChatFormatting.BOLD + "Loaded Config");
        }else if (args[0].equalsIgnoreCase("save")) {
                Stopper.saveConfig();
                sendClientMessage(ChatFormatting.BOLD + "Saved Config");
            } else sendClientMessage(getSyntax());
        }
    }



