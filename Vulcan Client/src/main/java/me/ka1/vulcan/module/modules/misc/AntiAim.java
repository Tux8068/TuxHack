package me.ka1.vulcan.module.modules.misc;

import me.ka1.vulcan.module.Module;


import java.util.Random;

public class AntiAim extends Module {
    public AntiAim() {
        super("AntiAim", "Makes it hard to target you", Category.Misc);
        }

    public String getDisplayInfo() {
        return null;
    }

    @Override
public int onUpdate() {
        Random r = new Random();
        mc.player.rotationPitch = r.nextInt(150);
        mc.player.rotationYaw = r.nextInt(150);

            return 0;
        }


}