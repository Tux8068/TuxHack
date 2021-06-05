package me.tux.tuxhack;

import doctor.swag.hwid.HwidManager;
import me.tux.tuxhack.Cape.CapeManager;
import me.tux.tuxhack.ClickGui2.ClickGUI2;
import me.tux.tuxhack.command.CommandManager;
import me.tux.tuxhack.event.EventProcessor;
import me.tux.tuxhack.macro.MacroManager;
import me.tux.tuxhack.module.ModuleManager;
import me.tux.tuxhack.setting.SettingsManager;
import me.tux.tuxhack.util.TpsUtils;
import me.tux.tuxhack.util.config.*;
import me.tux.tuxhack.util.enemy.Enemies;
import me.tux.tuxhack.util.font.CFontRenderer;
import me.tux.tuxhack.util.friend.Friends;
import me.zero.alpine.EventBus;
import me.zero.alpine.EventManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Method;

@Mod(modid = TuxHack.MOD_ID, name = TuxHack.MOD_NAME, version = TuxHack.VERSION)
public class TuxHack {
    public static final String MOD_ID = "tuxhack";
    public static final String MOD_NAME = "TuxHack";
    public static final String VERSION = "1.4.0";

    public static final Logger log = LogManager.getLogger(MOD_NAME);
    public static final EventBus EVENT_BUS = new EventManager();
    public static LoadConfiguration loadConfiguration;
    public static LoadModules loadModules;
    public static CFontRenderer fontRenderer;
    public static Enemies enemies;
    /**
     * This is the instance of your mod as created by Forge. It will never be null.
     */
    @Mod.Instance
    private static TuxHack INSTANCE;
    public boolean verified = false;
    public MacroManager macroManager;
    public SaveConfiguration saveConfiguration;
    public SaveModules saveModules;
    public ClickGUI2 clickGUI;
    public Friends friends;
    public SettingsManager settingsManager;
    public ModuleManager moduleManager;
    EventProcessor eventProcessor;
    private EventManager eventManager;

    public TuxHack() {
        INSTANCE = this;
    }

    public static TuxHack getInstance() {
        return INSTANCE;
    }

    public EventManager getEventManager() {
        if (this.eventManager == null) {
            this.eventManager = new EventManager();
        }

        return this.eventManager;
    }

    /**
     * This is the first initialization event.
     * The registry events below will have fired prior to entry to this method.
     */
    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        log.info("h");
    }

    /**
     * This is the second initialization event. Initialize your managers here.
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

        /**
         * HWID stuff
         */
        HwidManager.Reload();

        try {
            Method invoker = HwidManager.class.getMethod("Reload");

            invoker.invoke("PresetManager");
        } catch (Exception exc) {
           // exc.printStackTrace();
        }

        eventProcessor = new EventProcessor();
        eventProcessor.init();

        fontRenderer = new CFontRenderer(new Font("Arial", Font.ITALIC, 18), true, false);
        TpsUtils tpsUtils = new TpsUtils();
        settingsManager = new SettingsManager();

        friends = new Friends();
        enemies = new Enemies();
        log.info("Friends and enemies initialized!");

        moduleManager = new ModuleManager();
        log.info("Settings initialized!");
        log.info("Modules initialized!");

        clickGUI = new ClickGUI2();
        log.info("ClickGUI initialized!");

        macroManager = new MacroManager();
        log.info("Macros initialized!");

        saveConfiguration = new SaveConfiguration();
        Runtime.getRuntime().addShutdownHook(new Stopper());
        log.info("Config Saved!");

        loadConfiguration = new LoadConfiguration();
        log.info("Config Loaded!");

        saveModules = new SaveModules();
        Runtime.getRuntime().addShutdownHook(new Stopper());
        log.info("Modules Saved!");

        loadModules = new LoadModules();
        log.info("Modules Loaded!");

        CommandManager.initCommands();
        log.info("Commands initialized!");

        log.info("Initialization complete!\n");

        try {
            CapeManager.init();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This is the final initialization event.
     */
    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {
        Display.setTitle(MOD_NAME + " " + VERSION);

        log.info("PostInitialization of tuxhack complete!\n");
    }
    public static String getVersion() {
        return VERSION;
    }
}
