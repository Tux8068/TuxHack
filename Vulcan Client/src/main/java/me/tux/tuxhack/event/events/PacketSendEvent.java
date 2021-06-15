package me.tux.tuxhack.event.events;

import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class PacketSendEvent extends Event
{
    private Packet<?> packet;

    public PacketSendEvent(final Packet<?> packet) {
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return this.packet;
    }

    public void setPacket(final Packet<?> packet) {
        this.packet = packet;
    }
}