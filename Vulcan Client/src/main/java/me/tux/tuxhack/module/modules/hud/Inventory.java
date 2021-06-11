package me.tux.tuxhack.module.modules.hud;

import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.setting.Setting;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Inventory extends Module {

    public Inventory() {
        super("Inventory", "Renders inventory GUI and items on screen.", Category.Hud);
    }

    Setting.Integer r;
    Setting.Integer g;
    Setting.Integer b;
    Setting.Integer x;
    Setting.Integer y;
    Setting.Integer alpha;
    Setting.Double lineGirth;
    Setting.Integer w;
    Setting.Integer h;


    /**
     * @author _KA1
     * 08/04/21
     * @return
     */

    public boolean setup() {
        r = registerInteger("Red", "redInv", 255, 0, 255);
        g = registerInteger("Green", "greenInv", 255, 0, 255);
        b = registerInteger("Blue", "blueInv", 255, 0, 255);
        x = registerInteger("X", "xInv", 0, 0, 1280);
        y = registerInteger("Y", "yInv", 10, 0, 960);
        alpha = registerInteger("Alpha", "outlineAlphaInv", 90, 0, 255);
        lineGirth = registerDouble("Outline Width", "outlineWidthInv", 0.8, 0, 1.0);
        w = registerInteger("w", "w", 50, 0, 1000 );
        h = registerInteger("h", "h", 50, 0, 1000);
        return false;
    }


    public void onRender() {

        if (mc.player == null || mc.world == null) {
            return;
        }
        GlStateManager.pushMatrix();
        RenderHelper.enableGUIStandardItemLighting();

        GL11.glLineWidth((float) lineGirth.getValue());
        Gui.drawRect(x.getValue(), y.getValue(), x.getValue() + w.getValue()  , y.getValue() + h.getValue() , new Color(r.getValue(), g.getValue(), b.getValue(), alpha.getValue()).getRGB());



        for (int i = 0; i < 27; i++) {
            ItemStack item_stack = mc.player.inventory.mainInventory.get(i + 9);

            int item_position_x = x.getValue() + (i % 9) * 16;
            int item_position_y = y.getValue() + (i / 9) * 16;

            mc.getRenderItem().renderItemAndEffectIntoGUI(item_stack, item_position_x, item_position_y);
            mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRenderer, item_stack, item_position_x, item_position_y, null);

        }
        mc.getRenderItem().zLevel = - 5.0f;

        RenderHelper.disableStandardItemLighting();

        GlStateManager.popMatrix();

    }
}
