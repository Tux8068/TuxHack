package me.ka1.vulcan.mixin.mixins;

import me.ka1.vulcan.Vulcan;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
 @Mixin(Minecraft.class)
public class MixinMinecraft {

   /* @Inject(method = "shutdown" , at = @At("HEAD"), cancellable = false)
    public void shutdownHook(CallbackInfo info) {
        this.save();
    }


  /*  private void save() {
        System.out.println("Shutting down: saving configuration");
        Vulcan.save();
        System.out.println("Configuration saved.");
    } */

}
