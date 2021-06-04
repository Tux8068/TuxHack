package me.tux.tuxhack.module.modules.player;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.tux.tuxhack.command.Command;
import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.setting.Setting;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketPlayer;

public class NoVoid extends Module {
    private Object SPacketDisconnect;

    public NoVoid() {
        super("NoVoid", "antivoid", Category.Movement);
    }

    Setting.Integer rubberband;
    Setting.Boolean AllowLagBack;

    public boolean setup() {
        rubberband = registerInteger("LagBackValue", "RubberBand", 200, 100, 300);
        AllowLagBack = registerBoolean("AllowLagBack", "AllowLagBack", true);
        return false;
    }

    @Override
    public int onUpdate() {
        {
            double yLevel = mc.player.posY;
            if (yLevel <= .5) {
                final CPacketPlayer.Position outOfBoundsPosition = new CPacketPlayer.Position(mc.player.posX, mc.player.posY - 420.69, mc.player.posZ, mc.player.onGround);
                Command.sendRawMessage(("\u00A7aHovering: ") + ChatFormatting.RED + mc.player.getName() + ChatFormatting.WHITE + " in place");
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + rubberband.getValue(), mc.player.posZ, false));
                mc.getConnection().sendPacket(new CPacketPlayer.Position(mc.player.posX + mc.player.motionX, mc.player.posY - 100, mc.player.posZ + mc.player.motionZ, true));
                mc.player.connection.sendPacket(new CPacketPlayer.Position());
                mc.player.connection.sendPacket(new CPacketConfirmTeleport());
            }
        }
        return 0;
    }
}