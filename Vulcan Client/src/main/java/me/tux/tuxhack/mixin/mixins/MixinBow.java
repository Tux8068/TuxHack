package me.tux.tuxhack.mixin.mixins;

import me.tux.tuxhack.TuxHack;
import me.tux.tuxhack.event.events.EventBowEnd;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemBow.class)
public class MixinBow {
    @Inject(method="onPlayerStoppedUsing", at=@At("HEAD"))
    public void PlayerStoppedUsing(ItemStack itemStack, World world, EntityLivingBase livingbase, int durabillity, CallbackInfo CI) {
        TuxHack.EVENT_BUS.post(new EventBowEnd(itemStack, world, livingbase));
    }
}