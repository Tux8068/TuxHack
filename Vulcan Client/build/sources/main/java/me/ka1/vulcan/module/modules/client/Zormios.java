package me.ka1.vulcan.module.modules.client;

import me.ka1.vulcan.module.Module;
import me.ka1.vulcan.setting.Setting;

public class Zormios extends Module {
    public Zormios() {
        super("Zormios", Category.Client);
    }

    Setting.Integer speed;

    public static int getXormiosOffset() {
        return 0;
    }

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
}
