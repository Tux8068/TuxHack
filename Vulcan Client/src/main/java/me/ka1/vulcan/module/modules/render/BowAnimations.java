package me.ka1.vulcan.module.modules.render;

import me.ka1.vulcan.Vulcan;
import me.ka1.vulcan.event.events.EventBowEnd;
import me.ka1.vulcan.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.util.EnumHand;

public class BowAnimations extends Module {
    public BowAnimations() {
        super("BowAnimations", "BowAnimations", Category.Render);
    }
    @EventHandler
    private final Listener <EventBowEnd> bowlistener = new Listener<>(eventBowEnd -> {
        mc.player.swingArm(EnumHand.MAIN_HAND);
        mc.player.sendChatMessage("hi");
    });
    @Override
    public void onEnable() {
        Vulcan.EVENT_BUS.subscribe(bowlistener);
    }
    @Override
    public void onDisable() {
        Vulcan.EVENT_BUS.unsubscribe(bowlistener); }
}