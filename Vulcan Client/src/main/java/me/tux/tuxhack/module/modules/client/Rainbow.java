package me.tux.tuxhack.module.modules.client;

import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.setting.Setting;

public class Rainbow extends Module {
    public Rainbow() {
        super("Rainbow", Category.Client);
    }

    Setting.Integer speed;

    @Override
    public boolean setup() {
        speed = registerInteger("Speed", "speed", 2, 1, 12);
        super.setup();
        return false;
    }

    static int RainbowOffset = 0;

    @Override
    public void onRender() {
        RainbowOffset += speed.getValue();

        super.onRender();
    }

    public static int getRainbowOffset() {
        return RainbowOffset;
    }
}
