package me.tux.tuxhack.module.modules.hud;

import me.tux.tuxhack.TuxHack;
import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.setting.Setting;
import me.tux.tuxhack.util.TpsUtils;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

/*
* @author _KA1 12th feb 2021
 */


public class TPS extends Module {
    Setting.Mode tpsMode;
    Setting.Integer r;
    Setting.Integer g;
    Setting.Integer b;
    Setting.Integer x;
    Setting.Integer y;

    public TPS() {
        super("TPS", "tps", Category.HUD); }

    public boolean setup() {
        ArrayList<String> tpsModes;
        tpsModes = new ArrayList<>();
        tpsModes.add("Double");
        tpsModes.add("Float");

        tpsMode = registerMode("Precision", "tpsMode", tpsModes, "Double");
        r = registerInteger("Red", "red", 255, 0, 255);
        g = registerInteger("Green", "green", 255, 0, 255);
        b = registerInteger("Blue", "blue", 255, 0, 255);
        x = registerInteger("X", "xTps", 0, 0, 1280);
        y = registerInteger("Y", "yTps", 50, 0, 960);

        return false;
    }

    public void onRender() {
        DecimalFormat dbl = new DecimalFormat("##.##");
        DecimalFormat flt = new DecimalFormat("##.####");
        if (tpsMode.getValue() == "Double") {
            TuxHack.fontRenderer.drawStringWithShadow("TPS: " + dbl.format(TpsUtils.getTickRate()), x.getValue(), y.getValue(), new Color(r.getValue(), g.getValue(), b.getValue(), 255).getRGB());
        } else if (tpsMode.getValue() == "Float") {
            TuxHack.fontRenderer.drawStringWithShadow("TPS: " + flt.format(TpsUtils.getTickRate()), x.getValue(), y.getValue(), new Color(r.getValue(), g.getValue(), b.getValue(), 255).getRGB());
        }
    }
}
