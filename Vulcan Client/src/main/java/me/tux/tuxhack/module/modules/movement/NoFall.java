package me.tux.tuxhack.module.modules.movement;

import me.tux.tuxhack.command.Command;
import me.tux.tuxhack.event.events.PacketEvent;
import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.setting.Setting;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;

public class NoFall extends Module {
    Setting.Boolean twodelay;
    Setting.Integer tickDelay;
    Setting.Double speed;
    Setting.Boolean p;
    Setting.Boolean hh;
    public NoFall() {

        super("NoFall", "nofall", Category.Movement);

        twodelay = registerBoolean("2Delay", "2Delay", true);
        tickDelay = registerInteger("TickDelay", "TickDelay", 2, 0, 40);
        speed = registerDouble("speed","speed",1,0.1,2);
        p = registerBoolean("p","p", true);
        hh = registerBoolean("hh","hh", true);
    }

    @Override
    public int onUpdate() {


        mc.gameSettings.keyBindSneak.isKeyDown();
        mc.getConnection().sendPacket(new CPacketPlayer.Position(mc.player.posX + mc.player.motionX, mc.player.posY - 80680, mc.player.posZ + mc.player.motionZ, true));
        mc.getConnection().sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
        mc.player.noClip = true;
        if (tickDelay.getValue() > 0)
            if (mc.player.ticksExisted % tickDelay.getValue() != 0 && twodelay.getValue()) return 0;
        boolean yes = false;
        mc.player.noClip = true;
        if (yes)
            mc.getConnection().sendPacket(new CPacketPlayer.Position(mc.player.posX + mc.player.motionX, mc.player.posY + (mc.player.posY < (hh.getValue() ? 1.1 : -0.98) ? (speed.getValue() / 100) : 0) + (mc.gameSettings.keyBindJump.isKeyDown() ? (speed.getValue() / 100) : 0) - (mc.gameSettings.keyBindSneak.isKeyDown() ? (speed.getValue() / 100) : 0), mc.player.posZ + mc.player.motionZ, false)); // mc.player.rotationYaw, mc.player.rotationPitch, false));
        mc.player.noClip = true;
        if (yes)
            mc.getConnection().sendPacket(new CPacketPlayer.Position(mc.player.posX + mc.player.motionX, mc.player.posY - 42069, mc.player.posZ + mc.player.motionZ, true));
        return 0;
    }
    private boolean getAirBlocks(IBlockState blockState, BlockPos blockPos) {
        if (blockState.getBlock() == Blocks.AIR) {
            return false;
        } else if (mc.player.getDistanceSq(blockPos) < 1.0D /*PUT YOUR SETTING VALUE HERE*/) {
            return false;
        } else if (mc.world.getBlockState(blockPos.up()).getBlock() == Blocks.AIR) {
            return false;
        }
        return false;
    }

    }





