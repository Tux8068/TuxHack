package me.tux.tuxhack.command.commands;

import me.tux.tuxhack.command.Command;
import net.minecraft.util.text.TextFormatting;

public class HelpCommand extends Command {

    @Override
    public String[] getAlias() {
        return new String[]{
                "help", "h"
        };
    }
    String PREFIX;
    @Override
    public String getSyntax() {
        return null;
    }

    @Override
    public void onCommand(String command, String[] args) throws Exception {
        Command.sendRawMessage(TextFormatting.GOLD + "{" + TextFormatting.BOLD + TextFormatting.AQUA + "TuxHack" + TextFormatting.GOLD + "}" + TextFormatting.BOLD );
        Command.sendRawMessage("your prefix is " + TextFormatting.GREEN + Command.getPrefix());
        Command.sendRawMessage(TextFormatting.GREEN + "Bind, Config, Enemy, Font, Friend, Help, nmc, OpenFolder, Coords,  Credits.");

    }
}