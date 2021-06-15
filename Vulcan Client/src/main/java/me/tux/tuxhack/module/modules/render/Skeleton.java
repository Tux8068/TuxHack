package me.tux.tuxhack.module.modules.render;

import me.tux.tuxhack.mixin.mixins.accessor.IRenderManager;
import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.setting.Setting;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.entity.player.EntityPlayer;
import java.util.HashMap;
import net.minecraft.client.renderer.culling.ICamera;

public class Skeleton extends Module {
    public Skeleton() {
        super("SkeletonESP", "SkeletonESP, renders a skeleton on players", Category.RENDER);
        final HashMap<EntityPlayer, float[][]> entities;

    }
    private static final HashMap<EntityPlayer, float[][]> entities;
    Setting.Integer red;
    Setting.Integer green;
    Setting.Integer blue;
    Setting.Integer alpha;
    ICamera camera;


    public boolean setup() {
        red = registerInteger("red", "red", 255, 0, 255);
        green = registerInteger("green", "green", 255, 0, 255);
        blue = registerInteger("blue", "blue", 255, 0, 255);
        alpha = registerInteger("alpha", "", 255, 0, 255);
        this.camera = (ICamera) new Frustum();

        return false;
    }


    private Vec3d getVec3(final RenderWorldLastEvent event, final EntityPlayer e) {
        final float pt = event.getPartialTicks();
        final double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * pt;
        final double y = e.lastTickPosY + (e.posY - e.lastTickPosY) * pt;
        final double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * pt;
        return new Vec3d(x, y, z);
    }

    @SubscribeEvent
    public void onWorldRender(final RenderWorldLastEvent event) {
        if (this.mc.getRenderManager() == null || this.mc.getRenderManager().options == null) {
            return;
        }
        this.startEnd(true);
        GL11.glEnable(2903);
        GL11.glDisable(2848);
        Skeleton.entities.keySet().removeIf(this::doesntContain);
        this.mc.world.playerEntities.forEach(e -> this.drawSkeleton(event, e));
        Gui.drawRect(0, 0, 0, 0, 0);
        this.startEnd(false);
    }

