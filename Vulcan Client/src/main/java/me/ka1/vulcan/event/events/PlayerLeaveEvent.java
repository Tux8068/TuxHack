package me.ka1.vulcan.event.events;

import me.ka1.vulcan.event.Event;

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
