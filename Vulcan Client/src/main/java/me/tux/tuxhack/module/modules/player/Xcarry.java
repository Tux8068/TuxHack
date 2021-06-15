package me.tux.tuxhack.module.modules.player;

import me.tux.tuxhack.TuxHack;
import me.tux.tuxhack.event.events.PacketEvent;
import me.tux.tuxhack.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.client.CPacketCloseWindow;

public class Xcarry extends Module {
    public Xcarry() {
        super("XCarry", "Lets you store items in crafting slots", Category.PLAYER);
    }

    @EventHandler
    private final Listener<PacketEvent.Send> listener = new Listener<>(event -> {
        if (event.getPacket() instanceof CPacketCloseWindow){
            event.cancel();
        }

    });

    public void onEnable(){
        TuxHack.EVENT_BUS.subscribe(this);
    }

    public int onDisable(){
        TuxHack.EVENT_BUS.unsubscribe(this);
        return 0;
    }
}
