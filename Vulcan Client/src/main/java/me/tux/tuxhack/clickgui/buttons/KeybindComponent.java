package me.tux.tuxhack.clickgui.buttons;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.tux.tuxhack.clickgui.Buttons;
import me.tux.tuxhack.clickgui.Component;
import me.tux.tuxhack.module.ModuleManager;
import me.tux.tuxhack.module.modules.client.ClickGuiModule;
import me.tux.tuxhack.util.font.FontUtils;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class KeybindComponent extends Component {

    private boolean hovered;
    private boolean binding;
    private final Buttons parent;
    private int offset;
    private int x;
    private int y;

    public KeybindComponent(final Buttons button, final int offset) {
        this.parent = button;
        this.x = button.parent.getX() + button.parent.getWidth();
        this.y = button.parent.getY() + button.offset;
        this.offset = offset;
    }

    @Override
    public void setOff(final int newOff) {
        this.offset = newOff;
    }

    @Override
    public void renderComponent() {
        Gui.drawRect(this.parent.parent.getX(), this.parent.parent.getY() + this.offset + 1, this.parent.parent.getX() + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset + 15, this.hovered ? new Color(0, 0, 0, ClickGuiModule.opacity.getValue()-50).darker().darker().getRGB() : new Color(0, 0, 0, ClickGuiModule.opacity.getValue()-50).darker().darker().getRGB());
        Gui.drawRect(this.parent.parent.getX(), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset + 1, new Color(0, 0, 0, ClickGuiModule.opacity.getValue()-50).getRGB());
        Gui.drawRect(this.parent.parent.getX(), this.parent.parent.getY() + this.offset + 15, this.parent.parent.getX() + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset + 16, new Color(0, 0, 0, ClickGuiModule.opacity.getValue()-50).darker().darker().getRGB());
        FontUtils.drawKeyStringWithShadow(ModuleManager.isModuleEnabled("CustomFont"), this.binding ? "Key..." : ("Key: " + ChatFormatting.GRAY + Keyboard.getKeyName(this.parent.mod.getBind())), (this.parent.parent.getX() + 2), (this.parent.parent.getY() + this.offset + 4), -1);
    }

    @Override
    public void updateComponent(final int mouseX, final int mouseY) {
        this.hovered = this.isMouseOnButton(mouseX, mouseY);
        this.y = this.parent.parent.getY() + this.offset;
        this.x = this.parent.parent.getX();
    }

    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (this.isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.open) {
            this.binding = !this.binding;
        }
    }

    @Override
    public void keyTyped(final char typedChar, final int key) {
        if (this.binding) {
            if (key == 211) {
                this.parent.mod.setBind(0);
            }
            else {
                if (key == Keyboard.KEY_ESCAPE){
                    this.binding = false;
                }
                else {
                    this.parent.mod.setBind(key);
                }
            }
            this.binding = false;
        }
    }

    public boolean isMouseOnButton(final int x, final int y) {
        return x > this.x && x < this.x + 88 && y > this.y && y < this.y + 16;
    }
}

