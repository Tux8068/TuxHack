package me.tux.tuxhack.module.modules.player;

import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.util.PlayerUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;

public class Suicide extends Module {
    public Suicide() {
        super("Suicide", "/kills you lol", Category.Player);
    }

    public void onEnable() {
        this.disable();
        mc.player.sendChatMessage("/kill");
        if (this.mc.player != null) {
            mc.player.connection.sendPacket((Packet) new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.049, mc.player.posZ, false));
            mc.player.connection.sendPacket((Packet) new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
            mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, true));
        }
    }
}
