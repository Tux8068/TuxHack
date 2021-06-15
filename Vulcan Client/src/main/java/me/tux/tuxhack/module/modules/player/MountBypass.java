package me.tux.tuxhack.module.modules.player;

import me.tux.tuxhack.event.events.EventNetworkPacketEvent;
import me.tux.tuxhack.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.passive.AbstractChestHorse;
import net.minecraft.network.play.client.CPacketUseEntity;

public class MountBypass extends Module {
    public MountBypass() {
        super("MountBypass", "Forcefully mounts chests on entities", Category.PLAYER);
    }

    @EventHandler
    private Listener<EventNetworkPacketEvent> onPacketEventSend = new Listener<>(event ->
    {
        if (event.getPacket() instanceof CPacketUseEntity) {
            CPacketUseEntity packet = (CPacketUseEntity) event.getPacket();

            if (packet.getEntityFromWorld(mc.world) instanceof AbstractChestHorse) {
                if (packet.getAction() == CPacketUseEntity.Action.INTERACT_AT) {
                    event.cancel();
                }
            }
        }
    });
}

