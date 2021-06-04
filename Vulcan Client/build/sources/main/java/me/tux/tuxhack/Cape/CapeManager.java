package me.tux.tuxhack.Cape;


import me.tux.tuxhack.module.modules.render.Capes;
import me.tux.tuxhack.util.Timer;
import net.minecraft.util.ResourceLocation;
import org.jline.utils.InputStreamReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class CapeManager {

    private final List<ResourceLocation> capeFrames = new ArrayList<>();

    public static int capeFrameCount = 0;

    static class gifCapeCounter extends TimerTask {
        @Override
        public void run() {
            capeFrameCount++;
        }
    }
    public CapeManager() {
        Timer timer = new Timer();


        for (int i = 0; i < 35; i++) {
            capeFrames.add(new ResourceLocation("textures/gifcape/cape-" + i + ".png"));
        }
    }
    public ResourceLocation getGifCape() {
        return capeFrames.get(capeFrameCount % 35);
    }

    private static String getCapeUUIDs() throws IOException {
        URL u = new URL("https://raw.githubusercontent.com/Tux8068/Capes/main/CapeUsers.txt");
        URLConnection conn = u.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        conn.getInputStream()));
        StringBuffer buffer = new StringBuffer();
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            buffer.append(inputLine).append("\n");
        in.close();
        return buffer.toString();
    }

    public static void init() throws IOException {
        String[] VIPS = getCapeUUIDs().split("\n");
        for (String VIP : VIPS) {
            String[] info = VIP.split("\\s");
            String uuid = info[0];
            Capes.UUIDs.add(uuid);
        }

    }
}