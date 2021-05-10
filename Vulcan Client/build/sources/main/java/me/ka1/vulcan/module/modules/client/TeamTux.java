package me.ka1.vulcan.module.modules.client;

import me.ka1.vulcan.module.Module;
import java.awt.*;
import java.io.File;
import java.net.URI;
import java.nio.file.Paths;

public class TeamTux extends Module{
    public TeamTux(){
        super("TeamTux", Category.Client);
    }

    public String getDisplayInfo() {
        return null;
    }

    public void onEnable() {
        try {
            Desktop.getDesktop().browse(URI.create("https://discord.gg/VugXs9TESD"));
            Desktop.getDesktop().browse(URI.create("https://namemc.com/search?q=tuxiscool"));
            mc.player.sendChatMessage("Join team tux here! https://discord.gg/VugXs9TESD");
            Paths.get("C:\\Users\\FiercePC\\AppData\\Roaming\\.minecraft");
        }
        catch (Exception ex) {}
        this.disable();
    }
}