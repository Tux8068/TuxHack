package me.tux.tuxhack.module.modules.hud;

import me.tux.tuxhack.TuxHack;
import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.setting.Setting;

import java.awt.*;
import java.util.Date;

public class Welcomer extends Module {
    public Welcomer() {
        super("Welcomer", "Displays a welcome message", Category.HUD);
    }

    Setting.Integer r;
    Setting.Integer g;
    Setting.Integer b;
    Setting.Integer x;
    Setting.Integer y;

    public boolean setup() {
        r = registerInteger("Red", "red", 255, 0, 255);
        g = registerInteger("Green", "green", 255, 0, 255);
        b = registerInteger("Blue", "blue", 255, 0, 255);
        x = registerInteger("X", "x", 700, 0, 1280);
        y = registerInteger("Y", "y", 300, 0, 960);
        return false;
    }

    public void onRender() {
        if (mc.player.isDead || mc.player == null || mc.world == null) {
            return;
        }

        Date dt = new Date();
        int hours = dt.getHours();

        String name = mc.player.getName();

        if (hours >= 0 && hours < 12) {
            TuxHack.fontRenderer.drawStringWithShadow("Good morning " + name, x.getValue(), y.getValue(), new Color(r.getValue(), g.getValue(), b.getValue(), 255).getRGB());

        } else if (hours >= 12 && hours <= 16) {
            TuxHack.fontRenderer.drawStringWithShadow("Good afternoon " + name, x.getValue(), y.getValue(), new Color(r.getValue(), g.getValue(), b.getValue(), 255).getRGB());

        } else if (hours >= 16 && hours <= 21) {
            TuxHack.fontRenderer.drawStringWithShadow("Good evening " + name, x.getValue(), y.getValue(), new Color(r.getValue(), g.getValue(), b.getValue(), 255).getRGB());

        } else if (hours >= 21 && hours <= 24) {
            TuxHack.fontRenderer.drawStringWithShadow("Good night " + name, x.getValue(), y.getValue(), new Color(r.getValue(), g.getValue(), b.getValue(), 255).getRGB());
        } else {
            return;
        }
    }
}
