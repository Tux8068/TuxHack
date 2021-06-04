package me.tux.tuxhack.event.events;

import me.tux.tuxhack.event.EventStageable;


public class PerspectiveEvent extends EventStageable {
    private float aspect;

    public PerspectiveEvent(float aspect) {
        this.aspect = aspect;
    }

    public float getAspect() {
        return aspect;
    }

    public void setAspect(float aspect) {
        this.aspect = aspect;
    }
}
