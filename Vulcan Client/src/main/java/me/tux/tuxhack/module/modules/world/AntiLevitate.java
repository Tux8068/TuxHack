package me.tux.tuxhack.module.modules.world;


/**
 * @author Tuxiscool#6456
 * @since 14/06/2021
 */
import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.util.maths.MathUtil;
import net.minecraft.init.MobEffects;
import org.lwjgl.input.Keyboard;

public class AntiLevitate extends Module {
    public AntiLevitate() {
        super("AntiLevitation", "allows you to freely fly with levitation effect", Category.MOVEMENT);

    }

    @Override
    public int onUpdate() {
        {
            {
                {
                    {
                    if (!mc.player.isRiding()) ;
                    if (!(this.mc.player.isPotionActive(MobEffects.LEVITATION)))
                        mc.player.setVelocity(0, 1, 0);
                    MathUtil.directionSpeed(0.10000000148011612);
                }
                    if (!mc.player.isRiding()) ;
                    if (!(this.mc.player.isPotionActive(MobEffects.LEVITATION)))
                if (mc.player.movementInput.moveForward != 0.0F || mc.player.movementInput.moveStrafe != 0.0f) ;
                mc.player.motionX = 0;
                mc.player.motionX = 0;
            }
                if (!mc.player.isRiding()) ;
                if (!(this.mc.player.isPotionActive(MobEffects.LEVITATION)))
            if (mc.player.isElytraFlying() && mc.player.movementInput.jump && Keyboard.isKeyDown(Keyboard.KEY_SPACE)) ;
            this.mc.player.motionY = 0.10000000149011612;
        }
            if (!mc.player.isRiding()) ;
            if (!(this.mc.player.isPotionActive(MobEffects.LEVITATION)))
        if (mc.player.movementInput.sneak) ;
        this.mc.player.motionY = -0.10000000149011612;
    }
        return 0;
    }
}

