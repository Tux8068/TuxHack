package me.ka1.vulcan.module.modules.chat;

import me.ka1.vulcan.command.Command;
import me.ka1.vulcan.event.events.PacketEvent;
import me.ka1.vulcan.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.client.CPacketChatMessage;


public class ChatEncrypt extends Module{
    public ChatEncrypt(){super("ChatEncrypt", "Hide da secrets", Module.Category.Chat);}

    @EventHandler
    private final Listener<PacketEvent.Send> listener = new Listener<>(event -> {
        if (event.getPacket() instanceof CPacketChatMessage) {

            //disables suffix if a specail character is at the beginning of the msg (antiCommandBreak lole)
            if (((CPacketChatMessage) event.getPacket()).getMessage().startsWith("/") || ((CPacketChatMessage) event.getPacket()).getMessage().startsWith(Command.getPrefix()))
                return;
            if (((CPacketChatMessage) event.getPacket()).getMessage().startsWith("!") || ((CPacketChatMessage) event.getPacket()).getMessage().startsWith(Command.getPrefix()))
                return;
            if (((CPacketChatMessage) event.getPacket()).getMessage().startsWith(";") || ((CPacketChatMessage) event.getPacket()).getMessage().startsWith(Command.getPrefix()))
                return;
            if (((CPacketChatMessage) event.getPacket()).getMessage().startsWith(".") || ((CPacketChatMessage) event.getPacket()).getMessage().startsWith(Command.getPrefix()))
                return;
            if (((CPacketChatMessage) event.getPacket()).getMessage().startsWith(":") || ((CPacketChatMessage) event.getPacket()).getMessage().startsWith(Command.getPrefix()))
                return;
            if (((CPacketChatMessage) event.getPacket()).getMessage().startsWith("-") || ((CPacketChatMessage) event.getPacket()).getMessage().startsWith(Command.getPrefix()))
                return;

            String message = ((CPacketChatMessage) event.getPacket()).getMessage();

            encrypt(message);
        }
    });

    public void encrypt(String message){


        String encryptedmessage = "aaa " + message;
    }
}
