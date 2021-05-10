package me.ka1.vulcan.module.modules.Hud;

import me.ka1.vulcan.Vulcan;
import me.ka1.vulcan.module.Module;
import me.ka1.vulcan.setting.Setting;

import java.awt.*;
import java.util.ArrayList;

import static net.minecraft.client.Minecraft.getDebugFPS;

public class Watermark extends Module {
    Setting.Integer r;
    Setting.Integer g;
    Setting.Integer b;
    Setting.Integer x;
    Setting.Integer y;
    Setting.Mode mode;
    Setting.Boolean onion;

    public Watermark() {
        super("Watermark", "displays the modname and ver (optional)", Category.Hud); }

    public void setup() {
        ArrayList<String> Modes;
        Modes = new ArrayList<>();
        Modes.add(Vulcan.MOD_NAME);
        Modes.add(Vulcan.MOD_NAME + " " + Vulcan.VERSION);
        Modes.add("OnionWare");

        mode = registerMode("Mode", "watermarkMode", Modes, Vulcan.MOD_NAME);
        r = registerInteger("Red", "red", 255, 0, 255);
        g = registerInteger("Green", "green", 255, 0, 255);
        b = registerInteger("Blue", "blue", 255, 0, 255);
        x = registerInteger("X", "xWaternark", 1, 0, 1280);
        y = registerInteger("Y", "yWatermark", 1, 0, 960);
        onion = registerBoolean("OnionMode", "onion", false);
    }
    public void onRender() {
        if (onion.getValue()) {
            Vulcan.fontRenderer.drawStringWithShadow("OnionWareMc V1 | " + getDebugFPS() + " | " + mc.player.getName() , x.getValue(), y.getValue(), new Color(r.getValue(), g.getValue(), b.getValue(), 255).getRGB());
        }else {
            Vulcan.fontRenderer.drawStringWithShadow(mode.getValue() , x.getValue(), y.getValue(), new Color(r.getValue(), g.getValue(), b.getValue(), 255).getRGB());
        }
    }
}
