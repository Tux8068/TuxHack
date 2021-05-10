package me.ka1.vulcan.module.modules.render;

import me.ka1.vulcan.module.Module;
import me.ka1.vulcan.setting.Setting;
import me.ka1.vulcan.util.friend.Friends;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Tracers extends Module {
    public Tracers() {
        super("Tracers", "Draws a line to players in render distance", Category.Render);
    }

    /**
     * @author _KA1
     * 30/3/21
     */

    Setting.Integer range;
    Setting.Boolean friends;
    Setting.Double opacity;
    Setting.Double r;
    Setting.Double g;
    Setting.Double b;

    int playerAmount;
    Color color;


    public void setup() {
    range = registerInteger("Range", "range", 96, 1, 240);
    friends = registerBoolean("Friends", "friends", true);
    opacity = registerDouble("Opacity", "opacity", 0.8f, 0.1f, 1.0f);
    r = registerDouble("Red", "red", 255, 0, 255);
    g = registerDouble("Green", "green", 255, 0, 255);
    b = registerDouble("Blue", "blue", 255, 0, 255);
    }

    public void onRender() {
        if (mc.player == null || mc.world == null)
            return;
        playerAmount = 0;
        GlStateManager.pushMatrix();
        mc.world.loadedEntityList.stream()
                .filter(e->e instanceof EntityPlayer)
                .filter(e->e != mc.player)
                .filter(e-> mc.player.getDistance(e) <= range.getValue())
                .forEach(e->{
                    if (!friends.getValue() && (Friends.isFriend(e.getName())))
                        return;

                    /**
                     *  ***everything upwards works***
                     * Draw line to (e.getPosition)
                     */

                    drawLineToEntity(e, (float) r.getValue(), (float) g.getValue(), (float) b.getValue(),(float) opacity.getValue());


                    playerAmount++;
                });
        GlStateManager.popMatrix();
    }

    public static double interpolate(double now, double then) {
        return then + (now - then) * mc.getRenderPartialTicks();
    }

    public static double[] interpolate(Entity e) {
        double posX = interpolate(e.posX, e.lastTickPosX) - mc.getRenderManager().renderPosX;
        double posY = interpolate(e.posY, e.lastTickPosY) - mc.getRenderManager().renderPosY;
        double posZ = interpolate(e.posZ, e.lastTickPosZ) - mc.getRenderManager().renderPosZ;
        return new double[] { posX, posY, posZ };
    }

    public static void drawLineToEntity(Entity e, float red, float green, float blue, float opacity) {
        double[] xyz = interpolate(e);
        drawLine(xyz[0],xyz[1],xyz[2], e.height, red, green, blue, opacity);
    }

    public static void drawLine(double posx, double posy, double posz, double up, float red, float green, float blue, float opacity)
    {
        Vec3d eyes = new Vec3d(0, 0, 1)
                .rotatePitch(-(float)Math
                        .toRadians(Minecraft.getMinecraft().player.rotationPitch))
                .rotateYaw(-(float)Math
                        .toRadians(Minecraft.getMinecraft().player.rotationYaw));

        drawLineFromPosToPos(eyes.x, eyes.y + mc.player.getEyeHeight(), eyes.z, posx, posy, posz, up, red, green, blue, opacity);
    }

    public static void drawLineFromPosToPos(double posx, double posy, double posz, double posx2, double posy2, double posz2, double up, float red, float green, float blue, float opacity) {
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(1.5f);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glColor4f(red, green, blue, opacity);
        GlStateManager.disableLighting();
        GL11.glLoadIdentity();
        mc.entityRenderer.orientCamera(mc.getRenderPartialTicks());

        GL11.glBegin(GL11.GL_LINES);
        {
            GL11.glVertex3d(posx, posy, posz);
            GL11.glVertex3d(posx2, posy2, posz2);
            GL11.glVertex3d(posx2, posy2, posz2);
            GL11.glVertex3d(posx2, posy2+up, posz2);
        }

        GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glColor3d(1d,1d,1d);
        GlStateManager.enableLighting();
    }
}
