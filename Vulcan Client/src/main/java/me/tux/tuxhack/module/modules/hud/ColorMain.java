package me.tux.tuxhack.module.modules.hud;

import java.awt.Color;

import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.setting.Setting;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;

public class ColorMain extends Module
{
    public static Setting.Boolean rainbow;
    public static Setting.Integer Red;
    public static Setting.Integer Blue;
    public static Setting.Integer Green;
    public static Setting.Mode friendcolor;
    public static Setting.Mode enemycolor;

    public ColorMain() {
        super("Colors", Module.Category.HUD);
        this.setDrawn(false);
    }

    @Override
    public boolean setup() {
        ColorMain.rainbow = this.registerBoolean("Rainbow", "Rainbow", false);
        ColorMain.Red = this.registerInteger("Red", "Red", 255, 0, 255);
        ColorMain.Green = this.registerInteger("Green", "Green", 26, 0, 255);
        ColorMain.Blue = this.registerInteger("Blue", "Blue", 42, 0, 255);
        final ArrayList<String> tab = new ArrayList<String>();
        tab.add("Black");
        tab.add("Dark Green");
        tab.add("Dark Red");
        tab.add("Gold");
        tab.add("Dark Gray");
        tab.add("Green");
        tab.add("Red");
        tab.add("Yellow");
        tab.add("Dark Blue");
        tab.add("Dark Aqua");
        tab.add("Dark Purple");
        tab.add("Gray");
        tab.add("Blue");
        tab.add("Aqua");
        tab.add("Light Purple");
        tab.add("White");
        ColorMain.friendcolor = this.registerMode("Friend", "FriendColor", tab, "Blue");
        ColorMain.enemycolor = this.registerMode("Enemy", "EnemyColor", tab, "Red");
        return false;
    }

    public void onEnable() {
        this.disable();
    }

    public static TextFormatting getFriendColor() {
        if (ColorMain.friendcolor.getValue().equalsIgnoreCase("Black")) {
            return TextFormatting.BLACK;
        }
        if (ColorMain.friendcolor.getValue().equalsIgnoreCase("Dark Green")) {
            return TextFormatting.DARK_GREEN;
        }
        if (ColorMain.friendcolor.getValue().equalsIgnoreCase("Dark Red")) {
            return TextFormatting.DARK_RED;
        }
        if (ColorMain.friendcolor.getValue().equalsIgnoreCase("Gold")) {
            return TextFormatting.GOLD;
        }
        if (ColorMain.friendcolor.getValue().equalsIgnoreCase("Dark Gray")) {
            return TextFormatting.DARK_GRAY;
        }
        if (ColorMain.friendcolor.getValue().equalsIgnoreCase("Green")) {
            return TextFormatting.GREEN;
        }
        if (ColorMain.friendcolor.getValue().equalsIgnoreCase("Red")) {
            return TextFormatting.RED;
        }
        if (ColorMain.friendcolor.getValue().equalsIgnoreCase("Yellow")) {
            return TextFormatting.YELLOW;
        }
        if (ColorMain.friendcolor.getValue().equalsIgnoreCase("Dark Blue")) {
            return TextFormatting.DARK_BLUE;
        }
        if (ColorMain.friendcolor.getValue().equalsIgnoreCase("Dark Aqua")) {
            return TextFormatting.DARK_AQUA;
        }
        if (ColorMain.friendcolor.getValue().equalsIgnoreCase("Dark Purple")) {
            return TextFormatting.DARK_PURPLE;
        }
        if (ColorMain.friendcolor.getValue().equalsIgnoreCase("Gray")) {
            return TextFormatting.GRAY;
        }
        if (ColorMain.friendcolor.getValue().equalsIgnoreCase("Blue")) {
            return TextFormatting.BLUE;
        }
        if (ColorMain.friendcolor.getValue().equalsIgnoreCase("Light Purple")) {
            return TextFormatting.LIGHT_PURPLE;
        }
        if (ColorMain.friendcolor.getValue().equalsIgnoreCase("White")) {
            return TextFormatting.WHITE;
        }
        if (ColorMain.friendcolor.getValue().equalsIgnoreCase("Aqua")) {
            return TextFormatting.AQUA;
        }
        return null;
    }

    public static TextFormatting getEnemyColor() {
        if (ColorMain.enemycolor.getValue().equalsIgnoreCase("Black")) {
            return TextFormatting.BLACK;
        }
        if (ColorMain.enemycolor.getValue().equalsIgnoreCase("Dark Green")) {
            return TextFormatting.DARK_GREEN;
        }
        if (ColorMain.enemycolor.getValue().equalsIgnoreCase("Dark Red")) {
            return TextFormatting.DARK_RED;
        }
        if (ColorMain.enemycolor.getValue().equalsIgnoreCase("Gold")) {
            return TextFormatting.GOLD;
        }
        if (ColorMain.enemycolor.getValue().equalsIgnoreCase("Dark Gray")) {
            return TextFormatting.DARK_GRAY;
        }
        if (ColorMain.enemycolor.getValue().equalsIgnoreCase("Green")) {
            return TextFormatting.GREEN;
        }
        if (ColorMain.enemycolor.getValue().equalsIgnoreCase("Red")) {
            return TextFormatting.RED;
        }
        if (ColorMain.enemycolor.getValue().equalsIgnoreCase("Yellow")) {
            return TextFormatting.YELLOW;
        }
        if (ColorMain.enemycolor.getValue().equalsIgnoreCase("Dark Blue")) {
            return TextFormatting.DARK_BLUE;
        }
        if (ColorMain.enemycolor.getValue().equalsIgnoreCase("Dark Aqua")) {
            return TextFormatting.DARK_AQUA;
        }
        if (ColorMain.enemycolor.getValue().equalsIgnoreCase("Dark Purple")) {
            return TextFormatting.DARK_PURPLE;
        }
        if (ColorMain.enemycolor.getValue().equalsIgnoreCase("Gray")) {
            return TextFormatting.GRAY;
        }
        if (ColorMain.enemycolor.getValue().equalsIgnoreCase("Blue")) {
            return TextFormatting.BLUE;
        }
        if (ColorMain.enemycolor.getValue().equalsIgnoreCase("Light Purple")) {
            return TextFormatting.LIGHT_PURPLE;
        }
        if (ColorMain.enemycolor.getValue().equalsIgnoreCase("White")) {
            return TextFormatting.WHITE;
        }
        if (ColorMain.enemycolor.getValue().equalsIgnoreCase("Aqua")) {
            return TextFormatting.AQUA;
        }
        return null;
    }

