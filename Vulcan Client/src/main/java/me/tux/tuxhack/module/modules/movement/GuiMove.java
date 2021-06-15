package me.tux.tuxhack.module.modules.movement;

import me.tux.tuxhack.module.Module;
import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;


public class GuiMove extends Module {
    public GuiMove() {
        super ("GuiMove", "Allows you to move in GUI's using arrow keys.", Category.MOVEMENT);
    }

    private Minecraft mc = Minecraft.getMinecraft();
    public int onUpdate(){
        if (mc.currentScreen != null){
            if (!(mc.currentScreen instanceof GuiChat)){
                if (Keyboard.isKeyDown(200)){
                    mc.player.rotationPitch -= 6; }
                if (Keyboard.isKeyDown(208)){
                    mc.player.rotationPitch += 6; }
                if (Keyboard.isKeyDown(205)){
                    mc.player.rotationYaw += 6; }
                if (Keyboard.isKeyDown(203)){
                    mc.player.rotationYaw -= 6; }
                if (mc.player.rotationPitch > 90){
                    mc.player.rotationPitch = 90; }
                if (mc.player.rotationPitch < -90){
                    mc.player.rotationPitch = -90;
                }
            }
        }
        return 0;
    }
}
