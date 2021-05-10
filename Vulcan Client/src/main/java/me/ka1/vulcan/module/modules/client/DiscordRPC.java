package me.ka1.vulcan.module.modules.client;

import me.ka1.vulcan.VulcanRPC;
import me.ka1.vulcan.module.Module;
import me.ka1.vulcan.setting.Setting;

import java.util.ArrayList;

public class DiscordRPC extends Module {
    public DiscordRPC() {
        super("DiscordRPC", "Displays a customisable RPC", Category.Client);
    }

   public static Setting.Mode logoMode;
   public static Setting.Mode presenceState;
   public static Setting.Boolean server;

    public void setup() {

        ArrayList<String> logoModes;
        logoModes = new ArrayList<>();
        logoModes.add("Normal");
        logoModes.add("Transparent");
        //logoModes.add("Cute"); make extra logo?

        ArrayList<String> stateModes;
        stateModes = new ArrayList<>();
        stateModes.add("TuxHack owns 00");
        stateModes.add("Tuxiscool is based");
        stateModes.add("Hey :D");
        stateModes.add(":^)");
        stateModes.add("farming right now");
        stateModes.add("AFK!");

        logoMode = registerMode("Logo Mode", "RpcLogoMode", logoModes, "Normal");
        presenceState = registerMode("Text", "presenceState", stateModes, "Tuxiscool is based");
        server = registerBoolean("Server", "serverRpc", true);
    }
    public void onEnable() {
        VulcanRPC.init();
    }
    public void onDisable() {
        VulcanRPC.shutdown();
    }
}
