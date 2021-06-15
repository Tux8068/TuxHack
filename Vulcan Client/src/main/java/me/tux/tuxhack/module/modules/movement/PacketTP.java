package me.tux.tuxhack.module.modules.movement;


import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.module.ModuleManager;
import me.tux.tuxhack.setting.Setting;
import me.tux.tuxhack.util.MotionUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class PacketTP extends Module {
    public PacketTP() {
        super("PacketTP", "tp with packets", Category.MOVEMENT);

    }

    Setting.Boolean confirm;
    Setting.Double speed;

    @Override
    public boolean setup() {

        speed = registerDouble("TPSpeed", "TPSpeed", 10.0, 0.0, 20.0);
        confirm = registerBoolean("OnDisable", "OnDisable", true);
        return false;
    }


    @Override
    public int onUpdate() {
        final double posX = Speed.mc.player.posX;
        final double posY = Speed.mc.player.posY;
        final double posZ = Speed.mc.player.posZ;
        final boolean ground = Speed.mc.player.onGround;
        final double[] dir1 = MotionUtils.forward(0.5);
        final BlockPos pos = new BlockPos(posX + dir1[0], posY, posZ + dir1[1]);
        MotionUtils.setSpeed((EntityLivingBase) Speed.mc.player, 0.0);
        for (double x = 0.0625; x < this.speed.getValue(); x += 0.262) {
            final double[] dir2 = MotionUtils.forward(x);
            Speed.mc.player.connection.sendPacket((Packet) new CPacketPlayer.Position(posX + dir2[0], posY, posZ + dir2[1], ground));
            if (Speed.mc.world.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ())).getBlock() instanceof BlockAir) {
                Speed.mc.player.connection.sendPacket((Packet) new CPacketPlayer.Position(posX + Speed.mc.player.motionX, (Speed.mc.player.posY <= 10.0) ? 255.0 : 1.0, posZ + Speed.mc.player.motionZ, ground));
            }


            return 0;
        }

        return 0;
    }
}