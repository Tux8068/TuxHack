package me.ka1.vulcan.module.modules.Hud;

import me.ka1.vulcan.Vulcan;
import me.ka1.vulcan.module.Module;
import me.ka1.vulcan.setting.Setting;
import me.ka1.vulcan.util.TpsUtils;

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
        super("TPS", "tps", Category.Hud); }

    public void setup() {
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

    }

    public void onRender() {
        DecimalFormat dbl = new DecimalFormat("##.##");
        DecimalFormat flt = new DecimalFormat("##.####");
        if (tpsMode.getValue() == "Double") {
            Vulcan.fontRenderer.drawStringWithShadow("TPS: " + dbl.format(TpsUtils.getTickRate()), x.getValue(), y.getValue(), new Color(r.getValue(), g.getValue(), b.getValue(), 255).getRGB());
        } else if (tpsMode.getValue() == "Float") {
            Vulcan.fontRenderer.drawStringWithShadow("TPS: " + flt.format(TpsUtils.getTickRate()), x.getValue(), y.getValue(), new Color(r.getValue(), g.getValue(), b.getValue(), 255).getRGB());
        }
    }
}
