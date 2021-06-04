package me.tux.tuxhack.module.modules.render;

import me.tux.tuxhack.TuxHack;
import me.tux.tuxhack.event.events.EventBowEnd;
import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.setting.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumHand;

public class BowAnimations extends Module {
    public BowAnimations() {
        super("BowAnimations", "BowAnimations", Category.Render);
    }
    public boolean setup(){
        return false;
    }
    @EventHandler
    private final Listener <EventBowEnd> bowlistener = new Listener<>(eventBowEnd -> {
        mc.player.swingArm(EnumHand.MAIN_HAND);

    });
    @Override
    public void onEnable() {
        TuxHack.EVENT_BUS.subscribe(bowlistener);

    }
    @Override
    public int onDisable() {
        TuxHack.EVENT_BUS.unsubscribe(bowlistener);
        return 0;
    }
}