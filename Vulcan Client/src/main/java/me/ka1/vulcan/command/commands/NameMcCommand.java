package me.ka1.vulcan.command.commands;

import me.ka1.vulcan.command.Command;

import java.awt.*;
import java.net.URI;

public class NameMcCommand extends Command {
    String name;
    @Override
    public String[] getAlias() {
        return new String[]{
                "nmc", "namecheck", "name","namemc"
        };
    }

    @Override
    public String getSyntax() {
        return "font <Name> <Size>";
    }

    @Override
    public void onCommand(String command, String[] args) throws Exception {
        name = args[0];
        Desktop.getDesktop().browse(URI.create("https://namemc.com/search?q=" + name));

    }
    }