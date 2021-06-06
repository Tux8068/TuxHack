package me.tux.tuxhack.module.modules.hud;

import me.tux.tuxhack.TuxHack;
import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.setting.Setting;

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

    public boolean setup() {
        ArrayList<String> Modes;
        Modes = new ArrayList<>();
        Modes.add(TuxHack.MOD_NAME);
        Modes.add(TuxHack.MOD_NAME + " " + TuxHack.VERSION);
        Modes.add("OnionWare");

        mode = registerMode("Mode", "watermarkMode", Modes, TuxHack.MOD_NAME);
        r = registerInteger("Red", "red", 255, 0, 255);
        g = registerInteger("Green", "green", 255, 0, 255);
        b = registerInteger("Blue", "blue", 255, 0, 255);
        x = registerInteger("X", "xWaternark", 1, 0, 1280);
        y = registerInteger("Y", "yWatermark", 1, 0, 960);
        onion = registerBoolean("OnionMode", "onion", false);
        return false;
    }
    public void onRender() {
        if (onion.getValue()) {
            TuxHack.fontRenderer.drawStringWithShadow("OnionWareMc V1 | " + getDebugFPS() + " | " + mc.player.getName() , x.getValue(), y.getValue(), new Color(r.getValue(), g.getValue(), b.getValue(), 255).getRGB());
        }else {
            TuxHack.fontRenderer.drawStringWithShadow(mode.getValue() , x.getValue(), y.getValue(), new Color(r.getValue(), g.getValue(), b.getValue(), 255).getRGB());
        }
    }
}
