// 
// Decompiled by Procyon v0.5.36
// 

package me.tux.tuxhack.module.modules.client;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.List;
import java.util.ArrayList;
import me.tux.tuxhack.setting.Setting;
import me.tux.tuxhack.module.Module;

public class CommandColor extends Module
{
    public static Setting.Mode CommandColor;
    public static Setting.Mode BracketColor;
    
    public CommandColor() {
        super("CommandColor", Category.CLIENT);
    }
    
    @Override
    public boolean setup() {
        final ArrayList<String> colors = new ArrayList<String>();
        colors.add("Black");
        colors.add("Dark Green");
        colors.add("Dark Red");
        colors.add("Gold");
        colors.add("Dark Gray");
        colors.add("Green");
        colors.add("Red");
        colors.add("Yellow");
        colors.add("Dark Blue");
        colors.add("Dark Aqua");
        colors.add("Dark Purple");
        colors.add("Gray");
        colors.add("Blue");
        colors.add("Aqua");
        colors.add("Light Purple");
        colors.add("White");
        final ArrayList<String> brackets = new ArrayList<String>();
        brackets.add("Black");
        brackets.add("Dark Green");
        brackets.add("Dark Red");
        brackets.add("Gold");
        brackets.add("Dark Gray");
        brackets.add("Green");
        brackets.add("Red");
        brackets.add("Yellow");
        brackets.add("Dark Blue");
        brackets.add("Dark Aqua");
        brackets.add("Dark Purple");
        brackets.add("Gray");
        brackets.add("Blue");
        brackets.add("Aqua");
        brackets.add("Light Purple");
        brackets.add("White");
        me.tux.tuxhack.module.modules.client.CommandColor.CommandColor = this.registerMode("Text", "Color", colors, "Aqua");
        me.tux.tuxhack.module.modules.client.CommandColor.BracketColor = this.registerMode("Brackets", "BracketColor", brackets, "Light Purple");
        return false;
    }
    
    public static ChatFormatting getTextColor() {
        if (me.tux.tuxhack.module.modules.client.CommandColor.CommandColor.getValue().equalsIgnoreCase("Black")) {
            return ChatFormatting.BLACK;
        }
        if (me.tux.tuxhack.module.modules.client.CommandColor.CommandColor.getValue().equalsIgnoreCase("Dark Green")) {
            return ChatFormatting.DARK_GREEN;
        }
        if (me.tux.tuxhack.module.modules.client.CommandColor.CommandColor.getValue().equalsIgnoreCase("Dark Red")) {
            return ChatFormatting.DARK_RED;
        }
        if (me.tux.tuxhack.module.modules.client.CommandColor.CommandColor.getValue().equalsIgnoreCase("Gold")) {
            return ChatFormatting.GOLD;
        }
        if (me.tux.tuxhack.module.modules.client.CommandColor.CommandColor.getValue().equalsIgnoreCase("Dark Gray")) {
            return ChatFormatting.DARK_GRAY;
        }
        if (me.tux.tuxhack.module.modules.client.CommandColor.CommandColor.getValue().equalsIgnoreCase("Green")) {
            return ChatFormatting.GREEN;
        }
        if (me.tux.tuxhack.module.modules.client.CommandColor.CommandColor.getValue().equalsIgnoreCase("Red")) {
            return ChatFormatting.RED;
        }
        if (me.tux.tuxhack.module.modules.client.CommandColor.CommandColor.getValue().equalsIgnoreCase("Yellow")) {
            return ChatFormatting.YELLOW;
        }
        if (me.tux.tuxhack.module.modules.client.CommandColor.CommandColor.getValue().equalsIgnoreCase("Dark Blue")) {
            return ChatFormatting.DARK_BLUE;
        }
        if (me.tux.tuxhack.module.modules.client.CommandColor.CommandColor.getValue().equalsIgnoreCase("Dark Aqua")) {
            return ChatFormatting.DARK_AQUA;
        }
        if (me.tux.tuxhack.module.modules.client.CommandColor.CommandColor.getValue().equalsIgnoreCase("Dark Purple")) {
            return ChatFormatting.DARK_PURPLE;
        }
        if (me.tux.tuxhack.module.modules.client.CommandColor.CommandColor.getValue().equalsIgnoreCase("Gray")) {
            return ChatFormatting.GRAY;
        }
        if (me.tux.tuxhack.module.modules.client.CommandColor.CommandColor.getValue().equalsIgnoreCase("Blue")) {
            return ChatFormatting.BLUE;
        }
        if (me.tux.tuxhack.module.modules.client.CommandColor.CommandColor.getValue().equalsIgnoreCase("Light Purple")) {
            return ChatFormatting.LIGHT_PURPLE;
        }
        if (me.tux.tuxhack.module.modules.client.CommandColor.CommandColor.getValue().equalsIgnoreCase("White")) {
            return ChatFormatting.WHITE;
        }
        if (me.tux.tuxhack.module.modules.client.CommandColor.CommandColor.getValue().equalsIgnoreCase("Aqua")) {
            return ChatFormatting.AQUA;
        }
        return null;
    }
    
