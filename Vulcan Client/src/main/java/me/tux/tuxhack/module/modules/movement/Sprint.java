package me.tux.tuxhack.module.modules.movement;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.setting.Setting;

import java.util.ArrayList;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", "Makes you sprint", Category.MOVEMENT);
    }

    Setting.Mode mode;

    public boolean setup() {
        ArrayList<String> Modes = new ArrayList<>();
        Modes.add("Rage");
        Modes.add("Legit");

        mode = registerMode("Mode", "Mode", Modes, "Rage");
        return false;
    }

    public int onUpdate() {

        if (mode.getValue().equalsIgnoreCase("Legit")) {
            if (mc.gameSettings.keyBindForward.isKeyDown() && !(mc.player.collidedHorizontally)) {
                if (!mc.player.isSprinting()) {
                    mc.player.setSprinting(true);
                }
            }
        } else if (mode.getValue().equalsIgnoreCase("Rage")) {
            if (!mc.player.isSprinting()) {
                mc.player.setSprinting(true);
            }
        }
        return 0;
    }

    public String getHudInfo() {
        String t = "";
        if (mode.getValue().equalsIgnoreCase("Rage")) {
            t = "[" + ChatFormatting.WHITE + "Rage" + ChatFormatting.GRAY + "]";
        }
        if (mode.getValue().equalsIgnoreCase("Legit")) {
            t = "[" + ChatFormatting.WHITE + "Legit" + ChatFormatting.GRAY + "]";
        }
        return t;
    }

}