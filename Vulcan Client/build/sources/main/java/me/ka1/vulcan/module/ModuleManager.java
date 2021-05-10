package me.ka1.vulcan.module;

import me.ka1.vulcan.event.events.RenderEvent;
import me.ka1.vulcan.module.modules.Hud.*;
import me.ka1.vulcan.module.modules.chat.ChatEncrypt;
import me.ka1.vulcan.module.modules.chat.ChatSuffix;
import me.ka1.vulcan.module.modules.client.*;
import me.ka1.vulcan.module.modules.combat.*;
import me.ka1.vulcan.module.modules.misc.*;
import me.ka1.vulcan.module.modules.movement.MoonWalk;
import me.ka1.vulcan.module.modules.movement.ReverseStep;
import me.ka1.vulcan.module.modules.movement.Sprint;
import me.ka1.vulcan.module.modules.movement.Step;
import me.ka1.vulcan.module.modules.player.*;
import me.ka1.vulcan.module.modules.render.*;
import me.ka1.vulcan.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ModuleManager {

    public static ArrayList<Module> modules;

    public ModuleManager(){
        modules = new ArrayList<>();
        //Combat
        addMod(new AutoCrystal());
        addMod(new AutoMend());
        addMod(new AutoTotem());
        addMod(new Burrow());
        addMod(new BurrowBypass());
        addMod(new BedAura());
        addMod(new BowAim());
        addMod(new BowSpam());
        addMod(new EasyPearl());
        addMod(new GhostEXP());
        addMod(new KillAura());
        addMod(new SelfTrap());
        addMod(new Surround());
        addMod(new spiderman());
        addMod(new ExpFast());
        //Movement
        addMod(new MoonWalk());
        addMod(new ReverseStep());
        addMod(new Step());
        addMod(new Sprint());
        //Player
        addMod(new FastCrystal());
        addMod(new OldFagDupe());
        addMod(new PotionDetect());
        addMod(new Suicide());
        addMod(new MountBypass());
        addMod(new Xcarry());
        //Misc
        addMod(new RageQuit());
        addMod(new AntiAim());
        addMod(new FakePlayer());
        //addMod(new FastPlace());
        addMod(new GhastNotifier());
        addMod(new ArmorNotification());
        //Client
        addMod(new LoadConfig());
        addMod(new FpsLimiter());
        addMod(new DiscordRPC());
        addMod(new ClickGuiModule());
        addMod(new CustomFont());
        addMod(new Rainbow());
        addMod(new CommandColor());
        addMod(new TeamTux());
        addMod(new Zormios());
        //Hud
        addMod(new Inventory());
        addMod(new me.ka1.vulcan.module.modules.Hud.ArrayList());
        addMod(new Time());
        addMod(new Welcomer());
        addMod(new Coordinates());
        addMod(new Watermark());
        addMod(new CombatInfo());
        addMod(new TPS());
        addMod(new FPS());
        addMod(new ping());
        addMod(new HudEditor());
        addMod(new willy());
        // addMod(new DatabaseCounter());
        //Render
        addMod(new Tracers());
        addMod(new TextRadar());
        addMod(new BlockHighlight());
        addMod(new Logo());
        addMod(new LowHands());
        addMod(new Esp());
        addMod(new Nametags());
        addMod(new Fullbright());
        addMod(new Zoom());
        addMod(new FOVSlider());
        //chat
        addMod(new ChatSuffix());
        addMod(new ChatEncrypt());
    }

    public static void addMod(Module m){
        modules.add(m);
    }

    public static void onUpdate() {
        modules.stream().filter(Module::isEnabled).forEach(Module::onUpdate);
    }

    public static void onRender() {
        modules.stream().filter(Module::isEnabled).forEach(Module::onRender);
    }

    public static void onWorldRender(RenderWorldLastEvent event) {

        Minecraft.getMinecraft().profiler.startSection("Vulcan");
        Minecraft.getMinecraft().profiler.startSection("setup");

        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        GlStateManager.disableDepth();
        GlStateManager.glLineWidth(1f);

        Vec3d renderPos = getInterpolatedPos(Minecraft.getMinecraft().player, event.getPartialTicks());

        RenderEvent e = new RenderEvent(RenderUtil.INSTANCE, renderPos, event.getPartialTicks());
        e.resetTranslation();
        Minecraft.getMinecraft().profiler.endSection();

        modules.stream().filter(module -> module.isEnabled()).forEach(module -> {
            Minecraft.getMinecraft().profiler.startSection(module.getName());
            module.onWorldRender(e);
            Minecraft.getMinecraft().profiler.endSection();
        });

        Minecraft.getMinecraft().profiler.startSection("release");

        GlStateManager.glLineWidth(1f);
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.enableCull();
        RenderUtil.releaseGL();

        Minecraft.getMinecraft().profiler.endSection();
        Minecraft.getMinecraft().profiler.endSection();
    }

    public static ArrayList<Module> getModules() {
        return modules;
    }

    public static ArrayList<Module> getModulesInCategory(Module.Category c){
        ArrayList<Module> list = (ArrayList<Module>) getModules().stream().filter(m -> m.getCategory().equals(c)).collect(Collectors.toList());
        return list;
    }

    public static void onBind(int key) {
        if (key == 0 || key == Keyboard.KEY_NONE) return;
        modules.forEach(module -> {
            if(module.getBind() == key){
                module.toggle();
            }
        });
    }

    public static Module getModuleByName(String name){
        Module m = getModules().stream().filter(mm->mm.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
        return m;
    }

    public static boolean isModuleEnabled(String name){
        Module m = getModules().stream().filter(mm->mm.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
        return m.isEnabled();
    }

    public static boolean isModuleEnabled(Module m){
        return m.isEnabled();
    }

    public static Vec3d getInterpolatedPos(Entity entity, float ticks) {
        return new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ).add(getInterpolatedAmount(entity, ticks));
    }

    public static Vec3d getInterpolatedAmount(Entity entity, double ticks) {
        return getInterpolatedAmount(entity, ticks, ticks, ticks);
    }

    public static Vec3d getInterpolatedAmount(Entity entity, double x, double y, double z) {
        return new Vec3d(
                (entity.posX - entity.lastTickPosX) * x,
                (entity.posY - entity.lastTickPosY) * y,
                (entity.posZ - entity.lastTickPosZ) * z
        );
    }
}
