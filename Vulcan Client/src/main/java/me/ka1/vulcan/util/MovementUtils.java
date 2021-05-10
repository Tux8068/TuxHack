package me.ka1.vulcan.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

public class MovementUtils {
    static Minecraft mc = Minecraft.getMinecraft();


    public static boolean isInHole(Entity entity) {
        return isBlockValid(new BlockPos(entity.posX, entity.posY, entity.posZ));
    }

    public static boolean isBlockValid(BlockPos blockPos) {
        return (isBedrockHole(blockPos) || isObbyHole(blockPos) || isBothHole(blockPos));
    }

    public static boolean isObbyHole(BlockPos blockPos) {
        BlockPos[] touchingBlocks = { blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west(), blockPos.down() };
        for (BlockPos pos : touchingBlocks) {
            IBlockState touchingState = mc.world.getBlockState(pos);
            if (touchingState.getBlock() == Blocks.AIR || touchingState.getBlock() != Blocks.OBSIDIAN)
                return false;
        }
        return true;
    }

    public static boolean isBedrockHole(BlockPos blockPos) {
        BlockPos[] touchingBlocks = { blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west(), blockPos.down() };
        for (BlockPos pos : touchingBlocks) {
            IBlockState touchingState = mc.world.getBlockState(pos);
            if (touchingState.getBlock() == Blocks.AIR || touchingState.getBlock() != Blocks.BEDROCK)
                return false;
        }
        return true;
    }

    public static boolean isBothHole(BlockPos blockPos) {
        BlockPos[] touchingBlocks = { blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west(), blockPos.down() };
        for (BlockPos pos : touchingBlocks) {
            IBlockState touchingState = mc.world.getBlockState(pos);
            if (touchingState.getBlock() == Blocks.AIR || (touchingState.getBlock() != Blocks.BEDROCK && touchingState.getBlock() != Blocks.OBSIDIAN))
                return false;
        }
        return true;
    }



    public static double calcMoveYaw(float yawIn) {
        float moveForward = roundedForward;
        float moveString = roundedStrafing;

        float strafe = 90 * moveString;
        if (moveForward != 0f) {
            strafe *= moveForward * 0.5f;
        } else strafe *= 1f;
        //       strafe *= if (moveForward != 0F) moveForward * 0.5F else 1F;
        float yaw = yawIn - strafe;
        if (moveForward < 0f) {
            yaw -= 180;
        } else yaw -= 0;

        //yaw -= if (moveForward < 0F) 180 else 0;

        return Math.toRadians(yaw);
    }

    private static float roundedForward = getRoundedMovementInput(mc.player.movementInput.moveForward);

    private static float roundedStrafing = getRoundedMovementInput(mc.player.movementInput.moveStrafe);

    private static float getRoundedMovementInput(Float input) {
        if (input > 0) {
            input = 1f;
        } else if (input < 0) {
            input = -1f;
        } else input = 0f;
        return input;
    }

    public static double[] forward(final double speed) {
        float forward = Minecraft.getMinecraft().player.movementInput.moveForward;
        float side = Minecraft.getMinecraft().player.movementInput.moveStrafe;
        float yaw = Minecraft.getMinecraft().player.prevRotationYaw + (Minecraft.getMinecraft().player.rotationYaw - Minecraft.getMinecraft().player.prevRotationYaw) * Minecraft.getMinecraft().getRenderPartialTicks();
        if (forward != 0.0f) {
            if (side > 0.0f) {
                yaw += ((forward > 0.0f) ? -45 : 45);
            }
            else if (side < 0.0f) {
                yaw += ((forward > 0.0f) ? 45 : -45);
            }
            side = 0.0f;
            if (forward > 0.0f) {
                forward = 1.0f;
            }
            else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        final double sin = Math.sin(Math.toRadians(yaw + 90.0f));
        final double cos = Math.cos(Math.toRadians(yaw + 90.0f));
        final double posX = forward * speed * cos + side * speed * sin;
        final double posZ = forward * speed * sin - side * speed * cos;
        return new double[]{posX, posZ};
    }
}
