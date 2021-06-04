package me.tux.tuxhack.command.commands;

import me.tux.tuxhack.command.Command;
import net.minecraft.util.text.TextFormatting;

public class CreditsCommand extends Command {

    @Override
    public String[] getAlias() {
        return new String[]{
                "credits", "creds"
        };
    }
    String PREFIX;
    @Override
    public String getSyntax() {
        return null;
    }

    @Override
    public void onCommand(String command, String[] args) throws Exception {

        Command.sendRawMessage(TextFormatting.GREEN + "Tuxiscool#6456 for tuxhack <3, _KA1 for Vulcan client, gamesense PistonAura, 2Q1 for packetmine");

    }
}