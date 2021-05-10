package me.ka1.vulcan.module.modules.Hud;

import me.ka1.vulcan.Vulcan;
import me.ka1.vulcan.module.Module;
import me.ka1.vulcan.setting.Setting;

import java.awt.*;

public class willy extends Module {
    public willy() {
        super("willy", "funny willy lol", Category.Hud);
    }

    Setting.Integer r;
    Setting.Integer g;
    Setting.Integer b;
    Setting.Integer x;
    Setting.Integer y;
    Setting.Boolean anal;

    public void setup() {
        anal = registerBoolean("willylol", "willy", false);

        r = registerInteger("Red", "redwilly", 255, 0, 255);
        g = registerInteger("Green", "greenwilly", 255, 0, 255);
        b = registerInteger("Blue", "bluewilly", 255, 0, 255);
        x = registerInteger("X", "xwilly", 0, 0, 1280);
        y = registerInteger("Y", "ywilly", 90, 0, 960);
    }

    public void onRender() {
        if (anal.getValue() && mc.player.posY == 69) {
            Vulcan.fontRenderer.drawStringWithShadow("anal", x.getValue(), y.getValue(), new Color(r.getValue(), g.getValue(), b.getValue()).getRGB());
        }else
            {
            Vulcan.fontRenderer.drawStringWithShadow("fortnite", x.getValue(), y.getValue(), new Color(r.getValue(), g.getValue(), b.getValue()).getRGB());
        }
    }
}
