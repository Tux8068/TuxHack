package me.ka1.vulcan.event.events;

import me.ka1.vulcan.event.Event;

public class PlayerJoinEvent extends Event {
    private final String name;

    public PlayerJoinEvent(String n){
        super();
        name = n;
    }

    public String getName(){
        return name;
    }
}
