package me.ka1.vulcan.module.modules.render;

import me.ka1.vulcan.module.Module;
import me.ka1.vulcan.setting.Setting;
import net.minecraft.client.renderer.ItemRenderer;

public class LowHands extends Module {
    public LowHands() {
        super("LowHand", Category.Render);
    }
    ItemRenderer itemRenderer = mc.entityRenderer.itemRenderer;

    Setting.Double off;
    public void setup(){
        off = registerDouble("Off Height", "LowOffhandHeight", 0.5, 0, 2);
    }

    public int onUpdate(){
        mc.entityRenderer.itemRenderer.equippedProgressOffHand = (float)off.getValue();
        return 0;
    }
}

