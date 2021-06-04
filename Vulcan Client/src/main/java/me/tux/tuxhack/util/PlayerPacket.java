package me.tux.tuxhack.util;

import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

public class PlayerPacket {

    private final Vec3d position;
    private final Vec2f rotation;

    private PlayerPacket(Vec3d position, Vec2f rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public PlayerPacket(Vec2f rotation) {
        this(null, rotation);
    }

    public PlayerPacket(Vec3d position) {
        this(position, null);
    }

    public Vec3d getPosition() {
        return position;
    }

    public Vec2f getRotation() {
        return rotation;
    }
}