    private void drawSkeleton(final RenderWorldLastEvent event, final EntityPlayer e) {
        final double d3 = this.mc.player.lastTickPosX + (this.mc.player.posX - this.mc.player.lastTickPosX) * event.getPartialTicks();
        final double d4 = this.mc.player.lastTickPosY + (this.mc.player.posY - this.mc.player.lastTickPosY) * event.getPartialTicks();
        final double d5 = this.mc.player.lastTickPosZ + (this.mc.player.posZ - this.mc.player.lastTickPosZ) * event.getPartialTicks();
        this.camera.setPosition(d3, d4, d5);
        final float[][] entPos = Skeleton.entities.get(e);
        if (entPos != null && e.isEntityAlive() && this.camera.isBoundingBoxInFrustum(e.getEntityBoundingBox()) && !e.isDead && e != this.mc.player && !e.isPlayerSleeping()) {
            GL11.glPushMatrix();
            GL11.glEnable(2848);
            GL11.glLineWidth(1.0f);
            GlStateManager.color(this.red.getValue() / 255.0f, this.green.getValue() / 255.0f, this.blue.getValue() / 255.0f, this.alpha.getValue() / 255.0f);
            final Vec3d vec = this.getVec3(event, e);
            final double x = vec.x - ((IRenderManager)this.mc.getRenderManager()).getRenderPosX();
            final double y = vec.y - ((IRenderManager)this.mc.getRenderManager()).getRenderPosY();
            final double z = vec.z - ((IRenderManager)this.mc.getRenderManager()).getRenderPosZ();
            GL11.glTranslated(x, y, z);
            final float xOff = e.prevRenderYawOffset + (e.renderYawOffset - e.prevRenderYawOffset) * event.getPartialTicks();
            GL11.glRotatef(-xOff, 0.0f, 1.0f, 0.0f);
            GL11.glTranslated(0.0, 0.0, e.isSneaking() ? -0.235 : 0.0);
            final float yOff = e.isSneaking() ? 0.6f : 0.75f;
            GL11.glPushMatrix();
            GlStateManager.color(this.red.getValue() / 255.0f, this.green.getValue() / 255.0f, this.blue.getValue() / 255.0f, this.alpha.getValue() / 255.0f);
            GL11.glTranslated(-0.125, (double)yOff, 0.0);
            if (entPos[3][0] != 0.0f) {
                GL11.glRotatef(entPos[3][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
            }
            if (entPos[3][1] != 0.0f) {
                GL11.glRotatef(entPos[3][1] * 57.295776f, 0.0f, 1.0f, 0.0f);
            }
            if (entPos[3][2] != 0.0f) {
                GL11.glRotatef(entPos[3][2] * 57.295776f, 0.0f, 0.0f, 1.0f);
            }
            GL11.glBegin(3);
            GL11.glVertex3d(0.0, 0.0, 0.0);
            GL11.glVertex3d(0.0, (double)(-yOff), 0.0);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GlStateManager.color(this.red.getValue() / 255.0f, this.green.getValue() / 255.0f, this.blue.getValue() / 255.0f, this.alpha.getValue() / 255.0f);
            GL11.glTranslated(0.125, (double)yOff, 0.0);
            if (entPos[4][0] != 0.0f) {
                GL11.glRotatef(entPos[4][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
            }
            if (entPos[4][1] != 0.0f) {
                GL11.glRotatef(entPos[4][1] * 57.295776f, 0.0f, 1.0f, 0.0f);
            }
            if (entPos[4][2] != 0.0f) {
                GL11.glRotatef(entPos[4][2] * 57.295776f, 0.0f, 0.0f, 1.0f);
            }
            GL11.glBegin(3);
            GL11.glVertex3d(0.0, 0.0, 0.0);
            GL11.glVertex3d(0.0, (double)(-yOff), 0.0);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glTranslated(0.0, 0.0, e.isSneaking() ? 0.25 : 0.0);
            GL11.glPushMatrix();
            GlStateManager.color(this.red.getValue() / 255.0f, this.green.getValue() / 255.0f, this.blue.getValue() / 255.0f, this.alpha.getValue() / 255.0f);
            GL11.glTranslated(0.0, e.isSneaking() ? -0.05 : 0.0, e.isSneaking() ? -0.01725 : 0.0);
            GL11.glPushMatrix();
            GlStateManager.color(this.red.getValue() / 255.0f, this.green.getValue() / 255.0f, this.blue.getValue() / 255.0f, this.alpha.getValue() / 255.0f);
            GL11.glTranslated(-0.375, yOff + 0.55, 0.0);
            if (entPos[1][0] != 0.0f) {
                GL11.glRotatef(entPos[1][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
            }
            if (entPos[1][1] != 0.0f) {
                GL11.glRotatef(entPos[1][1] * 57.295776f, 0.0f, 1.0f, 0.0f);
            }
            if (entPos[1][2] != 0.0f) {
                GL11.glRotatef(-entPos[1][2] * 57.295776f, 0.0f, 0.0f, 1.0f);
            }
            GL11.glBegin(3);
            GL11.glVertex3d(0.0, 0.0, 0.0);
            GL11.glVertex3d(0.0, -0.5, 0.0);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslated(0.375, yOff + 0.55, 0.0);
            if (entPos[2][0] != 0.0f) {
                GL11.glRotatef(entPos[2][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
            }
            if (entPos[2][1] != 0.0f) {
                GL11.glRotatef(entPos[2][1] * 57.295776f, 0.0f, 1.0f, 0.0f);
            }
            if (entPos[2][2] != 0.0f) {
                GL11.glRotatef(-entPos[2][2] * 57.295776f, 0.0f, 0.0f, 1.0f);
            }
            GL11.glBegin(3);
            GL11.glVertex3d(0.0, 0.0, 0.0);
            GL11.glVertex3d(0.0, -0.5, 0.0);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glRotatef(xOff - e.rotationYawHead, 0.0f, 1.0f, 0.0f);
            GL11.glPushMatrix();
            GlStateManager.color(this.red.getValue() / 255.0f, this.green.getValue() / 255.0f, this.blue.getValue() / 255.0f, this.alpha.getValue() / 255.0f);
            GL11.glTranslated(0.0, yOff + 0.55, 0.0);
            if (entPos[0][0] != 0.0f) {
                GL11.glRotatef(entPos[0][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
            }
            GL11.glBegin(3);
            GL11.glVertex3d(0.0, 0.0, 0.0);
            GL11.glVertex3d(0.0, 0.3, 0.0);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPopMatrix();
            GL11.glRotatef(e.isSneaking() ? 25.0f : 0.0f, 1.0f, 0.0f, 0.0f);
            GL11.glTranslated(0.0, e.isSneaking() ? -0.16175 : 0.0, e.isSneaking() ? -0.48025 : 0.0);
            GL11.glPushMatrix();
            GL11.glTranslated(0.0, (double)yOff, 0.0);
            GL11.glBegin(3);
            GL11.glVertex3d(-0.125, 0.0, 0.0);
            GL11.glVertex3d(0.125, 0.0, 0.0);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GlStateManager.color(this.red.getValue() / 255.0f, this.green.getValue() / 255.0f, this.blue.getValue() / 255.0f, this.alpha.getValue() / 255.0f);
            GL11.glTranslated(0.0, (double)yOff, 0.0);
            GL11.glBegin(3);
            GL11.glVertex3d(0.0, 0.0, 0.0);
            GL11.glVertex3d(0.0, 0.55, 0.0);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslated(0.0, yOff + 0.55, 0.0);
            GL11.glBegin(3);
            GL11.glVertex3d(-0.375, 0.0, 0.0);
            GL11.glVertex3d(0.375, 0.0, 0.0);
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glPopMatrix();
        }
    }

    private void startEnd(final boolean revert) {
        if (revert) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GL11.glEnable(2848);
            GlStateManager.disableDepth();
            GlStateManager.disableTexture2D();
            GL11.glHint(3154, 4354);
        }
        else {
            GlStateManager.disableBlend();
            GlStateManager.enableTexture2D();
            GL11.glDisable(2848);
            GlStateManager.enableDepth();
            GlStateManager.popMatrix();
        }
        GlStateManager.depthMask(!revert);
    }

    public static void addEntity(final EntityPlayer e, final ModelPlayer model) {
        Skeleton.entities.put(e, new float[][] { { model.bipedHead.rotateAngleX, model.bipedHead.rotateAngleY, model.bipedHead.rotateAngleZ }, { model.bipedRightArm.rotateAngleX, model.bipedRightArm.rotateAngleY, model.bipedRightArm.rotateAngleZ }, { model.bipedLeftLeg.rotateAngleX, model.bipedLeftLeg.rotateAngleY, model.bipedLeftLeg.rotateAngleZ }, { model.bipedRightLeg.rotateAngleX, model.bipedRightLeg.rotateAngleY, model.bipedRightLeg.rotateAngleZ }, { model.bipedLeftLeg.rotateAngleX, model.bipedLeftLeg.rotateAngleY, model.bipedLeftLeg.rotateAngleZ } });
    }

    private boolean doesntContain(final EntityPlayer var0) {
        return !this.mc.world.playerEntities.contains(var0);
    }

    static {
        entities = new HashMap<EntityPlayer, float[][]>();
    }
}

