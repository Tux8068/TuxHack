package me.tux.tuxhack.module.modules.chat;

import me.tux.tuxhack.TuxHack;
import me.tux.tuxhack.command.Command;
import me.tux.tuxhack.event.events.PacketEvent;
import me.tux.tuxhack.event.events.TotemPopEvent;
import me.tux.tuxhack.module.Module;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.network.play.server.SPacketEntityStatus;
import java.util.function.Predicate;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import java.util.HashMap;

public class TotemCounter extends Module
{
    private HashMap<String, Integer> popList;
    @EventHandler
    public Listener<TotemPopEvent> totemPopEvent;
    @EventHandler
    public Listener<PacketEvent.Receive> totemPopListener;

    public TotemCounter() {
        super("PopCounter", Category.Chat);
        this.popList = new HashMap<String, Integer>();
        final int[] popCounter = new int[1];
        final int[] newPopCounter = {0};
        this.totemPopEvent = new Listener<TotemPopEvent>(event -> {
            if (this.popList == null) {
                this.popList = new HashMap<String, Integer>();
            }
            if (this.popList.get(event.getEntity().getName()) == null) {
                this.popList.put(event.getEntity().getName(), 1);
                Command.sendClientMessage(ChatFormatting.AQUA + event.getEntity().getName() + ChatFormatting.GREEN + " popped " + ChatFormatting.GOLD + 1 + ChatFormatting.YELLOW + " a totem!");
            }
            else if (this.popList.get(event.getEntity().getName()) != null) {
                popCounter[0] = this.popList.get(event.getEntity().getName());
                newPopCounter[0] = ++popCounter[0];
                this.popList.put(event.getEntity().getName(), newPopCounter[0]);
                Command.sendClientMessage(ChatFormatting.AQUA + event.getEntity().getName() + ChatFormatting.GREEN + " popped " + ChatFormatting.GOLD + newPopCounter[0] + ChatFormatting.YELLOW + " totems!");

            }
            return;
        }, (Predicate<TotemPopEvent>[])new Predicate[0]);
        final SPacketEntityStatus[] packet = new SPacketEntityStatus[1];
        final Entity[] entity = new Entity[1];
        this.totemPopListener = new Listener<PacketEvent.Receive>(event -> {
            if (TotemCounter.mc.world != null && TotemCounter.mc.player != null) {
                if (event.getPacket() instanceof SPacketEntityStatus) {
                    packet[0] = (SPacketEntityStatus)event.getPacket();
                    if (packet[0].getOpCode() == 35) {
                        entity[0] = packet[0].getEntity((World)TotemCounter.mc.world);
                        TuxHack.EVENT_BUS.post(new TotemPopEvent(entity[0]));
                    }
                }
            }
        }, (Predicate<PacketEvent.Receive>[])new Predicate[0]);
    }

    @Override
    public int onUpdate() {
        for (final EntityPlayer player : TotemCounter.mc.world.playerEntities) {
            if (player.getHealth() <= 0.0f && this.popList.containsKey(player.getName())) {
                Command.sendRawMessage(ChatFormatting.DARK_AQUA + player.getName() + ChatFormatting.DARK_RED + " died after popping " + ChatFormatting.GOLD + this.popList.get(player.getName()) + ChatFormatting.GOLD + " totems!");
                this.popList.remove(player.getName(), this.popList.get(player.getName()));
            }
        }
        return 0;
    }

    public void onEnable() {
        TuxHack.EVENT_BUS.subscribe(this);
        this.popList = new HashMap<String, Integer>();
    }
}
