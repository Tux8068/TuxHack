package me.tux.tuxhack.module.modules.render;

import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.setting.Setting;
import net.minecraft.client.renderer.ItemRenderer;

public class LowHands extends Module {
    public LowHands() {
        super("LowHand", Category.Render);
    }
    ItemRenderer itemRenderer = mc.entityRenderer.itemRenderer;

    Setting.Double off;
    public boolean setup(){
        off = registerDouble("Off Height", "LowOffhandHeight", 0.5, 0, 2);
        return false;
    }

    public int onUpdate(){
        mc.entityRenderer.itemRenderer.equippedProgressOffHand = (float)off.getValue();
        return 0;
    }
}

