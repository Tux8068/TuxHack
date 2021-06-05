package me.tux.tuxhack.event.events;

import me.tux.tuxhack.event.Event;
import net.minecraft.entity.Entity;

public class TotemPopEvent extends Event
{
    private final Entity entity;

    public TotemPopEvent(final Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return this.entity;
    }
}

