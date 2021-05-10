package me.ka1.vulcan.module.modules.Hud;

import me.ka1.vulcan.Vulcan;
import me.ka1.vulcan.module.Module;
import me.ka1.vulcan.setting.Setting;

import java.awt.*;

import static net.minecraft.client.Minecraft.getDebugFPS;

public class FPS extends Module {
        public FPS() {
            super("FPS", "fps", Category.Hud);
        }

    Setting.Integer r;
    Setting.Integer g;
    Setting.Integer b;
    Setting.Integer x;
    Setting.Integer y;
    int var1;

        public void setup() {
            r = registerInteger("Red", "redFps", 255, 0, 255);
            g = registerInteger("Green", "greenFps", 255, 0, 255);
            b = registerInteger("Blue", "blueFps", 255, 0, 255);
            x = registerInteger("X", "xFps", 0, 0, 1280);
            y = registerInteger("Y", "yFps", 20, 0, 960);
        }


        public void onRender() {
            var1 = getDebugFPS();
            String toPrint = "FPS: " + var1;
        Vulcan.fontRenderer.drawStringWithShadow(toPrint , x.getValue(), y.getValue(), new Color(r.getValue(), g.getValue(), b.getValue(), 255).getRGB());
        }
    }




