package me.ka1.vulcan.module.modules.player;

import me.ka1.vulcan.event.events.PacketEvent;
import me.ka1.vulcan.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
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
    });


}
