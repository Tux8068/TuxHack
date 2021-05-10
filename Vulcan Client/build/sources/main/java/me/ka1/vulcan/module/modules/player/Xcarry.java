package me.ka1.vulcan.module.modules.player;

import me.ka1.vulcan.Vulcan;
import me.ka1.vulcan.event.events.PacketEvent;
import me.ka1.vulcan.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.client.CPacketCloseWindow;

public class Xcarry extends Module {
    public Xcarry() {
        super("XCarry", "Lets you store items in crafting slots", Category.Player);
    }

    @EventHandler
    private final Listener<PacketEvent.Send> listener = new Listener<>(event -> {
        if (event.getPacket() instanceof CPacketCloseWindow){
            event.cancel();
        }

    });

    public void onEnable(){
        Vulcan.EVENT_BUS.subscribe(this);
    }

    public void onDisable(){
        Vulcan.EVENT_BUS.unsubscribe(this);
    }
}
