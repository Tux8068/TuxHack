package me.tux.tuxhack.module.modules.chat;

import me.tux.tuxhack.command.Command;
import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.setting.Setting;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Announcer extends Module {
    public Announcer() {
        super("Announcer", "announces what you do in chat", Category.CHAT);
    }

    public static int blockBrokeDelay = 0;
    static int blockPlacedDelay = 0;
    static int jumpDelay = 0;
    static int attackDelay = 0;
    static int eattingDelay = 0;

    static long lastPositionUpdate;
    static double lastPositionX;
    static double lastPositionY;
    static double lastPositionZ;
    private static double speed;
    String heldItem = "";


    public static String walkMessage;

    public Setting.Boolean clientSide;
    private Setting.Boolean walk;
    private Setting.Integer delay;
    private Setting.Mode lang;


    public boolean setup() {
        ArrayList<String> modes = new ArrayList<>();
        modes.add("English");
        modes.add("Portuguese");
        modes.add("Australian");
        lang = registerMode("Language", "Language", modes, "English");
        clientSide = this.registerBoolean("ClientSide", "ClientSide", false);
        walk = this.registerBoolean("Walk", "Walk", true);
        delay = this.registerInteger("Delay", "Delay", 5, 0, 30);
        return false;
    }


    @Override
    public int onUpdate() {
        blockBrokeDelay++;
        blockPlacedDelay++;
        jumpDelay++;
        attackDelay++;
        eattingDelay++;
        heldItem = mc.player.getHeldItemMainhand().getDisplayName();

        if (lang.getValue().equals("English")) {
            walkMessage = "I moved {blocks} blocks Thanks to Tuxhack :^)";

        }
        if (lang.getValue().equals("Portuguese")) {
            walkMessage = "Mudei {blocks} blocos gracas ao Tuxhack: ^)";

        }
        if (lang.getValue().equals("Australian")) {
            walkMessage = "oi Bogan, i just walked {blocks} blocks with my barbie :^)";
        }

        if (walk.getValue()) {
            if (lastPositionUpdate + (5000L * delay.getValue()) < System.currentTimeMillis()) {

                double d0 = lastPositionX - mc.player.lastTickPosX;
                double d2 = lastPositionY - mc.player.lastTickPosY;
                double d3 = lastPositionZ - mc.player.lastTickPosZ;

                speed = Math.sqrt(d0 * d0 + d2 * d2 + d3 * d3);

                if (!(speed <= 1) && !(speed > 5000)) {
                    String walkAmount = new DecimalFormat("0").format(speed);

                    if (clientSide.getValue()) {
                        Command.sendRawMessage(walkMessage.replace("{blocks}", walkAmount));
                    } else {
                        mc.player.sendChatMessage(walkMessage.replace("{blocks}", walkAmount));
                    }
                    lastPositionUpdate = System.currentTimeMillis();
                    lastPositionX = mc.player.lastTickPosX;
                    lastPositionY = mc.player.lastTickPosY;
                    lastPositionZ = mc.player.lastTickPosZ;
                }
            }
        }

        return 0;
    }
}
