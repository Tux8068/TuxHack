package me.ka1.vulcan;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import net.minecraft.client.Minecraft;

public class VulcanRPC {
    private static final String ClientId = "832760557433257994";
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static final DiscordRPC rpc = DiscordRPC.INSTANCE;
    public static DiscordRichPresence presence = new DiscordRichPresence();
    private static String details;
    private static String state;

    public static void init() {
        final DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.disconnected = ((var1, var2) -> System.out.println("Discord RPC disconnected, var1: " + String.valueOf(var1) + ", var2: " + var2));
        rpc.Discord_Initialize(ClientId, handlers, true, "");
        presence.startTimestamp = System.currentTimeMillis() / 1000L;
        presence.details = Vulcan.MOD_NAME + " " + Vulcan.VERSION;
        presence.state = me.ka1.vulcan.module.modules.client.DiscordRPC.presenceState.getValue(); // also make custom
        presence.largeImageKey = me.ka1.vulcan.module.modules.client.DiscordRPC.logoMode.getValue().toLowerCase(); // vulcan logo *** add transparent more | normal mode ***?
        presence.largeImageText = Vulcan.MOD_NAME + " " + Vulcan.VERSION;
        presence.smallImageKey =  "transparent";  //mc.getSession().getUsername().toLowerCase()
         presence.smallImageText = mc.getSession().getUsername();

        rpc.Discord_UpdatePresence(presence);
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    rpc.Discord_RunCallbacks();
                    details = Vulcan.MOD_NAME + Vulcan.VERSION;
                    state = me.ka1.vulcan.module.modules.client.DiscordRPC.presenceState.getValue();
                    if (mc.isIntegratedServerRunning()) {
                        details = "Singleplayer";
                    }
                    else if (mc.getCurrentServerData() != null && me.ka1.vulcan.module.modules.client.DiscordRPC.server.getValue()) {
                        if (!mc.getCurrentServerData().serverIP.equals("")) {
                            details = "pvping noobs @ " + mc.getCurrentServerData().serverIP;
                        }
                    } else {
                        details = " in main menu :^)";
                    }
                    if (!details.equals(presence.details) || !state.equals(presence.state)) {
                        presence.startTimestamp = System.currentTimeMillis() / 1000L;
                    }
                    presence.details = details;
                    presence.state = state;
                    rpc.Discord_UpdatePresence(presence);
                } catch(Exception e2){
                    e2.printStackTrace();
                }
                try {
                    Thread.sleep(5000L);
                } catch(InterruptedException e3){
                    e3.printStackTrace();
                }
            }
            return;
        }, "Discord-RPC-Callback-Handler").start();
    }

    public static void shutdown() {
        rpc.Discord_Shutdown();
    }
}

