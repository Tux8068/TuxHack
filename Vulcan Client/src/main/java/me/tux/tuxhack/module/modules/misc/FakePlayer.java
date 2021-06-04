package me.tux.tuxhack.module.modules.misc;

import com.mojang.authlib.GameProfile;
import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.setting.Setting;
import net.minecraft.client.entity.EntityOtherPlayerMP;

import java.util.ArrayList;
import java.util.UUID;

public class FakePlayer extends Module {
    public FakePlayer() {
        super("FakePlayer", "Creates a fake player", Category.Misc);
    }

    /**
     * @author _KA1
     * 28/3/21
     */

    Setting.Boolean copyInv;
    Setting.Mode multi;
    Setting.Double offset;
    Setting.Double speed;
    Setting.Boolean dynamic;

    public boolean setup() {
        ArrayList<String> modes;
        modes = new ArrayList<>();
        modes.add("Single");
        modes.add("Multi");

        copyInv = registerBoolean("Copy Inventory", "copyInv", true);
        multi = registerMode("Mode", "mode", modes, "Single");
        offset = registerDouble("Offset", "offset", 4, 1, 14);
        return false;
    }

    public void onEnable() {
        if (mc.world == null) {
            return;
        }

        if (multi.getValue().equalsIgnoreCase("Single")) {
            spawnSinglePlayer();
        } else if (multi.getValue().equalsIgnoreCase("Multi")) {
            spawnMultiplePlayers();
        } else {
            return;
        }
    }

    public int onDisable() {
            mc.world.removeEntityFromWorld(-100);
            mc.world.removeEntityFromWorld(-101);
            mc.world.removeEntityFromWorld(-102);
        return 0;
    }

    public void spawnSinglePlayer() {
        EntityOtherPlayerMP fakePlayer = new EntityOtherPlayerMP(mc.world, new GameProfile(UUID.fromString("2a158b00-dbd9-4197-8c69-2c08cf545462"), "TuxISCool"));
        fakePlayer.copyLocationAndAnglesFrom(mc.player);
        fakePlayer.rotationYawHead = mc.player.rotationYawHead;
        mc.world.addEntityToWorld(-100, fakePlayer);
        if (copyInv.getValue()) {
            fakePlayer.inventory.copyInventory(mc.player.inventory);
        }
    }

    public void spawnMultiplePlayers() {
        final double posX = mc.player.posX;
        final double posY = mc.player.posY;
        final double posZ = mc.player.posZ;
        float yaw = mc.player.rotationYawHead;
        float pitch = mc.player.rotationPitch;
        EntityOtherPlayerMP fakePlayer0 = new EntityOtherPlayerMP(mc.world, new GameProfile(UUID.fromString("2a158b00-dbd9-4197-8c69-2c08cf545462"), "TuxISCool"));
        EntityOtherPlayerMP fakePlayer1 = new EntityOtherPlayerMP(mc.world, new GameProfile(UUID.fromString("21210603-f973-416e-a622-de37f723a6c5"), "Target1"));
        EntityOtherPlayerMP fakePlayer2 = new EntityOtherPlayerMP(mc.world, new GameProfile(UUID.fromString("1931df52-4497-4f45-9b36-147aa34019a3"), "Target2"));
        fakePlayer0.setPositionAndRotation(posX - offset.getValue(), posY, posZ, yaw, pitch);
        fakePlayer1.setPositionAndRotation(posX, posY, posZ, yaw, pitch);
        fakePlayer2.setPositionAndRotation(posX + offset.getValue(), posY, posZ, yaw, pitch);
        mc.world.addEntityToWorld(-100, fakePlayer0);
        mc.world.addEntityToWorld(-101, fakePlayer1);
        mc.world.addEntityToWorld(-102, fakePlayer2);
        if (copyInv.getValue()) {
            fakePlayer0.inventory.copyInventory(mc.player.inventory);
            fakePlayer1.inventory.copyInventory(mc.player.inventory);
            fakePlayer2.inventory.copyInventory(mc.player.inventory);
        }
    }
}