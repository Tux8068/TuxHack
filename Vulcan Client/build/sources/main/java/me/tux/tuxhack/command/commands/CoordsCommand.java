package me.tux.tuxhack.command.commands;

import me.tux.tuxhack.command.Command;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.text.DecimalFormat;


public class CoordsCommand extends Command {
    @Override
    public String[] getAlias() {
        return new String[]{"Coord", "Coordinate", "Coords", "Coordinates", "pos"};
    }

    @Override
    public String getSyntax() {
        return "coords";
    }

    @Override
    public void onCommand(String command, String[] args) throws Exception {
        final DecimalFormat format = new DecimalFormat("#");
        final StringSelection contents = new StringSelection("X: "+format.format(Minecraft.getMinecraft().player.posX) + " Y: "+ format.format(Minecraft.getMinecraft().player.posY) + " Z: "+ format.format(Minecraft.getMinecraft().player.posZ));
        final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(contents, null);
        sendRawMessage("Saved Coords to Clipboard, use CTRL + V to paste.");
    }


}

