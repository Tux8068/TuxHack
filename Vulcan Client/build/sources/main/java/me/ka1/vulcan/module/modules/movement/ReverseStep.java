package me.ka1.vulcan.module.modules.movement;

import me.ka1.vulcan.module.Module;
import me.ka1.vulcan.setting.Setting;

public class ReverseStep extends Module {
    public ReverseStep() {super("ReverseStep", "Allows you to instantly step down blocks", Category.Movement);}

    Setting.Double height;

    public void setup() {
        height = registerDouble("Height", "Height", 2.5, 0.5, 15);
    }

    public int onUpdate() {
        if (mc.world == null || mc.player == null || mc.player.isInWater() || mc.player.isInLava() || mc.player.isOnLadder()
                || mc.gameSettings.keyBindJump.isKeyDown()) {
            return 0;
        }

        if (mc.player != null && mc.player.onGround && !mc.player.isInWater() && !mc.player.isOnLadder()) {
            for (double y = 0.0; y < this.height.getValue() + 0.5; y += 0.01) {
                if (!mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().offset(0.0, -y, 0.0)).isEmpty()) {
                    mc.player.motionY = -10.0;
                    break;
                }
            }
        }
        return 0;
    }
}
