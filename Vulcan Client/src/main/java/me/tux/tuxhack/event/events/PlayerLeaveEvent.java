package me.tux.tuxhack.event.events;

import me.tux.tuxhack.event.Event;

public class PlayerLeaveEvent extends Event {

    private final String name;

    public PlayerLeaveEvent(String n){
        super();
        name = n;
    }

    public String getName(){
        return name;
    }
}
