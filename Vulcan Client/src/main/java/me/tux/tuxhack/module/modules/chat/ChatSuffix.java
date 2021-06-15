package me.tux.tuxhack.module.modules.chat;

import me.tux.tuxhack.TuxHack;
import me.tux.tuxhack.command.Command;
import me.tux.tuxhack.event.events.PacketEvent;
import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.setting.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.client.CPacketChatMessage;

import java.util.ArrayList;

//Made by _KA1 :)
/*acc cant belive this works rofl
*/

public class ChatSuffix extends Module {
    public ChatSuffix(){super("ChatSuffix", "Adds a suffix to the end of your message", Category.CHAT);}
    Setting.Mode Separator;

    Setting.Mode Mode;

    public boolean setup() {
// settings etc
        ArrayList<String> Separators;
        Separators = new ArrayList<>();
        Separators.add(">>");
        Separators.add("|");
        Separators.add(":^)");
        Separators.add("~uwu~");
        Separator = registerMode("Separator", "Separator", Separators, "|");

        ArrayList<String> Modes;
        Modes = new ArrayList<>();
        Modes.add("TuxHack 1.4");
        Modes.add("tuxhack");
        Modes.add("T3xH3ck");

        Mode = registerMode("Mode", "Mode", Modes, "TuxHack 1.5");
        return false;
    }

    @EventHandler
    private final Listener<PacketEvent.Send> listener = new Listener<>(event -> {
        if (event.getPacket() instanceof CPacketChatMessage) {

            //disables suffix if a special character is at the beginning of the msg (antiCommandBreak lole)
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

            String Separator2 = "|";
            if (Separator.getValue().equalsIgnoreCase(">>")) {
                Separator2 = " >> ";

            } else if (Separator.getValue().equalsIgnoreCase("|")) {
                Separator2 = " | ";

            } else if (Separator.getValue().equalsIgnoreCase(":^)")) {
                    Separator2 = " :^) ";
            } else if (Separator.getValue().equalsIgnoreCase("~uwu~")) {
                Separator2 = " ~uwu~ ";
            // seperators
            }

            String Mode2 = "TuxHack";
            if (Mode.getValue().equalsIgnoreCase("TuxHack 1.5")) {
        Mode2 = "TuxHack 1.4";

            }
            else if (Mode.getValue().equalsIgnoreCase("tuxhack")) {
                Mode2 = "tuxhack";

            }
            else if (Mode.getValue().equalsIgnoreCase("T3xH3ck")) {
                Mode2 = "T3xH3ck";

            }
            
            String old = ((CPacketChatMessage) event.getPacket()).getMessage();
            String suffix = toUnicode(Mode2);
            String s = old + Separator2 + suffix;
            if (s.length() > 255) return;
            ((CPacketChatMessage) event.getPacket()).message = s;
        }
    });

    public void onEnable() {
        TuxHack.EVENT_BUS.subscribe(this);
    }

    public String toUnicode(String s) {
        return s.toLowerCase()
                .replace("a", "a")
                .replace("b", "b")
                .replace("c", "c")
                .replace("d", "d")
                .replace("e", "e")
                .replace("f", "f")
                .replace("g", "g")
                .replace("h", "h")
                .replace("i", "i")
                .replace("j", "j")
                .replace("k", "k")
                .replace("l", "l")
                .replace("m", "m")
                .replace("n", "n")
                .replace("o", "o")
                .replace("p", "p")
                .replace("q", "q")
                .replace("r", "r")
                .replace("s", "s")
                .replace("t", "t")
                .replace("u", "u")
                .replace("v", "v")
                .replace("w", "w")
                .replace("x", "x")
                .replace("y", "y")
                .replace("z", "z");
    }

 public int onDisable() {
     TuxHack.EVENT_BUS.unsubscribe(this);
     return 0;
 }
}
