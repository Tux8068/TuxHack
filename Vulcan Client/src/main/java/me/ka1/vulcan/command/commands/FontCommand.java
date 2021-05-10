package me.ka1.vulcan.command.commands;

import me.ka1.vulcan.Vulcan;
import me.ka1.vulcan.command.Command;
import me.ka1.vulcan.util.font.CFontRenderer;

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
        Vulcan.fontRenderer = new CFontRenderer(new Font(font, Font.PLAIN, size), true, false);
        Vulcan.fontRenderer.setFontName(font);
        Vulcan.fontRenderer.setFontSize(size);
    }
}

