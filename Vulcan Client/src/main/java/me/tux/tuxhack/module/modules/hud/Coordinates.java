package me.tux.tuxhack.module.modules.hud;

import me.tux.tuxhack.TuxHack;
import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.setting.Setting;
import net.minecraft.entity.Entity;

import java.awt.*;
import java.util.ArrayList;

public class Coordinates extends Module {
    public Coordinates() {
        super("Coordinates", "Displays coordinates on screen", Category.HUD);
    }

    Setting.Mode spaces;
    Setting.Mode separators;
    Setting.Boolean nether;
    Setting.Boolean nice;
    Setting.Integer r;
    Setting.Integer g;
    Setting.Integer b;
    Setting.Integer X;
    Setting.Integer Y;

    public boolean setup() {

        ArrayList<String> Separators;
        Separators = new ArrayList<>();
        Separators.add("None");
        Separators.add("Commas");

        ArrayList<String> Spaces;
        Spaces = new ArrayList<>();
        Spaces.add("One");
        Spaces.add("Two");

        //  spaces = registerMode("Spaces", "Spaces", Spaces, "One");
        //   separators = registerMode("Separators", "Separators", Separators, "None");
        nether = registerBoolean("Nether", "nether", true);
        nice = registerBoolean("Nice", "nice", true);
        r = registerInteger("Red", "red", 255, 0, 255);
        g = registerInteger("Green", "green", 255, 0, 255);
        b = registerInteger("Blue", "blue", 255, 0, 255);
        X = registerInteger("X", "x", 0, 0, 1280);
        Y = registerInteger("Y", "y", 320, 0, 960);
        return false;
    }

    public void onRender() {

        String x = String.format("%.1f", (double) (mc.player.posX));
        String y = String.format("%.1f", (double) (mc.player.posY));
        String z = String.format("%.1f", (double) (mc.player.posZ));

        String netherX = String.format("%.1f", (double) (mc.player.posX / 8));
        String netherZ = String.format("%.1f", (double) (mc.player.posZ / 8));

        String owX = String.format("%.1f", (double) (mc.player.posX * 8));
        String owZ = String.format("%.1f", (double) (mc.player.posZ * 8));

        /**
         * Vulcan.fontRenderer.drawStringWithShadow("[" + x + "  " + y + "  " + z + "]" + "  " + "[" + netherX + " " + netherZ + "]", X.getValue() , Y.getValue(), new Color (r.getValue(), g.getValue(), b.getValue(), 255).getRGB());
         */

        Entity viewEntity = mc.getRenderViewEntity();
        int dimension = viewEntity.dimension;

        if (mc.player.isDead || mc.player == null || mc.world == null) {
            return;
        }
        switch (dimension) {
            case 0: // ow

                if (nether.getValue()) {
                    if (mc.player.posY == 69 && nice.getValue()) {
                        TuxHack.fontRenderer.drawStringWithShadow("[" + x + "  " + "nice" + "  " + z + "]" + "  " + "[" + netherX + "  " + netherZ + "]", X.getValue(), Y.getValue(), new Color(r.getValue(), g.getValue(), b.getValue(), 255).getRGB());
                    } else
                        TuxHack.fontRenderer.drawStringWithShadow("[" + x + "  " + y + "  " + z + "]" + "  " + "[" + netherX + "  " + netherZ + "]", X.getValue(), Y.getValue(), new Color(r.getValue(), g.getValue(), b.getValue(), 255).getRGB());
                } else
                    TuxHack.fontRenderer.drawStringWithShadow("[" + x + "  " + y + "  " + z + "]", X.getValue(), Y.getValue(), new Color(r.getValue(), g.getValue(), b.getValue(), 255).getRGB());
                break;
            case -1: // nether

                if (mc.player.posY == 69 && nice.getValue()) {
                    TuxHack.fontRenderer.drawStringWithShadow("[" + x + "  " + "nice" + "  " + z + "]" + "  " + "[" + owX + "  " + owZ + "]", X.getValue(), Y.getValue(), new Color(r.getValue(), g.getValue(), b.getValue(), 255).getRGB());
                } else {
                    TuxHack.fontRenderer.drawStringWithShadow("[" + x + "  " + y + "  " + z + "]" + "  " + "[" + owX + "  " + owZ + "]", X.getValue(), Y.getValue(), new Color(r.getValue(), g.getValue(), b.getValue(), 255).getRGB());
                }
                break;
            case 1: // end
                if (mc.player.posY == 69 && nice.getValue()) {
                    TuxHack.fontRenderer.drawStringWithShadow("[" + x + "  " + "nice" + "  " + z + "]", X.getValue(), Y.getValue(), new Color(r.getValue(), g.getValue(), b.getValue(), 255).getRGB());
                } else {
                    TuxHack.fontRenderer.drawStringWithShadow("[" + x + "  " + y + "  " + z + "]", X.getValue(), Y.getValue(), new Color(r.getValue(), g.getValue(), b.getValue(), 255).getRGB());
                }
                default:
                break;
        }
    }
}
