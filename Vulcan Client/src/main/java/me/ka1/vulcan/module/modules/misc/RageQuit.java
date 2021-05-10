package me.ka1.vulcan.module.modules.misc;

import me.ka1.vulcan.module.Module;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RageQuit extends Module {

    public static final Logger log = LogManager.getLogger();

    public RageQuit() {
        super("Rage Quit", "Currently coping??", Category.Misc);
    }

    public int onUpdate() {
        if (this.isEnabled())
            this.disable();
            log.info("Stay mad kid");
            System.exit(0);
        return 0;
    }
}
