package me.tux.tuxhack.mixin.mixins;

import me.tux.tuxhack.TuxHack;
import me.tux.tuxhack.event.events.EventRenderEntityName;
import me.tux.tuxhack.module.ModuleManager;
import me.tux.tuxhack.module.modules.render.Nametags;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Made By 336
**/

@Mixin(RenderPlayer.class)
public class MixinRenderPlayer {
    @Inject(method = "renderEntityName", at = @At("HEAD"), cancellable = true)

    public void renderLivingLabel(AbstractClientPlayer entityIn, double x, double y, double z, String name, double distanceSq, CallbackInfo info) {
        EventRenderEntityName l_Event = new EventRenderEntityName(entityIn, x, y, z, name, distanceSq);
        TuxHack.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled())
            info.cancel();

    }

    @Inject(method = {"renderEntityName"}, at = {@At(value = "HEAD")}, cancellable = true)
    public void renderEntityNameHook(AbstractClientPlayer entityIn, double x, double y, double z, String name, double distanceSq, CallbackInfo info) {
        if (ModuleManager.getModuleByName("Nametags").isEnabled()) {
            info.cancel();
        }
    }
}