    public static ChatFormatting getBrackets() {
        if (me.tux.tuxhack.module.modules.client.CommandColor.BracketColor.getValue().equalsIgnoreCase("Black")) {
            return ChatFormatting.BLACK;
        }
        if (me.tux.tuxhack.module.modules.client.CommandColor.BracketColor.getValue().equalsIgnoreCase("Dark Green")) {
            return ChatFormatting.DARK_GREEN;
        }
        if (me.tux.tuxhack.module.modules.client.CommandColor.BracketColor.getValue().equalsIgnoreCase("Dark Red")) {
            return ChatFormatting.DARK_RED;
        }
        if (me.tux.tuxhack.module.modules.client.CommandColor.BracketColor.getValue().equalsIgnoreCase("Gold")) {
            return ChatFormatting.GOLD;
        }
        if (me.tux.tuxhack.module.modules.client.CommandColor.BracketColor.getValue().equalsIgnoreCase("Dark Gray")) {
            return ChatFormatting.DARK_GRAY;
        }
        if (me.tux.tuxhack.module.modules.client.CommandColor.BracketColor.getValue().equalsIgnoreCase("Green")) {
            return ChatFormatting.GREEN;
        }
        if (me.tux.tuxhack.module.modules.client.CommandColor.BracketColor.getValue().equalsIgnoreCase("Red")) {
            return ChatFormatting.RED;
        }
        if (me.tux.tuxhack.module.modules.client.CommandColor.BracketColor.getValue().equalsIgnoreCase("Yellow")) {
            return ChatFormatting.YELLOW;
        }
        if (me.tux.tuxhack.module.modules.client.CommandColor.BracketColor.getValue().equalsIgnoreCase("Dark Blue")) {
            return ChatFormatting.DARK_BLUE;
        }
        if (me.tux.tuxhack.module.modules.client.CommandColor.BracketColor.getValue().equalsIgnoreCase("Dark Aqua")) {
            return ChatFormatting.DARK_AQUA;
        }
        if (me.tux.tuxhack.module.modules.client.CommandColor.BracketColor.getValue().equalsIgnoreCase("Dark Purple")) {
            return ChatFormatting.DARK_PURPLE;
        }
        if (me.tux.tuxhack.module.modules.client.CommandColor.BracketColor.getValue().equalsIgnoreCase("Gray")) {
            return ChatFormatting.GRAY;
        }
        if (me.tux.tuxhack.module.modules.client.CommandColor.BracketColor.getValue().equalsIgnoreCase("Blue")) {
            return ChatFormatting.BLUE;
        }
        if (me.tux.tuxhack.module.modules.client.CommandColor.BracketColor.getValue().equalsIgnoreCase("Light Purple")) {
            return ChatFormatting.LIGHT_PURPLE;
        }
        if (me.tux.tuxhack.module.modules.client.CommandColor.BracketColor.getValue().equalsIgnoreCase("White")) {
            return ChatFormatting.WHITE;
        }
        if (me.tux.tuxhack.module.modules.client.CommandColor.BracketColor.getValue().equalsIgnoreCase("Aqua")) {
            return ChatFormatting.AQUA;
        }
        return null;
    }
}
