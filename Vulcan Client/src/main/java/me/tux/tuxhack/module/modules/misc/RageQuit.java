package me.tux.tuxhack.module.modules.misc;

import me.tux.tuxhack.module.Module;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RageQuit extends Module {

    public static final Logger log = LogManager.getLogger();

    public RageQuit() {
        super("Rage Quit", "Currently coping??", Category.MISC);
    }

    public int onUpdate() {
        if (this.isEnabled())
            this.disable();
        mc.player.sendChatMessage("fuck this im out!");
            log.info("Stay mad kid");
            System.exit(0);
        return 0;
    }
}
