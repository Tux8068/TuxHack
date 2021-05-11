package me.ka1.vulcan.util.entities;

import me.ka1.vulcan.Vulcan;
import me.ka1.vulcan.util.maths.MathUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class EntityUtil
{

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static Block isColliding(double posX, double posY, double posZ) {
        Block block = null;
        if (mc.player != null) {
            final AxisAlignedBB bb = mc.player.getRidingEntity() != null ? mc.player.getRidingEntity().getEntityBoundingBox().contract(0.0d, 0.0d, 0.0d).offset(posX, posY, posZ) : mc.player.getEntityBoundingBox().contract(0.0d, 0.0d, 0.0d).offset(posX, posY, posZ);
            int y = (int) bb.minY;
            for (int x = MathHelper.floor(bb.minX); x < MathHelper.floor(bb.maxX) + 1; x++) {
                for (int z = MathHelper.floor(bb.minZ); z < MathHelper.floor(bb.maxZ) + 1; z++) {
                    block = mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
                }
            }
        }
        return block;
    }

    public static boolean isInLiquid() {
        if (mc.player != null) {
            if (mc.player.fallDistance >= 3.0f) {
                return false;
            }
            boolean inLiquid = false;
            final AxisAlignedBB bb = mc.player.getRidingEntity() != null ? mc.player.getRidingEntity().getEntityBoundingBox() : mc.player.getEntityBoundingBox();
            int y = (int) bb.minY;
            for (int x = MathHelper.floor(bb.minX); x < MathHelper.floor(bb.maxX) + 1; x++) {
                for (int z = MathHelper.floor(bb.minZ); z < MathHelper.floor(bb.maxZ) + 1; z++) {
                    final Block block = mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if (!(block instanceof BlockAir)) {
                        if (!(block instanceof BlockLiquid)) {
                            return false;
                        }
                        inLiquid = true;
                    }
                }
            }
            return inLiquid;
        }
        return false;
    }


    public static Vec3d getInterpolatedAmount(Entity entity, double ticks) {
        return getInterpolatedAmount(entity, ticks, ticks, ticks);
    }

    public static Vec3d getInterpolatedPos(Entity entity, float ticks) {
        return new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ).add(getInterpolatedAmount(entity, ticks));
    }

    public static Vec3d getInterpolatedAmount(Entity entity, double x, double y, double z) {
        return new Vec3d(
                (entity.posX - entity.lastTickPosX) * x,
                (entity.posY - entity.lastTickPosY) * y,
                (entity.posZ - entity.lastTickPosZ) * z
        );
    }

    public static float clamp(float val, float min, float max) {
        if (val <= min) {
            val = min;
        }
        if (val >= max) {
            val = max;
        }
        return val;
    }

    public static List<BlockPos> getSphere(BlockPos loc, float r, int h, boolean hollow, boolean sphere, int plus_y) {
        List<BlockPos> circleBlocks = new ArrayList<>();
        int cx = loc.getX();
        int cy = loc.getY();
        int cz = loc.getZ();
        for (int x = cx - (int) r; x <= cx + r; x++) {
            for (int z = cz - (int) r; z <= cz + r; z++) {
                for (int y = (sphere ? cy - (int) r : cy); y < (sphere ? cy + r : cy + h); y++) {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (dist < r * r && !(hollow && dist < (r - 1) * (r - 1))) {
                        BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleBlocks.add(l);
                    }
                }
            }
        }
        return circleBlocks;
    }

    public static List<BlockPos> getSquare(BlockPos pos1, BlockPos pos2) {
        List<BlockPos> squareBlocks = new ArrayList<>();
        int x1 = pos1.getX();
        int y1 = pos1.getY();
        int z1 = pos1.getZ();
        int x2 = pos2.getX();
        int y2 = pos2.getY();
        int z2 = pos2.getZ();
        for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x += 1) {
            for (int z = Math.min(z1, z2); z <= Math.max(z1, z2); z += 1) {
                for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y += 1) {
                    squareBlocks.add(new BlockPos(x, y, z));
                }
            }
        }
        return squareBlocks;
    }

    public static double[] calculateLookAt(double px, double py, double pz, Entity me) {
        double dirx = me.posX - px;
        double diry = me.posY - py;
        double dirz = me.posZ - pz;

        double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);

        dirx /= len;
        diry /= len;
        dirz /= len;

        double pitch = Math.asin(diry);
        double yaw = Math.atan2(dirz, dirx);

        pitch = pitch * 180.0d / Math.PI;
        yaw = yaw * 180.0d / Math.PI;

        yaw += 90f;

        return new double[]{yaw, pitch};
    }

    public static boolean basicChecksEntity(Entity pl) {
        return pl.getName().equals(mc.player.getName()) || Vulcan.getInstance().friends.isFriend(pl.getName()) || pl.isDead;
    }

    public static BlockPos getPosition(Entity pl) {
        return new BlockPos(Math.floor(pl.posX), Math.floor(pl.posY), Math.floor(pl.posZ));
    }

    public static List<BlockPos> getBlocksIn(Entity pl) {
        List<BlockPos> blocks = new ArrayList<>();
        AxisAlignedBB bb = pl.getEntityBoundingBox();
        for (double x = Math.floor(bb.minX); x < Math.ceil(bb.maxX); x++) {
            for (double y = Math.floor(bb.minY); y < Math.ceil(bb.maxY); y++) {
                for (double z = Math.floor(bb.minZ); z < Math.ceil(bb.maxZ); z++) {
                    blocks.add(new BlockPos(x, y, z));
                }
            }
        }
        return blocks;
    }

    public static boolean isPassive(Entity e)
    {
        if (e instanceof EntityWolf && ((EntityWolf) e).isAngry())
            return false;
        if (e instanceof EntityAnimal || e instanceof EntityAgeable || e instanceof EntityTameable
                || e instanceof EntityAmbientCreature || e instanceof EntitySquid)
            return true;
        if (e instanceof EntityIronGolem && ((EntityIronGolem) e).getRevengeTarget() == null)
            return true;
        return false;
    }

    public static void setTimer(float speed) {
        Minecraft.getMinecraft().timer.tickLength = 50.0f / speed;
    }

    public static void resetTimer() {
        Minecraft.getMinecraft().timer.tickLength = 50;
    }

    public static boolean isLiving(Entity e)
    {
        return e instanceof EntityLivingBase;
    }

    public static boolean isFakeLocalPlayer(Entity entity)
    {
        return entity != null && entity.getEntityId() == -100 && Minecraft.getMinecraft().player != entity;
    }

    public static Vec3d interpolateEntity(Entity entity, float time) {
        return new Vec3d(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * time, entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * time, entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * time);
    }

    public static boolean isMobAggressive(Entity entity)
    {
        if (entity instanceof EntityPigZombie)
        {
            // arms raised = aggressive, angry = either game or we have set the anger
            // cooldown
            if (((EntityPigZombie) entity).isArmsRaised() || ((EntityPigZombie) entity).isAngry())
            {
                return true;
            }
        }
        else if (entity instanceof EntityWolf)
        {
            return ((EntityWolf) entity).isAngry()
                    && !Minecraft.getMinecraft().player.equals(((EntityWolf) entity).getOwner());
        }
        else if (entity instanceof EntityEnderman)
        {
            return ((EntityEnderman) entity).isScreaming();
        }
        return isHostileMob(entity);
    }

    /**
     * If the mob by default wont attack the player, but will if the player attacks
     * it
     */
    public static boolean isNeutralMob(Entity entity)
    {
        return entity instanceof EntityPigZombie || entity instanceof EntityWolf || entity instanceof EntityEnderman;
    }

    /**
     * If the mob is friendly (not aggressive)
     */
    public static boolean isFriendlyMob(Entity entity)
    {
        return (entity.isCreatureType(EnumCreatureType.CREATURE, false) && !EntityUtil.isNeutralMob(entity))
                || (entity.isCreatureType(EnumCreatureType.AMBIENT, false)) || entity instanceof EntityVillager
                || entity instanceof EntityIronGolem || (isNeutralMob(entity) && !EntityUtil.isMobAggressive(entity));
    }

    /**
     * If the mob is hostile
     */
    public static boolean isHostileMob(Entity entity)
    {
        return (entity.isCreatureType(EnumCreatureType.MONSTER, false) && !EntityUtil.isNeutralMob(entity));
    }

    /**
     * Find the entities interpolated position
     */

    public static Vec3d getInterpolatedRenderPos(Entity entity, float ticks)
    {
        return getInterpolatedPos(entity, ticks).subtract(Minecraft.getMinecraft().getRenderManager().renderPosX,
                Minecraft.getMinecraft().getRenderManager().renderPosY,
                Minecraft.getMinecraft().getRenderManager().renderPosZ);
    }

    public static boolean isInWater(Entity entity)
    {
        if (entity == null)
            return false;

        double y = entity.posY + 0.01;

        for (int x = MathHelper.floor(entity.posX); x < MathHelper.ceil(entity.posX); x++)
            for (int z = MathHelper.floor(entity.posZ); z < MathHelper.ceil(entity.posZ); z++)
            {
                BlockPos pos = new BlockPos(x, (int) y, z);

                if (Minecraft.getMinecraft().world.getBlockState(pos).getBlock() instanceof BlockLiquid)
                    return true;
            }

        return false;
    }

    public static boolean isDrivenByPlayer(Entity entityIn)
    {
        return Minecraft.getMinecraft().player != null && entityIn != null
                && entityIn.equals(Minecraft.getMinecraft().player.getRidingEntity());
    }

    public static boolean isAboveWater(Entity entity)
    {
        return isAboveWater(entity, false);
    }

    public static boolean isAboveWater(Entity entity, boolean packet)
    {
        if (entity == null)
            return false;

        double y = entity.posY - (packet ? 0.03 : (EntityUtil.isPlayer(entity) ? 0.2 : 0.5)); // increasing this seems
                                                                                              // to flag more in NCP but
                                                                                              // needs to be increased
                                                                                              // so the player lands on
                                                                                              // solid water

        for (int x = MathHelper.floor(entity.posX); x < MathHelper.ceil(entity.posX); x++)
            for (int z = MathHelper.floor(entity.posZ); z < MathHelper.ceil(entity.posZ); z++)
            {
                BlockPos pos = new BlockPos(x, MathHelper.floor(y), z);

                if (Minecraft.getMinecraft().world.getBlockState(pos).getBlock() instanceof BlockLiquid)
                    return true;
            }

        return false;
    }

    public static double[] calculateLookAt(double px, double py, double pz, EntityPlayer me)
    {
        double dirx = me.posX - px;
        double diry = me.posY - py;
        double dirz = me.posZ - pz;

        double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);

        dirx /= len;
        diry /= len;
        dirz /= len;

        double pitch = Math.asin(diry);
        double yaw = Math.atan2(dirz, dirx);

        // to degree
        pitch = pitch * 180.0d / Math.PI;
        yaw = yaw * 180.0d / Math.PI;

        yaw += 90f;

        return new double[]
        { yaw, pitch };
    }

    public static boolean isPlayer(Entity entity)
    {
        return entity instanceof EntityPlayer;
    }

    public static double getRelativeX(float yaw)
    {
        return (double) (MathHelper.sin(-yaw * 0.017453292F));
    }

    public static double getRelativeZ(float yaw)
    {
        return (double) (MathHelper.cos(yaw * 0.017453292F));
    }
    
    public static int GetPlayerMS(EntityPlayer p_Player)
    {
        if (p_Player.getUniqueID() == null)
            return 0;
        
        final NetworkPlayerInfo playerInfo = Minecraft.getMinecraft().getConnection().getPlayerInfo(p_Player.getUniqueID());

        if (playerInfo == null)
            return 0;
        
        return playerInfo.getResponseTime();
    }

    public static Vec3d CalculateExpectedPosition(EntityPlayer p_Player)
    {
        Vec3d l_Position = new Vec3d(p_Player.posX, p_Player.posY, p_Player.posZ);
        
        if (p_Player.lastTickPosX != p_Player.posX && p_Player.lastTickPosY != p_Player.posY && p_Player.lastTickPosZ != p_Player.posZ)
            return l_Position;
        
        int l_PlayerMS = GetPlayerMS(p_Player);

        final double deltaX = p_Player.posX - p_Player.prevPosX;
        final double deltaZ = p_Player.posZ - p_Player.prevPosZ;
        final float tickRate = (Minecraft.getMinecraft().timer.tickLength / 1000.0f);
        
        float l_Distance = MathHelper.sqrt(deltaX * deltaX + deltaZ * deltaZ);
        
        double l_Facing = MathUtil.calculateAngle(p_Player.posX, p_Player.posZ, p_Player.lastTickPosX, p_Player.lastTickPosZ) / 45;
        
        return new Vec3d(
                p_Player.posX + (Math.cos(l_Facing) * l_Distance),
                p_Player.posY,
                p_Player.posZ + (Math.sin(l_Facing) * l_Distance)
                );
    }
    
    public static double GetDistance(double p_X, double p_Y, double p_Z, double x, double y, double z)
    {
        double d0 = p_X - x;
        double d1 = p_Y - y;
        double d2 = p_Z - z;
        return (double)MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
    }
    
    public static double GetDistanceOfEntityToBlock(Entity p_Entity, BlockPos p_Pos)
    {
        return GetDistance(p_Entity.posX, p_Entity.posY, p_Entity.posZ, p_Pos.getX(), p_Pos.getY(), p_Pos.getZ());
    }

    public static boolean IsVehicle(Entity entity)
    {
        return entity instanceof EntityBoat || entity instanceof EntityMinecart;
    }

    /*public static String getNameFromUUID(final String uuid) {
        try {
            Aurora.log.info("Attempting to get name from UUID: " + uuid);
            final String jsonUrl = IOUtils.toString(new URL("https://api.mojang.com/user/profiles/" + uuid.replace("-", "") + "/names"));
            final JsonParser parser = new JsonParser();
            return parser.parse(jsonUrl).getAsJsonArray().get(parser.parse(jsonUrl).getAsJsonArray().size() - 1).getAsJsonObject().get("name").toString();
        }
        catch (IOException ex) {
            ZenithMod.log.error((Object)ex.getStackTrace());
            ZenithMod.log.error("Failed to get username from UUID due to an exception. Maybe your internet is being the big gay? Somehow?");
            return null;
        }
    }*/

    public static BlockPos GetPositionVectorBlockPos(Entity entity, @Nullable BlockPos toAdd)
    {
        final Vec3d v = entity.getPositionVector();
        
        if (toAdd == null)
            return new BlockPos(v.x, v.y, v.z);
        
        return new BlockPos(v.x, v.y, v.z).add(toAdd);
    }
}
