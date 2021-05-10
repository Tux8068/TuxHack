package me.ka1.vulcan.module.modules.combat;

import me.ka1.vulcan.module.Module;
import me.ka1.vulcan.util.entities.EntityUtil;
import me.ka1.vulcan.util.maths.MathUtil;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.util.math.Vec3d;


/**
 * @author Madmegsox1
 * @since 05/05/2021
 */

public class BowAim extends Module {

    public BowAim() { super("BowAim", "makes your bow have aimbot",Category.Combat); }
    @Override
    public int onUpdate() {
        if (mc.player.getHeldItemMainhand().getItem() instanceof ItemBow && mc.player.isHandActive() && mc.player.getItemInUseMaxCount() >= 3) {
            EntityPlayer player = null;
            float tickDis = 100f;
            for (EntityPlayer p : mc.world.playerEntities) {
                float dis = p.getDistance(mc.player);
                if (dis < tickDis) {
                    tickDis = dis;
                    player = p;
                }
            }
            if (player != null) {
                Vec3d pos = EntityUtil.getInterpolatedPos(player, mc.getRenderPartialTicks());
                float[] angels = MathUtil.calcAngle(EntityUtil.getInterpolatedPos(mc.player, mc.getRenderPartialTicks()), pos);
                //angels[1] -=  calcPitch(tickDis);
                mc.player.rotationYaw = angels[0];
                mc.player.rotationPitch = angels[1];
            }
        }
        return 0;
    }


    private float calcPitch(float dis) {
        float a = 129.84f;
        float h = 1.32f;
        return a;
    }
}