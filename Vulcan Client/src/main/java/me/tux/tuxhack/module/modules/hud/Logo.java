package me.tux.tuxhack.module.modules.hud;

import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.setting.Setting;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Logo extends Module {

    private ResourceLocation logo;
    private static Setting.Integer x;
    private static Setting.Integer y;
    private static Setting.Integer height;
    private static Setting.Integer width;
    private static Setting.Integer uWidth;
    private static Setting.Integer uHeight;

    public boolean setup() {
        x = registerInteger("X", "xLogoPos", 0, 0, 1280);
        y = registerInteger("Y", "yLogoPos", 0, 0, 960);
        height = registerInteger("Logo Width", "logoWidth", 64, 32, 2560);
        width = registerInteger("Logo Height", "logoHeight", 64, 32, 2560);
        uWidth = registerInteger("uWidth", "uWidth",64 ,32 , 2560);
        uHeight = registerInteger("uWidth", "uWidth",64 ,32 , 2560);
        return false;
    }

    public Logo() {
        super("Logo", "Displays a Logo on screen!", Category.HUD);
    }

    @Override
    public void onRender() {



        mc.renderEngine.bindTexture(logo);
        GlStateManager.color(255.0f, 255.0f, 255.0f);
        Gui.drawScaledCustomSizeModalRect(x.getValue(), y.getValue(), 7.0f, 7.0f, width.getValue(), height.getValue(), uWidth.getValue(), uHeight.getValue(), width.getValue(), height.getValue());
    }

    private void onLoad() {
        BufferedImage image = null;
        DynamicTexture dynamicTexture;
        try {
           if (getFile("/watermark.png") != null) {
                image = this.getImage(getFile("/watermark.png"), ImageIO::read);
            } else {
                dynamicTexture = new DynamicTexture(image);
                dynamicTexture.loadTexture(mc.getResourceManager());
                logo = mc.getTextureManager().getDynamicTextureLocation("watermark.png", dynamicTexture);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FunctionalInterface
    private interface ThrowingFunction<T, R>
    {
        R apply(final T p0) throws IOException;
    }

    private InputStream getFile(String string) {
        return Logo.class.getResourceAsStream(string);
    }


    private <T> BufferedImage getImage(final T source, final ThrowingFunction<T, BufferedImage> readFunction) {
        try {
            return readFunction.apply(source);
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}