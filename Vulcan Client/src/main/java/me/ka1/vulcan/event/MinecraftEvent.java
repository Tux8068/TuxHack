package me.ka1.vulcan.event;

import me.zero.alpine.fork.event.type.Cancellable;
import net.minecraft.client.Minecraft;

public class MinecraftEvent extends Cancellable
{
    private boolean cancelled = false;
    private Era era = Era.PRE;
    private final float partialTicks;

    public MinecraftEvent()
    {
        partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();
    }
    
    public MinecraftEvent(Era p_Era)
    {
        partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();
        era = p_Era;
    }

    public void setEra(MinecraftEvent.Era p_Era)
    {
        this.setCancelled(false);
        era = p_Era;
    }

    public Era getEra()
    {
        return era;
    }

    public float getPartialTicks()
    {
        return partialTicks;
    }

    public void setCancelled(boolean way) {
        this.cancelled = way;
    }
    
    public enum Era
    {
        PRE,
        PERI,
        POST
    }

}
