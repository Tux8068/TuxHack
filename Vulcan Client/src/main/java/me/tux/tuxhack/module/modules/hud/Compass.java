package me.tux.tuxhack.module.modules.hud;


import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.setting.Setting;
import me.tux.tuxhack.util.Wrapper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.gui.ScaledResolution;
import java.awt.Color;


public class Compass extends Module
{

    Color c;
    private static final double HALF_PI = 1.5707963267948966;
    ScaledResolution resolution;

    public Compass() {
        super("Compass", Category.HUD);
        this.resolution = new ScaledResolution(Compass.mc);
    }


    Setting.Double scale;
    public boolean setup() {

       scale = registerDouble("Scale", "Scale", 3.0, 1.0, 5.0);
        return false;
    }

    @Override
    public void onRender() {
        final double centerX = this.resolution.getScaledWidth() * 1.11;
        final double centerY = this.resolution.getScaledHeight_double() * 1.8;
        for (final Direction dir : Direction.values()) {
            final double rad = getPosOnCompass(dir);
            this.drawStringWithShadow(dir.name(), (int)(centerX + this.getX(rad)), (int)(centerY + this.getY(rad)), (dir == Direction.N) ? new Color(ColorMain.Red.getValue(), ColorMain.Green.getValue(), ColorMain.Blue.getValue(), 255).getRGB() : new Color(255, 255, 255, 255).getRGB());
        }
    }

    private double getX(final double rad) {
        return Math.sin(rad) * (this.scale.getValue() * 10.0);
    }

    private double getY(final double rad) {
        final double epicPitch = MathHelper.clamp(Wrapper.getRenderEntity().rotationPitch + 30.0f, -90.0f, 90.0f);
        final double pitchRadians = Math.toRadians(epicPitch);
        return Math.cos(rad) * Math.sin(pitchRadians) * (this.scale.getValue() * 10.0);
    }
    private void drawStringWithShadow(final String text, final int x, final int y, final int color) {
        Compass.mc.fontRenderer.drawStringWithShadow(text, (float)x, (float)y, color);
    }


    private static double getPosOnCompass(final Direction dir) {
        final double yaw = Math.toRadians(MathHelper.wrapDegrees(Wrapper.getRenderEntity().rotationYaw));
        final int index = dir.ordinal();
        return yaw + index * 1.5707963267948966;
    }

    private enum Direction
    {
        N,
        W,
        S,
        E;
    }
}
