package me.tux.tuxhack.module.modules.movement;

import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.setting.Setting;
import net.minecraft.block.material.Material;

public class MoonWalk extends Module {
    public MoonWalk() {
        super("MoonWalk", "Tux is Tux", Category.MOVEMENT);
    }

    Setting.Double poop;

    public boolean setup() {
        poop = registerDouble("h", "poop", 69, 6.9, 420);
        return false;
    }

    public int onUpdate() {
        if(mc.player.onGround && mc.gameSettings.keyBindJump.isPressed()) {
            mc.player.motionY = 0.25;
        } else if(mc.player.isAirBorne && !mc.player.isInWater() && !mc.player.isOnLadder() && !mc.player.isInsideOfMaterial(Material.LAVA)) {
            mc.player.motionY = 0.0000010D;
            mc.player.jumpMovementFactor *= 1.21337f;
        }
        return 0;
    }
}

