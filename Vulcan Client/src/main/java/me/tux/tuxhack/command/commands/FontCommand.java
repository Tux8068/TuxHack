package me.tux.tuxhack.command.commands;

import me.tux.tuxhack.command.Command;
import me.tux.tuxhack.TuxHack;
import me.tux.tuxhack.util.font.CFontRenderer;

import java.awt.*;

public class FontCommand extends Command {
    @Override
    public String[] getAlias() {
        return new String[]{
                "font", "setfont"
        };
    }

    @Override
    public String getSyntax() {
        return "font <Name> <Size>";
    }

    @Override
    public void onCommand(String command, String[] args) throws Exception {
        String font = args[0].replace("_", " ");
        int size = Integer.parseInt(args[1]);
        TuxHack.fontRenderer = new CFontRenderer(new Font(font, Font.PLAIN, size), true, false);
        TuxHack.fontRenderer.setFontName(font);
        TuxHack.fontRenderer.setFontSize(size);
    }
}

