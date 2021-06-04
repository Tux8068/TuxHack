package me.tux.tuxhack.mixin.mixins;

import me.tux.tuxhack.module.ModuleManager;
import me.tux.tuxhack.module.modules.render.Capes;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.UUID;

@Mixin(value = {AbstractClientPlayer.class})
abstract class MixinAbstractClientPlayer {

    @Shadow
    @Nullable
    protected abstract NetworkPlayerInfo getPlayerInfo();

    @Inject(method = {"getLocationCape"}, at = {@At(value = "HEAD")}, cancellable = true)
    public void getLocationCape(CallbackInfoReturnable<ResourceLocation> callbackInfoReturnable) {
        if (ModuleManager.getModuleByName("Capes").isEnabled()) {
            NetworkPlayerInfo info = this.getPlayerInfo();
            UUID uuid = null;
            if (info != null) {
                uuid = this.getPlayerInfo().getGameProfile().getId();
            }
            ResourceLocation cape = Capes.getCapeResource();
            ResourceLocation getGifCape = Capes.getCapeResource();
            if (uuid != null && Capes.hasCape(uuid)) {
                callbackInfoReturnable.setReturnValue(cape);
                callbackInfoReturnable.setReturnValue(getGifCape);
            }
        }
    }
}

