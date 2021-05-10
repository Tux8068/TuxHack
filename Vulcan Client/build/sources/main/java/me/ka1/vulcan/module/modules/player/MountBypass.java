package me.ka1.vulcan.module.modules.player;

import me.ka1.vulcan.event.events.EventNetworkPacketEvent;
import me.ka1.vulcan.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.passive.AbstractChestHorse;
import net.minecraft.network.play.client.CPacketUseEntity;

public class MountBypass extends Module {
    public MountBypass() {
        super("MountBypass", "Forcefully mounts chests on entities", Category.Player);
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

