package me.tux.tuxhack.mixin.mixins;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;

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
