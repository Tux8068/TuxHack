package me.tux.tuxhack.module.modules.combat;

import me.tux.tuxhack.module.Module;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;

public class MultiTask extends Module {
    public MultiTask() {
        super("MultiTask", "Allows you to do 2 actions", Category.COMBAT);
    }

    @Override
    public int onUpdate() {
        if (mc.gameSettings.keyBindUseItem.isKeyDown() && mc.player.getActiveHand() == EnumHand.MAIN_HAND) {
            RayTraceResult r = mc.player.rayTrace(6.0D, mc.getRenderPartialTicks());
            if (!(mc.player.getHeldItemOffhand().getItem() instanceof ItemBlock) && mc.player.getHeldItemOffhand().getItem() != Items.END_CRYSTAL) {
                if (mc.player.getHeldItemOffhand().getItem() instanceof ItemFood && mc.gameSettings.keyBindUseItem.isKeyDown() && mc.gameSettings.keyBindAttack.isKeyDown()) {
                    mc.player.setActiveHand(EnumHand.OFF_HAND);
                    mc.player.swingArm(EnumHand.MAIN_HAND);
                }
            } else {
                assert r != null;
                mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(r.getBlockPos(), r.sideHit, EnumHand.OFF_HAND, 0.0F, 0.0F, 0.0F));
            }
        }

        return 0;
    }
}

