package me.tux.tuxhack.module.modules.render;

import me.tux.tuxhack.module.Module;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.UUID;

public class Capes extends Module {

    public Capes() {
        super("Capes", Category.Render);
    }
    public static final ResourceLocation CAPE = new ResourceLocation("textures/cape.png");

    private final List<ResourceLocation> capeFrames = new ArrayList<>();

    public static int capeFrameCount = 0;

    static class gifCapeCounter extends TimerTask {
        @Override
        public void run() {
            capeFrameCount++;
        }
    }

    public ResourceLocation getGifCape() {
        return capeFrames.get(capeFrameCount % 35);
    }
    public static List<String> UUIDs = new ArrayList<String>() {
    };


    public static ResourceLocation getCapeResource() {
        return CAPE;
    }

    public static boolean hasCape(UUID uuid) {
        boolean retVal = false;
        for (String UUID : UUIDs) {
            if (uuid.toString().equals(UUID)) {
                retVal = true;
            }
        }
        return retVal;
    }

    public static String formatUUID(String input) {
        return input.replace("-", "").toLowerCase();
    }
}