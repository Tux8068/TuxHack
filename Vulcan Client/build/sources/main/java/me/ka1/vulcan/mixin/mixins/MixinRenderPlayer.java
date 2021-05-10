package me.ka1.vulcan.mixin.mixins;

import me.ka1.vulcan.Vulcan;
import me.ka1.vulcan.event.events.EventRenderEntityName;
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
public class MixinRenderPlayer
{
    @Inject(method = "renderEntityName", at = @At("HEAD"), cancellable = true)
    public void renderLivingLabel(AbstractClientPlayer entityIn, double x, double y, double z, String name, double distanceSq, CallbackInfo info)
    {
        EventRenderEntityName l_Event = new EventRenderEntityName(entityIn, x, y, z, name, distanceSq);
        Vulcan.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled())
            info.cancel();
    }
}
