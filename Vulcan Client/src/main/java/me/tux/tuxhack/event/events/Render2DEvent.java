package me.tux.tuxhack.event.events;

import me.tux.tuxhack.event.MinecraftEvent;
import net.minecraft.client.gui.ScaledResolution;

public class Render2DEvent
        extends MinecraftEvent {
    public ScaledResolution scaledResolution;
    public float partialTicks;

    public Render2DEvent(float partialTicks, ScaledResolution scaledResolution) {
        this.partialTicks = partialTicks;
        this.scaledResolution = scaledResolution;
    }

    public void setPartialTicks(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public void setScaledResolution(ScaledResolution scaledResolution) {
        this.scaledResolution = scaledResolution;
    }

    public double getScreenWidth() {
        return this.scaledResolution.getScaledWidth_double();
    }

    public double getScreenHeight() {
        return this.scaledResolution.getScaledHeight_double();
    }
}
