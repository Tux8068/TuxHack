package me.tux.tuxhack.module.modules.hud;

import me.tux.tuxhack.TuxHack;
import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.setting.Setting;

import java.awt.*;

import static net.minecraft.client.Minecraft.getDebugFPS;

public class FPS extends Module {
        public FPS() {
            super("FPS", "fps", Category.HUD);
        }

    Setting.Integer r;
    Setting.Integer g;
    Setting.Integer b;
    Setting.Integer x;
    Setting.Integer y;
    int var1;

        public boolean setup() {
            r = registerInteger("Red", "redFps", 255, 0, 255);
            g = registerInteger("Green", "greenFps", 255, 0, 255);
            b = registerInteger("Blue", "blueFps", 255, 0, 255);
            x = registerInteger("X", "xFps", 0, 0, 1280);
            y = registerInteger("Y", "yFps", 20, 0, 960);
            return false;
        }


        public void onRender() {
            var1 = getDebugFPS();
            String toPrint = "FPS: " + var1;
        TuxHack.fontRenderer.drawStringWithShadow(toPrint , x.getValue(), y.getValue(), new Color(r.getValue(), g.getValue(), b.getValue(), 255).getRGB());
        }
    }




