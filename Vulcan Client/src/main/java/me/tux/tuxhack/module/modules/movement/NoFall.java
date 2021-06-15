package me.tux.tuxhack.module.modules.movement;

import me.tux.tuxhack.command.Command;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.Entity;
import me.tux.tuxhack.util.EntityUtil;
import me.tux.tuxhack.setting.Setting;
import me.tux.tuxhack.module.Module;

public class NoFall extends Module
{
    Setting.Boolean twodelay;
    Setting.Integer tickDelay;
    Setting.Integer blocksFromGround;
    Setting.Integer waitTicks;
    Setting.Double speed;
    Setting.Boolean p;
    Setting.Boolean hh;
    private int ticks;

    public NoFall() {
        super("NoFall", "nofall, works on most servers", Category.MOVEMENT);
        this.blocksFromGround = this.registerInteger("BlocksFromGround", "GroundBlocks", 1, 0, 10);
        this.twodelay = this.registerBoolean("Delay", "2Delay", true);
        this.tickDelay = this.registerInteger("TickDelay", "TickDelay", 2, 0, 40);
        this.waitTicks = this.registerInteger("WaitTicks", "WaitTicks", 20, 0, 60);
        this.speed = this.registerDouble("speed", "speed", 1.0, 0.1, 2.0);
        this.p = this.registerBoolean("Freeze", "Freeze", true);
        this.hh = this.registerBoolean("NoDamage", "NoDamage", true);
    }

    @Override
    protected void onEnable() {
        this.ticks = 0;
    }

    @Override
    public int onUpdate() {
        if (this.ticks > this.tickDelay.getValue()) {
            this.disable();
            return 0;
        }
        final BlockPos playerPos = EntityUtil.getPosition((Entity)NoFall.mc.player);
        for (int i = 0; i < this.blocksFromGround.getValue(); ++i) {
            if (NoFall.mc.world.getBlockState(new BlockPos((Vec3i)playerPos).down(i + 1)).getBlock() != Blocks.AIR) {
                this.doThing();
            }
        }
        return 0;
    }

    private void doThing() {
        ++this.ticks;
        NoFall.mc.gameSettings.keyBindSneak.isKeyDown();
        NoFall.mc.getConnection().sendPacket((Packet)new CPacketPlayer.Position(NoFall.mc.player.posX + NoFall.mc.player.motionX, NoFall.mc.player.posY - 80680.0, NoFall.mc.player.posZ + NoFall.mc.player.motionZ, true));
        NoFall.mc.getConnection().sendPacket((Packet)new CPacketPlayer.Position(NoFall.mc.player.posX, NoFall.mc.player.posY, NoFall.mc.player.posZ, false));
        NoFall.mc.player.noClip = true;
        if (this.tickDelay.getValue() > 0 && NoFall.mc.player.ticksExisted % this.tickDelay.getValue() != 0 && this.twodelay.getValue()) {
            return;
        }
        final boolean yes = false;
        NoFall.mc.player.noClip = true;
        if (yes) {
            NoFall.mc.getConnection().sendPacket((Packet)new CPacketPlayer.Position(NoFall.mc.player.posX + NoFall.mc.player.motionX, NoFall.mc.player.posY + ((NoFall.mc.player.posY < (this.hh.getValue() ? 1.1 : -0.98)) ? (this.speed.getValue() / 100.0) : 0.0) + (NoFall.mc.gameSettings.keyBindJump.isKeyDown() ? (this.speed.getValue() / 100.0) : 0.0) - (NoFall.mc.gameSettings.keyBindSneak.isKeyDown() ? (this.speed.getValue() / 100.0) : 0.0), NoFall.mc.player.posZ + NoFall.mc.player.motionZ, false));
        }
        NoFall.mc.player.noClip = true;
        if (yes) {
            NoFall.mc.getConnection().sendPacket((Packet)new CPacketPlayer.Position(NoFall.mc.player.posX + NoFall.mc.player.motionX, NoFall.mc.player.posY - 42069.0, NoFall.mc.player.posZ + NoFall.mc.player.motionZ, true));
        }
        if (this.p.getValue()) {}
        Command.sendRawMessage(TextFormatting.AQUA + " " + TextFormatting.UNDERLINE + "You are now frosty :^)");
    }
}
