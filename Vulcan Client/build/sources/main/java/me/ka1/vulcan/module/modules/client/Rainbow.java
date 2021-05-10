package me.ka1.vulcan.module.modules.client;

import me.ka1.vulcan.module.Module;
import me.ka1.vulcan.setting.Setting;

public class Rainbow extends Module {
    public Rainbow() {
        super("Rainbow", Category.Client);
    }

    Setting.Integer speed;

    @Override
    public void setup() {
        speed = registerInteger("Speed", "speed", 2, 1, 12);
        super.setup();
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
