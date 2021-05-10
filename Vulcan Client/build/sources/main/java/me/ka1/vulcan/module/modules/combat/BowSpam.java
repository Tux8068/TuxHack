package me.ka1.vulcan.module.modules.combat;

import me.ka1.vulcan.module.Module;
import me.ka1.vulcan.setting.Setting;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.math.BlockPos;
/**
 * @author TuxISCool
 * @since 09/05/21
 */
public class BowSpam extends Module {
    public BowSpam() {
        super("BowSpam", "Spams your bow to do maximum damage!", Category.Combat);
    }

    Setting.Integer DELAY;
    Setting.Integer PITCH;

    @Override
    public void setup() {
        DELAY = registerInteger("Delay", "Delay", 3, 3, 20);
        PITCH = registerInteger("Pitch", "Pitch", 60, -180, 180);
    }

    public int onUpdate() {
        if (mc.player == null) {
            return 0;
        }
        if (mc.player.getHeldItemMainhand().getItem() instanceof ItemBow && mc.player.isHandActive() && mc.player.getItemInUseMaxCount() >= DELAY.getValue()) {
            mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, mc.player.getHorizontalFacing()));
            mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(mc.player.getActiveHand()));
            mc.player.stopActiveHand();
            mc.player.rotationPitch = (PITCH.getValue());
        }
        return 0;
    }
}