    public static int getFriendColorInt() {
        if (ColorMain.friendcolor.getValue().equalsIgnoreCase("Black")) {
            return Color.BLACK.getRGB();
        }
        if (ColorMain.friendcolor.getValue().equalsIgnoreCase("Dark Green")) {
            return Color.GREEN.darker().getRGB();
        }
        if (ColorMain.friendcolor.getValue().equalsIgnoreCase("Dark Red")) {
            return Color.RED.darker().getRGB();
        }
        if (ColorMain.friendcolor.getValue().equalsIgnoreCase("Gold")) {
            return Color.yellow.darker().getRGB();
        }
        if (ColorMain.friendcolor.getValue().equalsIgnoreCase("Dark Gray")) {
            return Color.DARK_GRAY.getRGB();
        }
        if (ColorMain.friendcolor.getValue().equalsIgnoreCase("Green")) {
            return Color.green.getRGB();
        }
        if (ColorMain.friendcolor.getValue().equalsIgnoreCase("Red")) {
            return Color.red.getRGB();
        }
        if (ColorMain.friendcolor.getValue().equalsIgnoreCase("Yellow")) {
            return Color.yellow.getRGB();
        }
        if (ColorMain.friendcolor.getValue().equalsIgnoreCase("Dark Blue")) {
            return Color.blue.darker().getRGB();
        }
        if (ColorMain.friendcolor.getValue().equalsIgnoreCase("Dark Aqua")) {
            return Color.CYAN.darker().getRGB();
        }
        if (ColorMain.friendcolor.getValue().equalsIgnoreCase("Dark Purple")) {
            return Color.MAGENTA.darker().getRGB();
        }
        if (ColorMain.friendcolor.getValue().equalsIgnoreCase("Gray")) {
            return Color.GRAY.getRGB();
        }
        if (ColorMain.friendcolor.getValue().equalsIgnoreCase("Blue")) {
            return Color.blue.getRGB();
        }
        if (ColorMain.friendcolor.getValue().equalsIgnoreCase("Light Purple")) {
            return Color.magenta.getRGB();
        }
        if (ColorMain.friendcolor.getValue().equalsIgnoreCase("White")) {
            return Color.WHITE.getRGB();
        }
        if (ColorMain.friendcolor.getValue().equalsIgnoreCase("Aqua")) {
            return Color.cyan.getRGB();
        }
        return Color.WHITE.getRGB();
    }

    public static int getEnemyColorInt() {
        if (ColorMain.enemycolor.getValue().equalsIgnoreCase("Black")) {
            return Color.BLACK.getRGB();
        }
        if (ColorMain.enemycolor.getValue().equalsIgnoreCase("Dark Green")) {
            return Color.GREEN.darker().getRGB();
        }
        if (ColorMain.enemycolor.getValue().equalsIgnoreCase("Dark Red")) {
            return Color.RED.darker().getRGB();
        }
        if (ColorMain.enemycolor.getValue().equalsIgnoreCase("Gold")) {
            return Color.yellow.darker().getRGB();
        }
        if (ColorMain.enemycolor.getValue().equalsIgnoreCase("Dark Gray")) {
            return Color.DARK_GRAY.getRGB();
        }
        if (ColorMain.enemycolor.getValue().equalsIgnoreCase("Green")) {
            return Color.green.getRGB();
        }
        if (ColorMain.enemycolor.getValue().equalsIgnoreCase("Red")) {
            return Color.red.getRGB();
        }
        if (ColorMain.enemycolor.getValue().equalsIgnoreCase("Yellow")) {
            return Color.yellow.getRGB();
        }
        if (ColorMain.enemycolor.getValue().equalsIgnoreCase("Dark Blue")) {
            return Color.blue.darker().getRGB();
        }
        if (ColorMain.enemycolor.getValue().equalsIgnoreCase("Dark Aqua")) {
            return Color.CYAN.darker().getRGB();
        }
        if (ColorMain.enemycolor.getValue().equalsIgnoreCase("Dark Purple")) {
            return Color.MAGENTA.darker().getRGB();
        }
        if (ColorMain.enemycolor.getValue().equalsIgnoreCase("Gray")) {
            return Color.GRAY.getRGB();
        }
        if (ColorMain.enemycolor.getValue().equalsIgnoreCase("Blue")) {
            return Color.blue.getRGB();
        }
        if (ColorMain.enemycolor.getValue().equalsIgnoreCase("Light Purple")) {
            return Color.magenta.getRGB();
        }
        if (ColorMain.enemycolor.getValue().equalsIgnoreCase("White")) {
            return Color.WHITE.getRGB();
        }
        if (ColorMain.enemycolor.getValue().equalsIgnoreCase("Aqua")) {
            return Color.cyan.getRGB();
        }
        return Color.WHITE.getRGB();
    }
}

