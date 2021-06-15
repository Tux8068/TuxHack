package me.tux.tuxhack.module.modules.client;

import me.tux.tuxhack.module.Module;
import net.minecraft.entity.item.EntityBoat;

import java.awt.*;
import java.net.URI;
import java.nio.file.Paths;

public class TeamTux extends Module{
    public TeamTux(){
        super("TeamTux", Category.CLIENT);
    }

    public String getDisplayInfo() {
        return null;
    }

    public void onEnable() {
        try {
            Desktop.getDesktop().browse(URI.create("https://discord.gg/VugXs9TESD"));
            Desktop.getDesktop().browse(URI.create("https://namemc.com/search?q=tuxiscool"));
            mc.player.sendChatMessage("Join team tux here! https://discord.gg/VugXs9TESD");

        }
        catch (Exception ex) {}
        this.disable();
    }
}