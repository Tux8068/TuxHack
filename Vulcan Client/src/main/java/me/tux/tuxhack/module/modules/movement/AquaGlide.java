//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.tux.tuxhack.module.modules.movement;

import org.lwjgl.input.Keyboard;
import me.tux.tuxhack.setting.Setting;
import me.tux.tuxhack.module.Module;
/**
 * @author TuxISCool
 * @since 28/05/2021
 */
public class AquaGlide extends Module
{
    Setting.Double blocks;
    
    public AquaGlide() {
        super("AquaGlide", "AquaGlide allows you to fall in water quickly", Category.Movement);
    }
    
    @Override
    public boolean setup() {
        this.blocks = this.registerDouble("tpblocks", "tpblock", -0.1, -0.1, -1.0);
        return false;
    }
    
    @Override
    public int onUpdate() {
        if ((AquaGlide.mc.player.isInWater() || AquaGlide.mc.player.isInLava()) && Keyboard.isKeyDown(42)) {
            AquaGlide.mc.player.setPosition(AquaGlide.mc.player.posX, AquaGlide.mc.player.posY + this.blocks.getValue(), AquaGlide.mc.player.posZ);
        }
        return 0;
    }
}
