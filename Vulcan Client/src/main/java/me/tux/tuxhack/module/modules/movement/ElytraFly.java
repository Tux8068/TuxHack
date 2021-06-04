package me.tux.tuxhack.module.modules.movement;

import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.setting.Setting;
import net.minecraft.network.play.client.CPacketEntityAction;

import java.util.Objects;
import java.util.Random;

public class ElytraFly extends Module {
    public ElytraFly() {
        super("ElytraFly", Category.Movement);
    }

    Setting.Double speed;
    Setting.Boolean NcpStrict;
    Setting.Boolean infdura;
    Setting.Boolean animations;
    Setting.Boolean rusherhack;
    public boolean setup()
    {
        speed = registerDouble("Speed", "speed", 1, 0.1,3 );
        NcpStrict = registerBoolean("NcpStrict", "NcpStrict", false);
        infdura = registerBoolean("infdura", "infdura", false);
        animations = registerBoolean("animations", "animations", false);
        rusherhack = registerBoolean("rushehrhac", "idk", false);
        return false;
    }

    public int onUpdate(){
        Random r = new Random();
        if(mc.player.capabilities.isFlying || mc.player.isElytraFlying())
            mc.player.setSprinting(false);
        if (mc.player.capabilities.isFlying) {
            mc.player.setVelocity(0, 0, 0);
            mc.player.setPosition(mc.player.posX, mc.player.posY - 0.000050000002f, mc.player.posZ);
            mc.player.capabilities.setFlySpeed((float)speed.getValue());
            mc.player.setSprinting(false);
        }
        if (mc.player.onGround) {
            mc.player.capabilities.allowFlying = false;
        }
        if (mc.player.isElytraFlying()) {
            mc.player.capabilities.setFlySpeed(.915f);
            mc.player.capabilities.isFlying = true;

            if (!mc.player.capabilities.isCreativeMode)
                mc.player.capabilities.allowFlying = true;

            if (mc.player.isInWater() || mc.player.isInLava()) {
                if (mc.player.isElytraFlying()) {
                    Objects.requireNonNull(mc.getConnection()).sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
                    if (NcpStrict.getValue() == true) {
                        if (mc.gameSettings.keyBindJump.isKeyDown()) {
                            mc.player.motionY = 0.02f;
                        }

                        if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                            mc.player.motionY = -0.2f;
                        }

                        if (mc.player.ticksExisted % 8 == 0 && mc.player.posY <= 240) {
                            mc.player.motionY = 0.02f;
                        }

                        mc.player.capabilities.isFlying = true;
                        mc.player.capabilities.setFlySpeed(0.025f);
                        if (mc.player.movementInput.moveStrafe != 0 || mc.player.movementInput.moveForward != 0) {
                        } else {
                            mc.player.motionX = 0;
                            mc.player.motionZ = 0;
                        }
                        if (infdura.getValue() == true) {
                            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
                            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
                        }
                        if (animations.getValue() == true) {
                            mc.player.rotateElytraX = r.nextInt(150);
                            mc.player.rotateElytraZ = r.nextInt(150);
                            }
                        }
                    }
                }
                return 0;
            }
        return 0;
    }

    protected int onDisable() {
        mc.player.capabilities.isFlying = false;
        mc.player.capabilities.setFlySpeed(0.05f);
        if (!mc.player.capabilities.isCreativeMode)
            mc.player.capabilities.allowFlying = false;
        return 0;
    }
}


