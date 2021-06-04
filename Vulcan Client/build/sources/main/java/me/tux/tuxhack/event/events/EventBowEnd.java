package me.tux.tuxhack.event.events;

import me.tux.tuxhack.event.Event;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EventBowEnd extends Event {
    private ItemStack item;
    private World world;
    private EntityLivingBase entitylivingbase;
    public EventBowEnd(ItemStack itemStack, World world, EntityLivingBase livingbase){
        this.item = itemStack;
        this.world = world;
        this.entitylivingbase = livingbase;
    }
    public EntityLivingBase getEntitylivingbase(){return this.entitylivingbase;}
}