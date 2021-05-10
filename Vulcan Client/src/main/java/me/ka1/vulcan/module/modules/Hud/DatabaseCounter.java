package me.ka1.vulcan.module.modules.Hud;

import me.ka1.vulcan.Vulcan;
import me.ka1.vulcan.module.Module;
import me.ka1.vulcan.setting.Setting;

import java.awt.*;
import java.io.File;

public class DatabaseCounter extends Module {
    public DatabaseCounter() {
        super("DatabaseCounter", "Displays how many databases you have loaded", Module.Category.Hud);
    }

    Setting.Integer r;
    Setting.Integer g;
    Setting.Integer b;
    Setting.Integer x;
    Setting.Integer y;
    Setting.Boolean justnum;
    int dbs;

    public void setup() {
        r = registerInteger("Red", "redaa", 255, 0, 255);
        g = registerInteger("Green", "greenaa", 255, 0, 255);
        b = registerInteger("Blue", "blueaa", 255, 0, 255);
        x = registerInteger("X", "xaa", 0, 0, 1280);
        y = registerInteger("Y", "yaa", 10, 0, 960);
        justnum = registerBoolean("JustNumber", "only displays the number of databases", false);

        File file = new File(mc.gameDir + File.separator + "Vulcan" + File.separator + "Databases");
        if (!file.exists()) {
            file.mkdir();
        }
    }

    public void onEnable() {
        dbs = 0;
        File f = new File(mc.gameDir + File.separator + "Vulcan" + File.separator + "Databases");
        String[] files = f.list();

        for (String file : files) {
            dbs += 1;
        }
    }

    public void onRender() {
        if (justnum.getValue()) {
            Vulcan.fontRenderer.drawStringWithShadow(String.valueOf(dbs), x.getValue(), y.getValue(), new Color(r.getValue(), g.getValue(), b.getValue()).getRGB());
        }else{
            Vulcan.fontRenderer.drawStringWithShadow("Databases loaded - " + dbs, x.getValue(), y.getValue(), new Color(r.getValue(), g.getValue(), b.getValue()).getRGB());
        }
    }
}
