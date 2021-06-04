package me.tux.tuxhack.util;

import me.tux.tuxhack.TuxHack;
import me.tux.tuxhack.event.events.PacketEvent;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;

public class SpoofRotationUtil {

    private static final Minecraft mc = Minecraft.getMinecraft();
    public static final SpoofRotationUtil ROTATION_UTIL = new SpoofRotationUtil();

    private int rotationConnections = 0;

    private boolean shouldSpoofAngles;
    private boolean isSpoofingAngles;
    private double yaw;
    private double pitch;

    // Forces only ever one
    private SpoofRotationUtil() {

    }

    public void onEnable() {
        rotationConnections++;
        if (rotationConnections == 1)
            TuxHack.getInstance().getEventManager().subscribe(this);
    }

    public void onDisable() {
        rotationConnections--;
        if (rotationConnections == 0)
            TuxHack.getInstance().getEventManager().unsubscribe(this);

    }

    public void lookAtPacket(double px, double py, double pz, EntityPlayer me) {
        double[] v = EntityUtil.calculateLookAt(px, py, pz, me);
        this.setYawAndPitch((float) v[0], (float) v[1]);
    }

    public void setYawAndPitch(float yaw1, float pitch1) {
        yaw = yaw1;
        pitch = pitch1;
        isSpoofingAngles = true;
    }

    public void resetRotation() {
        if (isSpoofingAngles) {
            yaw = mc.player.rotationYaw;
            pitch = mc.player.rotationPitch;
            isSpoofingAngles = false;
        }
    }

    public void shouldSpoofAngles(boolean e) {
        shouldSpoofAngles = e;
    }

    public boolean isSpoofingAngles() {
        return isSpoofingAngles;
    }

    @SuppressWarnings("unused")
    @EventHandler
    private final Listener<PacketEvent.Send> packetSendListener = new Listener<>(event -> {
        Packet packet = event.getPacket();
        if (packet instanceof CPacketPlayer && shouldSpoofAngles) {
            if (isSpoofingAngles) {
                ((CPacketPlayer) packet).yaw = (float) yaw;
                ((CPacketPlayer) packet).pitch = (float) pitch;
            }
        }
    });
}