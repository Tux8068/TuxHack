package me.ka1.vulcan.module.modules.Hud;

import me.ka1.vulcan.Vulcan;
import me.ka1.vulcan.module.Module;
import me.ka1.vulcan.setting.Setting;

import java.awt.*;
import java.util.Objects;

public class ping extends Module {
    Setting.Integer r;
    Setting.Integer g;
    Setting.Integer b;
    Setting.Integer x;
    Setting.Integer y;

    public ping() {
        super("Ping", "ping", Category.Hud);
    }

    public void setup() {
        r = registerInteger("Red", "redPing", 255, 0, 255);
        g = registerInteger("Green", "greenPing", 255, 0, 255);
        b = registerInteger("Blue", "bluePing", 255, 0, 255);
        x = registerInteger("X", "xPing", 0, 0, 1280);
        y = registerInteger("Y", "yPing", 30, 0, 960);
    }
    public int getPing() {
        int p = -1;
        if (mc.player == null || mc.getConnection() == null || mc.getConnection().getPlayerInfo(mc.player.getName()) == null) {
        } else {
            p = Objects.requireNonNull(mc.getConnection().getPlayerInfo(mc.player.getName())).getResponseTime();
        }
        return p;
    }

    public void onRender() {
        Vulcan.fontRenderer.drawStringWithShadow(getPing() + "ms" , x.getValue(), y.getValue(), new Color(r.getValue(), g.getValue(), b.getValue(), 255).getRGB());
    }
}
