package me.tux.tuxhack.module.modules.render;

import me.tux.tuxhack.TuxHack;
import me.tux.tuxhack.command.Command;
import me.tux.tuxhack.event.events.PerspectiveEvent;
import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.setting.Setting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;

public class Aspect extends Module {
    Setting.Double ratio;

    public Aspect() {
        super("Aspect", "travis ontop", Category.RENDER);
    }

    public boolean setup() {
        ratio = registerDouble("Aspect", "aspectRatio", 1.4, 0.0 ,3.0);
        return false;
    }

    @EventHandler
    private Listener<PerspectiveEvent> eventListener = new Listener<>(event -> {
        Command.sendClientMessage("event");
        event.setAspect((float) ratio.getValue());
    });

    public void onEnable(){
        TuxHack.EVENT_BUS.subscribe(this);
    }

    public int onDisable(){
        TuxHack.EVENT_BUS.unsubscribe(this);
        return 0;
    }

}
