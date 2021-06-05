package me.tux.tuxhack.module.modules.chat;


import java.util.ArrayList;

import me.tux.tuxhack.TuxHack;
import me.tux.tuxhack.command.Command;
import me.tux.tuxhack.event.events.PacketEvent;
import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.module.modules.client.CommandColor;
import me.tux.tuxhack.setting.Setting;
import net.minecraft.network.play.client.CPacketChatMessage;
import java.util.function.Predicate;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.util.text.TextComponentString;
import java.util.Date;
import java.text.SimpleDateFormat;
import me.zero.alpine.listener.EventHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import me.zero.alpine.listener.Listener;

public class ChatModifier extends Module
{
    public Setting.Boolean clearBkg;
    Setting.Boolean chattimestamps;
    Setting.Mode format;
    Setting.Mode color;
    Setting.Mode decoration;
    Setting.Boolean space;
    Setting.Boolean greentext;
    @EventHandler
    private final Listener<ClientChatReceivedEvent> chatReceivedEventListener;
    @EventHandler
    private final Listener<PacketEvent.Send> listener;

    public ChatModifier() {
        super("ChatModifier", Category.Misc);
        final String[] decoLeft = new String[1];
        final String[] decoRight = new String[1];
        final String[] dateFormat = new String[1];
        final String[] date = new String[1];
        final TextComponentString textComponentString = null;
        final TextComponentString[] time = new TextComponentString[1];
        this.chatReceivedEventListener = new Listener<ClientChatReceivedEvent>(event -> {
            if (this.chattimestamps.getValue()) {
                decoLeft[0] = (this.decoration.getValue().equalsIgnoreCase(" ") ? "" : this.decoration.getValue().split(" ")[0]);
                decoRight[0] = (this.decoration.getValue().equalsIgnoreCase(" ") ? "" : this.decoration.getValue().split(" ")[1]);
                if (this.space.getValue()) {
                    decoRight[0] += " ";
                }
                dateFormat[0] = this.format.getValue().replace("H24", "k").replace("H12", "h");
                date[0] = new SimpleDateFormat(dateFormat[0]).format(new Date());
                new TextComponentString(CommandColor.getTextColor() + decoLeft[0] + date[0] + decoRight[0] + ChatFormatting.RESET);
                time[0] = textComponentString;
                event.setMessage(time[0].appendSibling(event.getMessage()));
            }
            return;
        }, (Predicate<ClientChatReceivedEvent>[])new Predicate[0]);
        final String[] message = new String[1];
        final String[] prefix = new String[1];
        final String[] prefix2 = new String[1];
        final String[] s = new String[1];
        this.listener = new Listener<PacketEvent.Send>(event -> {
            if (this.greentext.getValue() && event.getPacket() instanceof CPacketChatMessage) {
                if (!((CPacketChatMessage)event.getPacket()).getMessage().startsWith("/") && !((CPacketChatMessage)event.getPacket()).getMessage().startsWith(Command.getPrefix())) {
                    message[0] = ((CPacketChatMessage)event.getPacket()).getMessage();
                    prefix[0] = "";
                    prefix2[0] = ">";
                    s[0] = prefix2[0] + message[0];
                    if (s[0].length() <= 255) {
                        ((CPacketChatMessage)event.getPacket()).message = s[0];
                    }
                }
            }
        }, (Predicate<PacketEvent.Send>[])new Predicate[0]);
    }

    @Override
    public boolean setup() {
        final ArrayList<String> formats = new ArrayList<String>();
        formats.add("H24:mm");
        formats.add("H12:mm");
        formats.add("H12:mm a");
        formats.add("H24:mm:ss");
        formats.add("H12:mm:ss");
        formats.add("H12:mm:ss a");
        final ArrayList<String> deco = new ArrayList<String>();
        deco.add("< >");
        deco.add("[ ]");
        deco.add("{ }");
        deco.add(" ");
        final ArrayList<String> colors = new ArrayList<String>();
        for (final ChatFormatting cf : ChatFormatting.values()) {
            colors.add(cf.getName());
        }
        this.clearBkg = this.registerBoolean("Clear Chat", "ClearChat", false);
        this.greentext = this.registerBoolean("Green Text", "GreenText", false);
        this.chattimestamps = this.registerBoolean("Chat Time Stamps", "ChatTimeStamps", false);
        this.format = this.registerMode("Format", "Format", formats, "H24:mm");
        this.decoration = this.registerMode("Deco", "Deco", deco, "[ ]");
        this.color = this.registerMode("Color", "Colors", colors, ChatFormatting.GRAY.getName());
        this.space = this.registerBoolean("Space", "Space", false);
        return false;
    }

    public void onEnable() {
        TuxHack.EVENT_BUS.subscribe(this);
    }

    public int onDisable() {
        TuxHack.EVENT_BUS.unsubscribe(this);
        return 0;
    }
}
