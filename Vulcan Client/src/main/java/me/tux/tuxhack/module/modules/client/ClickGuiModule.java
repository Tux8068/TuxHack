package me.tux.tuxhack.module.modules.client;

import me.tux.tuxhack.TuxHack;
import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.setting.Setting;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class ClickGuiModule extends Module{
    public ClickGuiModule() {
        super("ClickGUI", Category.CLIENT);
        setDrawn(false);
        setBind(Keyboard.KEY_P);
    }
    public static int color;
    public static Setting.Integer opacity;
    public static Setting.Integer red;
    public static Setting.Integer green;
    public static Setting.Integer blue;
    public static Setting.Integer fontRed;
    public static Setting.Integer fontGreen;
    public static Setting.Integer fontBlue;

    public boolean setup(){
        red = registerInteger("Red", "RedHUD", 255, 0, 255);
        green = registerInteger("Green", "GreenHUD", 255, 0, 255);
        blue = registerInteger("Blue", "BlueHUD", 255, 0, 255);
        fontRed = registerInteger("Font Red", "RedFONT", 255, 0, 255);
        fontGreen = registerInteger("Font Green", "GreenFONT", 255, 0, 255);
        fontBlue = registerInteger("Font Blue", "BlueFONT", 255, 0, 255);
        opacity = registerInteger("Opacity", "Opacity", 200,50,255);
        color = new Color(fontRed.getValue(), fontBlue.getValue(), fontGreen.getValue(), opacity.getValue()).getRGB();
        return false;
    }

    public void onEnable(){
        mc.displayGuiScreen(TuxHack.getInstance().clickGUI);
        disable();
    }
}
