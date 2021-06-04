package me.tux.tuxhack.module.modules.combat;

import me.tux.tuxhack.TuxHack;
import me.tux.tuxhack.event.MinecraftEvent;
import me.tux.tuxhack.event.events.OnUpdateWalkingPlayerEvent;
import me.tux.tuxhack.module.Module;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class BowAim extends Module {

    public BowAim() { super("BowAim", "makes your bow have aimbot",Category.Combat); }
    
    private final List<EntityLivingBase> validTargets = new CopyOnWriteArrayList<>();

    private EntityLivingBase target = null;

    private final Listener<OnUpdateWalkingPlayerEvent> onUpdateWalkingPlayerEventListener = new Listener<>(event -> {
        if (event.getEra() == MinecraftEvent.Era.PRE){
            doBowAim();
        }
        else if (event.getEra() == MinecraftEvent.Era.POST){

        }

    });

    public int doBowAim() {
        if (validTargets.isEmpty())
        {
            for (Object object : mc.world.loadedEntityList)
            {
                Entity entity = (Entity) object;

                if (entity instanceof EntityLivingBase)
                {
                    EntityLivingBase entityLivingBase = (EntityLivingBase) entity;

                    if (isValidEntity(entityLivingBase) && validTargets.size() < 5)
                    {
                        validTargets.add(entityLivingBase);
                    }
                }
            }
        }

        validTargets.forEach(entityLivingBase ->
        {
            if (!isValidEntity(entityLivingBase))
            {
                validTargets.remove(entityLivingBase);
            }
        });
        target = getClosestEntity();

        if (isValidEntity(target))
        {

            float[] rotations = getRotations(target);

            if (mc.player.getHeldItemMainhand() == null)
            {
                return 0;
            }

            if (!(mc.player.getHeldItemMainhand().getItem() instanceof ItemBow))
            {
                return 0;
            }

            if (mc.player.isHandActive()) {
                mc.player.rotationYaw = wrapAngleTo180(rotations[0]);
                mc.player.rotationPitch = wrapAngleTo180(rotations[1] + (mc.player.getDistance(target) * -0.15F));
            }
            }
        else
        {
            validTargets.remove(target);
            target = null;
        }

        return 0;
    }

    public static float[] getRotations(Entity entity)
    {
        double positionX = entity.posX - mc.player.posX;
        double positionZ = entity.posZ - mc.player.posZ;
        double positionY = entity.posY + entity.getEyeHeight() / 1.3D - (mc.player.posY + mc.player.getEyeHeight());
        double positions = MathHelper.sqrt(positionX * positionX + positionZ * positionZ);
        float yaw = (float)(Math.atan2(positionZ, positionX) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float) - (Math.atan2(positionY, positions) * 180.0D / Math.PI);
        return new float[] {yaw, pitch};
    }

    public static float wrapAngleTo180(float angle)
    {
        angle %= 360f;

        if (angle >= 180f)
        {
            angle -= 360f;
        }

        if (angle < -180f)
        {
            angle += 360f;
        }

        return angle;
    }


    @Override
    protected int onDisable() {
        target = null;
        validTargets.clear();

        return 0;
    }

    public boolean canEntityBeSeen(Entity entityIn) {
        return mc.world.rayTraceBlocks(new Vec3d(mc.player.posX, mc.player.posY + (double)mc.player.getEyeHeight(), mc.player.posZ), new Vec3d(entityIn.posX, entityIn.posY + (double)entityIn.getEyeHeight(), entityIn.posZ), false, false, false) == null;
    }
    
    @Override
    protected void onEnable() {
        super.onEnable();
    }

    private boolean isValidEntity(EntityLivingBase entity)
    {
        if (entity == null || entity.isDead || !entity.isEntityAlive() || mc.player.getDistance(entity) > 50 || entity.ticksExisted < 20 || !canEntityBeSeen(entity))
        {
            return false;
        }

        if (entity instanceof IMob)
        {
            return false;
        }

        if (entity instanceof IAnimals)
        {
            return false;
        }

        if (entity instanceof EntityPlayer)
        {

            EntityPlayer entityPlayer = (EntityPlayer) entity;

            if (entityPlayer.equals(mc.player) || entityPlayer.capabilities.isCreativeMode)
            {
                return false;
            }

            if (entityPlayer.isInvisible())
            {
                return false;
            }

            return !TuxHack.getInstance().friends.isFriend(entity.getName());
        }

        return true;
    }

    public static boolean isAiming(float yaw, float pitch, int fov)
    {
        yaw = wrapAngleTo180(yaw);
        pitch = wrapAngleTo180(pitch);
        float curYaw = wrapAngleTo180(mc.player.rotationYaw);
        float curPitch = wrapAngleTo180(mc.player.rotationPitch);
        float yawDiff = Math.abs(yaw - curYaw);
        float pitchDiff = Math.abs(pitch - curPitch);
        return yawDiff + pitchDiff <= fov;
    }
    
    private EntityLivingBase getClosestEntity()
    {
        double range = 50;
        EntityLivingBase closest = null;

        for (EntityLivingBase entity : validTargets)
        {
            float distance = mc.player.getDistance(entity);

            if (distance < range)
            {
                if (isValidEntity(entity) && isAiming(getRotations(entity)[0], getRotations(entity)[1], 360))
                {
                    closest = entity;
                    range = distance;
                }
            }
        }

        return closest;
    }

}