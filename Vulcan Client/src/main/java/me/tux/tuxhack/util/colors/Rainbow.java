package me.tux.tuxhack.util.colors;

import me.tux.tuxhack.event.EventProcessor;

import java.awt.Color;

public class Rainbow
{
    public static int getInt() {
        return EventProcessor.INSTANCE.getRgb();
    }

    public static Color getColor() {
        return EventProcessor.INSTANCE.getC();
    }

    public static Color getColorWithOpacity(final int opacity) {
        return new Color(getColor().getRed(), getColor().getGreen(), getColor().getBlue(), opacity);
    }

    public static int getIntWithOpacity(final int opacity) {
        return getColorWithOpacity(opacity).getRGB();
    }
}
