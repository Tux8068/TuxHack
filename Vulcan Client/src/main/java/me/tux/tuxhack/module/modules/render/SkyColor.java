package me.tux.tuxhack.module.modules.render;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.MinecraftForge;
import me.tux.tuxhack.event.events.UpdateEvent;
import java.awt.Color;
import me.tux.tuxhack.setting.Setting;
import me.tux.tuxhack.module.Module;

public class SkyColor extends Module
{
    Setting.Integer r;
    Setting.Integer g;
    Setting.Integer b;
    Setting.Boolean rainbow;

    public SkyColor() {
        super("SkyColor", "changes the sky", Category.Render);
        this.r = this.registerInteger("Red", "R", 255, 0, 255);
        this.g = this.registerInteger("Green", "G", 255, 0, 255);
        this.b = this.registerInteger("Blue", "B", 255, 0, 255);
        this.rainbow = this.registerBoolean("rainbow", "rainbow", false);
    }

    public int getSkyColorByTemp(final float par1) {
        return Color.red.getRGB();
    }

    public void onUpdate(final UpdateEvent event) {
        if (this.isEnabled()) {
            MinecraftForge.EVENT_BUS.register((Object)this);
        }
        else {
            MinecraftForge.EVENT_BUS.unregister((Object)this);
        }
    }

    @SubscribeEvent
    public void fogColors(final EntityViewRenderEvent.FogColors event) {
        event.setRed(this.r.getValue() / 255.0f);
        event.setGreen(this.g.getValue() / 255.0f);
        event.setBlue(this.b.getValue() / 255.0f);
        if (rainbow.getValue() == true);
        this.Rainbow(0);
    }

    @SubscribeEvent
    public void fogDensity(final EntityViewRenderEvent.FogDensity event) {
        event.setDensity(0.0f);
        event.setCanceled(true);
    }

    public static Color Rainbow(final int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360.0;
        return Color.getHSBColor((float)(rainbowState / 360.0), 0.8f, 0.7f);
    }

    public void onEnable() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    public int onDisable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
        return 0;
    }
}
