package me.tux.tuxhack.command.commands;

import me.tux.tuxhack.command.Command;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.text.DecimalFormat;


public class BedCommand extends Command {
    @Override
    public String[] getAlias() {
        return new String[]{"BedCoord", "BedCoordinate", "BedCoords", "BedCoordinates", "Bed"};
    }

    @Override
    public String getSyntax() {
        return "Bed";
    }

    @Override
    public void onCommand(String command, String[] args) throws Exception {
        final DecimalFormat format = new DecimalFormat("#");
        final StringSelection contents = new StringSelection(Minecraft.getMinecraft().player.getBedLocation() + " !");
        final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(contents, null);
        sendRawMessage("Saved Bed Coords to Clipboard, use CTRL + V to paste.");
    }
}