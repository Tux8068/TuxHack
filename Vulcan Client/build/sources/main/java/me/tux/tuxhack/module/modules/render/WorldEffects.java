package me.tux.tuxhack.module.modules.render;


import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.setting.Setting;
import net.minecraft.init.MobEffects;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WorldEffects extends Module {
    public WorldEffects() {
        super("WorldEffects", "Removes world related rendering", Category.Render);
    }

    Setting.Boolean Negative;
    Setting.Boolean Weather;
    Setting.Boolean Blocks;
    Setting.Boolean Liquid;
    Setting.Boolean Fire;

    @Override
    public boolean setup() {
        Negative = registerBoolean("NegativeEffects", "Negative", false);
        Weather = registerBoolean("Weather", "Weather", false);
        Blocks = registerBoolean("Blocks", "blox", false);
        Liquid = registerBoolean("Liquid", "Liquid", false);
        Fire = registerBoolean("Fire", "Fire", false);
        return false;
    }

    @Override
    public int onUpdate() {
        if (Negative.getValue() == true) ;
        mc.player.removePotionEffect(MobEffects.BLINDNESS);
        mc.player.removePotionEffect(MobEffects.NAUSEA);

        if (Weather.getValue() == true) ;
        mc.world.setRainStrength(0);
        mc.world.setThunderStrength(0);
        return 0;
    }

    @SubscribeEvent
    public void renderBlockEvent(RenderBlockOverlayEvent event) {
        if (Blocks.getValue() && event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.BLOCK) {
            event.setCanceled(true);
            if (Liquid.getValue() && event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.WATER)
                event.setCanceled(true);
            if (Fire.getValue() && event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.FIRE)
                event.setCanceled(true);
        }
    }
    public void onLogin() {
        if (this.isEnabled()) {
            this.disable();
            this.enable();
        }
    }

}
