package me.tux.tuxhack.module.modules.player;

import me.tux.tuxhack.event.events.PacketEvent;
import me.tux.tuxhack.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;

public class Velocity extends Module {
    public Velocity() {
        super("Velocity", "Stops you from taking knockback", Module.Category.Player);
    }
    

    @EventHandler
    private final Listener<PacketEvent.Receive> packetReceiveListener = new Listener<>(event -> {
        if (event.getPacket() instanceof SPacketEntityVelocity) {
            event.cancel();
        }

        if (event.getPacket() instanceof SPacketExplosion) {
            SPacketExplosion sPacketExplosion = (SPacketExplosion) event.getPacket();
            sPacketExplosion.motionX = 0;
            sPacketExplosion.motionY = 0;
            sPacketExplosion.motionZ = 0;
        }
        if (Velocity.mc.player.hurtTime == Velocity.mc.player.maxHurtTime && Velocity.mc.player.maxHurtTime > 0) {
            final EntityPlayerSP player = Velocity.mc.player;
            player.motionX *= 100.0f;
            final EntityPlayerSP player2 = Velocity.mc.player;
            player2.motionY *= 100.0f;
            final EntityPlayerSP player3 = Velocity.mc.player;
            player3.motionZ *= 100.0f;
        }
    });
}


