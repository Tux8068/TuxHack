package me.tux.tuxhack.module.modules.combat;

import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.setting.Setting;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.math.BlockPos;
/**
 * @author TuxISCool
 * @edited Kami :^) I added a thing
 * @since 09/05/21
 */
public class BowSpam extends Module {
    public BowSpam() {
        super("BowSpam", "Spams your bow to do maximum damage!", Category.COMBAT);
    }

    Setting.Integer DELAY;
    Setting.Boolean PITCHSHIFT;
    Setting.Integer PITCH;

    @Override
    public boolean setup() {
        DELAY = registerInteger("Delay", "Delay", 3, 3, 20);
        PITCHSHIFT = registerBoolean("PitchShift", "PitchShift", true);
        PITCH = registerInteger("Pitch", "Pitch", 60, -180, 180);
        return false;
    }

    public int onUpdate() {
        if (mc.player == null) {
            return 0;
        }
        if (mc.player.getHeldItemMainhand().getItem() instanceof ItemBow
                && mc.player.isHandActive()
                && mc.player.getItemInUseMaxCount() >= DELAY.getValue()) {
            mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, mc.player.getHorizontalFacing()));
            mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(mc.player.getActiveHand()));
            mc.player.stopActiveHand();
            if(PITCHSHIFT.getValue())
                mc.player.rotationPitch = (PITCH.getValue());
        }
        return 0;
    }
}