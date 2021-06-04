package me.tux.tuxhack.module.modules.Hud;

import me.tux.tuxhack.TuxHack;
import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.setting.Setting;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Time extends Module {
    public Time() {
        super("Time", "Displays the time", Category.Hud);
    }

    Setting.Mode timeFormat;
    Setting.Integer r;
    Setting.Integer g;
    Setting.Integer b;
    Setting.Integer x;
    Setting.Integer y;


    public boolean setup() {
        ArrayList<String> timeFormats;
        timeFormats = new ArrayList<>();
        timeFormats.add("24hr");
        timeFormats.add("12hr");

        timeFormat = registerMode("Time Format", "Format", timeFormats, "24hr");
        r = registerInteger("Red", "red", 255, 0, 255);
        g = registerInteger("Green", "green", 255, 0, 255);
        b = registerInteger("Blue", "blue", 255, 0, 255);
        x = registerInteger("X", "x", 0, 0, 1280);
        y = registerInteger("Y", "y", 40, 0, 960);
        return false;
    }

    public void onRender() {
        DateFormat twoFour = new SimpleDateFormat("hh:mm aa"); //24hr format
        DateFormat oneTwo = new SimpleDateFormat("HH:mm"); // 12hr format

        Date dt = new Date();
        int hours = dt.getHours();
        int minutes = dt.getMinutes();

        if (timeFormat.getValue().equalsIgnoreCase("24hr")) {
            String time = twoFour.format(dt);
            TuxHack.fontRenderer.drawStringWithShadow(time, x.getValue(), y.getValue(), new Color(r.getValue(), g.getValue(), b.getValue(), 255).getRGB());
        } else if (timeFormat.getValue().equalsIgnoreCase("12hr")) {
            String time = oneTwo.format(dt);
            TuxHack.fontRenderer.drawStringWithShadow(time, x.getValue(), y.getValue(), new Color(r.getValue(), g.getValue(), b.getValue(), 255).getRGB());
        }

    }
}
