package me.tux.tuxhack.module.modules.render;

import me.tux.tuxhack.command.Command;
import net.minecraft.client.tutorial.TutorialSteps;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemSword;
import java.util.Random;
import net.minecraft.client.renderer.ItemRenderer;
import me.tux.tuxhack.setting.Setting;
import me.tux.tuxhack.module.Module;

public class RenderTweaks extends Module
{
    public Setting.Boolean viewClip;
    Setting.Boolean OnePointSeven;
    Setting.Boolean lowOffhand;
    Setting.Boolean fovChanger;
    Setting.Double lowOffhandSlider;
    Setting.Integer fovChangerSlider;
    ItemRenderer itemRenderer;
    Setting.Boolean Australia;
    Setting.Boolean Tutorial;
    Random r;
    private float oldFOV;

    public RenderTweaks() {
        super("RenderTweaks", "rendertweaks", Category.WORLD);
        this.r = new Random();
        this.itemRenderer = RenderTweaks.mc.entityRenderer.itemRenderer;
    }

    @Override
    public boolean setup() {
        this.Australia = this.registerBoolean("Australia", "Australia", false);
        this.viewClip = this.registerBoolean("View Clip", "ViewClip", false);
        this.OnePointSeven = this.registerBoolean("1.7 animation", "1.7 animation", false);
        this.lowOffhand = this.registerBoolean("Low Offhand", "LowOffhand", false);
        this.lowOffhandSlider = this.registerDouble("Offhand Height", "OffhandHeight", 1.0, 0.1, 1.0);
        this.fovChanger = this.registerBoolean("FOV", "FOV", false);
        this.fovChangerSlider = this.registerInteger("FOV Slider", "FOVSlider", 135, 30, 150);
        this.Tutorial = this.registerBoolean("Tutorial", "Tutorial", false);
        return false;
    }

    @Override
    public int onUpdate() {
        if (this.OnePointSeven.getValue() && RenderTweaks.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword && RenderTweaks.mc.entityRenderer.itemRenderer.prevEquippedProgressMainHand >= 0.9) {
            RenderTweaks.mc.entityRenderer.itemRenderer.equippedProgressMainHand = 1.0f;
            RenderTweaks.mc.entityRenderer.itemRenderer.itemStackMainHand = RenderTweaks.mc.player.getHeldItemMainhand();
            if (this.OnePointSeven.getValue() && RenderTweaks.mc.player.getHeldItemMainhand().getItem() instanceof ItemBow && RenderTweaks.mc.entityRenderer.itemRenderer.prevEquippedProgressMainHand >= 0.9) {
                RenderTweaks.mc.entityRenderer.itemRenderer.equippedProgressMainHand = 1.0f;
                RenderTweaks.mc.entityRenderer.itemRenderer.itemStackMainHand = RenderTweaks.mc.player.getHeldItemMainhand();
                if (this.OnePointSeven.getValue() && RenderTweaks.mc.player.getHeldItemMainhand().getItem() instanceof ItemPickaxe && RenderTweaks.mc.entityRenderer.itemRenderer.prevEquippedProgressMainHand >= 0.9) {
                    RenderTweaks.mc.entityRenderer.itemRenderer.equippedProgressMainHand = 1.0f;
                    RenderTweaks.mc.entityRenderer.itemRenderer.itemStackMainHand = RenderTweaks.mc.player.getHeldItemMainhand();
                    if (this.OnePointSeven.getValue() && RenderTweaks.mc.player.getHeldItemMainhand().getItem() instanceof ItemAppleGold && RenderTweaks.mc.entityRenderer.itemRenderer.prevEquippedProgressMainHand >= 0.9) {
                        RenderTweaks.mc.entityRenderer.itemRenderer.equippedProgressMainHand = 1.0f;
                        RenderTweaks.mc.entityRenderer.itemRenderer.itemStackMainHand = RenderTweaks.mc.player.getHeldItemMainhand();
                        if (this.OnePointSeven.getValue() && RenderTweaks.mc.player.getHeldItemMainhand().getItem() instanceof ItemGlassBottle && RenderTweaks.mc.entityRenderer.itemRenderer.prevEquippedProgressMainHand >= 0.9) {
                            RenderTweaks.mc.entityRenderer.itemRenderer.equippedProgressMainHand = 1.0f;
                            RenderTweaks.mc.entityRenderer.itemRenderer.itemStackMainHand = RenderTweaks.mc.player.getHeldItemMainhand();
                            if (this.OnePointSeven.getValue() && RenderTweaks.mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock && RenderTweaks.mc.entityRenderer.itemRenderer.prevEquippedProgressMainHand >= 0.9) {
                                RenderTweaks.mc.entityRenderer.itemRenderer.equippedProgressMainHand = 1.0f;
                                RenderTweaks.mc.entityRenderer.itemRenderer.itemStackMainHand = RenderTweaks.mc.player.getHeldItemMainhand();
                            }
                            if (this.Tutorial.getValue()) {
                                Minecraft.getMinecraft().gameSettings.tutorialStep = TutorialSteps.NONE;
                                Minecraft.getMinecraft().gameSettings.tutorialStep = TutorialSteps.NONE;
                                Minecraft.getMinecraft().getTutorial().setStep(TutorialSteps.NONE);
                                Command.sendRawMessage("Set tutorial step to none!");
                            }

                        }
                    }
                }
            }
        }
        if (this.lowOffhand.getValue()) {
            this.itemRenderer.equippedProgressOffHand = (float)this.lowOffhandSlider.getValue();
        }
        if (this.fovChanger.getValue()) {
            RenderTweaks.mc.gameSettings.fovSetting = (float)this.fovChangerSlider.getValue();
        }
        if (!this.fovChanger.getValue()) {
            RenderTweaks.mc.gameSettings.fovSetting = this.oldFOV;
        }
        if (this.Australia.getValue()) {
            RenderTweaks.mc.gameSettings.fovSetting = 200.0f;
        }
        return 0;
    }

    public void onEnable() {
        this.oldFOV = RenderTweaks.mc.gameSettings.fovSetting;
    }

    public int onDisable() {
        RenderTweaks.mc.gameSettings.fovSetting = this.oldFOV;
        return 0;
    }
}
