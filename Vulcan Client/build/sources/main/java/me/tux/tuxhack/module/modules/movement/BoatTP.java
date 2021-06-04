package me.tux.tuxhack.module.modules.movement;

import me.tux.tuxhack.module.Module;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.FMLClientHandler;
import org.lwjgl.input.Keyboard;

import java.util.Comparator;

public class BoatTP extends Module {
    public BoatTP() {
        super("BoatTP", "Notifies you when you need to mend your armor", Category.Movement);
    }
    public KeyUpNotifier upKeyNotif = new KeyUpNotifier();

    public KeyDownNotifier downKeyNotif = new KeyDownNotifier();

    public static void clip(int y) {
        Entity boat = mc.player.getRidingEntity();
        boat.setPositionAndUpdate(mc.player.posX, y, mc.player.posZ);
        FMLClientHandler.instance().getClientToServerNetworkManager().sendPacket((Packet)new CPacketVehicleMove(boat));
    }

    public static int findNextAvailableSpaceUp(int start) {
        int up = start;
        int playerX = (int)mc.player.posX;
        int playerZ = (int)mc.player.posZ;
        for (int i = start + 1; i < ((mc.player.dimension != 1) ? 257 : 125); i++) {
            if ((mc.world.getBlockState(new BlockPos(playerX, i, playerZ)).getBlock()).material.equals(Material.AIR) &&
                    (mc.world.getBlockState(new BlockPos(playerX, i + 1, playerZ)).getBlock()).material.equals(Material.AIR) &&
                    !(mc.world.getBlockState(new BlockPos(playerX, i - 1, playerZ)).getBlock()).material.equals(Material.AIR)) {
                up = i;
                return up;
            }
        }
        return up;
    }

    public static int findNextAvailableSpaceDown(int start) {
        int down = start;
        int playerX = (int)mc.player.posX;
        int playerZ = (int)mc.player.posZ;
        for (int i = start - 1; i > 4; i--) {
            if ((mc.world.getBlockState(new BlockPos(playerX, i, playerZ)).getBlock()).material.equals(Material.AIR) &&
                    (mc.world.getBlockState(new BlockPos(playerX, i + 1, playerZ)).getBlock()).material.equals(Material.AIR) &&
                    !(mc.world.getBlockState(new BlockPos(playerX, i - 1, playerZ)).getBlock()).material.equals(Material.AIR)) {
                down = i;
                return down;
            }
        }
        return down;
    }

    public int onUpdate() {
        if (mc.player.ticksExisted % 2 != 0)
            return 0;
        this.upKeyNotif.set(Keyboard.isKeyDown(200));
        this.downKeyNotif.set(Keyboard.isKeyDown(208));
        mc.world.getLoadedEntityList().stream()
                .filter(entity -> entity instanceof EntityBoat)
                .map(entity -> (EntityBoat)entity)
                .filter(entityBoat -> (mc.player.getDistance((Entity)entityBoat) < 3.0F))
                .min(Comparator.comparing(entityBoat -> Float.valueOf(entityBoat.getDistance((Entity)mc.player))))
                .ifPresent(entityBoat -> {
                    if (mc.player.getRidingEntity() != null)
                        return;
                    mc.playerController.interactWithEntity((EntityPlayer)mc.player, (Entity)entityBoat, EnumHand.MAIN_HAND);
                });
        return 0;
    }

    protected void onEnable() {
        if (mc.player == null) {
            disable();
            return;
        }
        mc.player.sendMessage((ITextComponent)new TextComponentString(TextFormatting.AQUA + "BoatTP enabled "  + TextFormatting.WHITE +  "get in a boat to use."));
    }

    class KeyUpNotifier extends LazyKeyPressNotifier {
        public void onKeyDown() {
            BoatTP.mc.player.sendMessage((ITextComponent)new TextComponentString(TextFormatting.GREEN + "Going up!"));
            if (BoatTP.mc.player.getRidingEntity() instanceof EntityBoat &&
                    FMLClientHandler.instance().getClientToServerNetworkManager() != null &&
                    Keyboard.isKeyDown(200))
                BoatTP.clip(BoatTP.findNextAvailableSpaceUp((int)(BoatTP.mc.player.getRidingEntity()).posY));
        }
    }

    class KeyDownNotifier extends LazyKeyPressNotifier {
        public void onKeyDown() {
            BoatTP.mc.player.sendMessage((ITextComponent)new TextComponentString(TextFormatting.RED + "Going down!"));
            if (BoatTP.mc.player.getRidingEntity() instanceof EntityBoat &&
                    FMLClientHandler.instance().getClientToServerNetworkManager() != null &&
                    Keyboard.isKeyDown(208))
                BoatTP.clip(BoatTP.findNextAvailableSpaceDown((int)(BoatTP.mc.player.getRidingEntity()).posY));
        }
    }

    abstract class LazyKeyPressNotifier {
        boolean pressed;

        public void set(boolean value) {
            if (value && !this.pressed)
                onKeyDown();
            this.pressed = value;
        }

        public abstract void onKeyDown();
    }
}

