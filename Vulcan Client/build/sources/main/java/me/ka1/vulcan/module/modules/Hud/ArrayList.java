package me.ka1.vulcan.module.modules.Hud;

import com.google.common.collect.Lists;
import me.ka1.vulcan.module.Module;
import me.ka1.vulcan.module.ModuleManager;
import me.ka1.vulcan.setting.Setting;

import java.awt.*;
import java.util.Comparator;
import java.util.List;

import static me.ka1.vulcan.Vulcan.fontRenderer;

public class ArrayList extends Module {
    public ArrayList() {
        super("ArrayList", "Displays a list of enabled modules", Category.Hud);
    }

    /**
     * @author _KA1
     * 1/4/21
     */
    public List<Module> enabledModules = new java.util.ArrayList();
    private int moduleCount;
    Setting.Boolean dock;
    Setting.Integer x;
    Setting.Integer y;

    public void setup() {
        dock = registerBoolean("Dock", "dock", true);
        x = registerInteger("X", "arrayListx", 1, 1, 1280);
        y = registerInteger("Y", "arrayListy", 1, 1, 960);
    }

    public void onRender() {
        if (mc.player == null || mc.world == null)
            return;

        enabledModules.clear();
        for (Module module : ModuleManager.getModules()) {
            if (ModuleManager.isModuleEnabled(module) && module.isDrawn() && module.getCategory() != Category.Client && module.getCategory() != Category.Hud)
                enabledModules.add(module);
        }

        enabledModules.sort(Comparator.comparing(module -> fontRenderer.getStringWidth(module.getName() + module.getHudInfo())));
        enabledModules = Lists.reverse(enabledModules);
        moduleCount = 0;

        for (Module module : enabledModules) {

            if (x.getValue() <= (1280 / 2)) { //***Left side***
                if (y.getValue() <= (500 / 2)) { // top left of the screen
                    fontRenderer.drawStringWithShadow(module.getName() + " " + module.getHudInfo(), x.getValue(), y.getValue() + (moduleCount * 10), new Color(255, 255, 255).getRGB());
                } else {  // Top left of the screen
                    fontRenderer.drawStringWithShadow(module.getName() + " " + module.getHudInfo(), x.getValue(), y.getValue() - (moduleCount * 10), new Color(255, 255, 255).getRGB());
                }
            } else if (x.getValue() > (1280 / 2)) { // ***Right side***
                if (y.getValue() < (500 / 2)) { //Top right of the screen
                    fontRenderer.drawStringWithShadow(module.getName() + " " + module.getHudInfo(), x.getValue() - fontRenderer.getStringWidth(module.getName() + module.getHudInfo()), y.getValue() + (moduleCount * 10), new Color(255, 255, 255).getRGB());
                } else //Bottom right of the screen
                fontRenderer.drawStringWithShadow(module.getName() + " " + module.getHudInfo(), x.getValue() - fontRenderer.getStringWidth(module.getName() + module.getHudInfo()), y.getValue() - (moduleCount * 10), new Color(255, 255, 255).getRGB());
            }
            moduleCount++;
        }
    }
}