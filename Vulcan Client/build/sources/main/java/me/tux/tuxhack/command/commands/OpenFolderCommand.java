package me.tux.tuxhack.command.commands;

import me.tux.tuxhack.command.Command;

import java.awt.*;
import java.io.File;

public class OpenFolderCommand extends Command {
    @Override
    public String[] getAlias() {
        return new String[]{"openfolder", "folder"};
    }

    @Override
    public String getSyntax() {
        return "openfolder";
    }

    @Override
    public void onCommand(String command, String[] args) throws Exception {
        try {
            Desktop.getDesktop().open(new File("Vulcan"));
        } catch(Exception e){sendClientMessage("Error: " + e.getMessage());}
    }
}
