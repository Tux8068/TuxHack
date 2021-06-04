package me.tux.tuxhack.mixin.mixins;

import me.tux.tuxhack.TuxHack;
import me.tux.tuxhack.event.events.DamageBlockEvent;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerControllerMP.class)
public class MixinPlayerControllerMP {

    @Inject(method = "onPlayerDamageBlock", at = @At("HEAD"), cancellable = true)
    public void onPlayerDamageBlock(BlockPos pos, EnumFacing facing, CallbackInfoReturnable<Boolean> ci)
    {
        DamageBlockEvent event = new DamageBlockEvent(pos, facing);
        TuxHack.getInstance().getEventManager().post(event);
        if (event.isCancelled())
        {
            ci.setReturnValue(false);
            ci.cancel();
        }
    }

}
