package me.ka1.vulcan;

import me.ka1.vulcan.command.CommandManager;
import me.ka1.vulcan.event.EventProcessor;
import me.ka1.vulcan.macro.MacroManager;
import me.ka1.vulcan.module.ModuleManager;
import me.ka1.vulcan.setting.SettingsManager;
import me.ka1.vulcan.util.TpsUtils;
import me.ka1.vulcan.util.enemy.Enemies;
import me.ka1.vulcan.util.font.CFontRenderer;
import me.ka1.vulcan.util.friend.Friends;
import me.ka1.vulcan.ClickGui2.ClickGUI2;
import me.ka1.vulcan.util.config.LoadConfiguration;
import me.ka1.vulcan.util.config.LoadModules;
import me.ka1.vulcan.util.config.SaveConfiguration;
import me.ka1.vulcan.util.config.SaveModules;
import me.zero.alpine.EventBus;
import me.zero.alpine.EventManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import me.ka1.vulcan.util.config.Stopper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.lwjgl.opengl.Display;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

@Mod(modid = Vulcan.MOD_ID, name = Vulcan.MOD_NAME, version = Vulcan.VERSION)
public class Vulcan {
    public static final String MOD_ID = "tuxhack";
    public static final String MOD_NAME = "TuxHack";
    public static final String VERSION = "1.1.0";

    public static final Logger log = LogManager.getLogger(MOD_NAME);

    public final boolean verified = false;
    public MacroManager macroManager;
    public SaveConfiguration saveConfiguration;
    public static LoadConfiguration loadConfiguration;
    public SaveModules saveModules;
    public static LoadModules loadModules;
    public ClickGUI2 clickGUI;
    public Friends friends;
    public SettingsManager settingsManager;
    public ModuleManager moduleManager;
    EventProcessor eventProcessor;
    public static CFontRenderer fontRenderer;
    public static Enemies enemies;
    private EventManager eventManager;

    public static final EventBus EVENT_BUS = new EventManager();

    /**
     * This is the instance of your mod as created by Forge. It will never be null.
     */
    @Mod.Instance
    private static Vulcan INSTANCE;

    public Vulcan(){
        INSTANCE = this;
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
        log.info("2q1 was here");
    }

  /*  @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        final String var0 = String.valueOf(System.getenv("os")) + System.getProperty("os.name") + System.getProperty("os.arch") + System.getProperty("os.version") + System.getProperty("user.language") + System.getenv("SystemRoot") + System.getenv("HOMEDRIVE") + System.getenv("PROCESSOR_LEVEL") + System.getenv("PROCESSOR_REVISION") + System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_ARCHITECTURE") + System.getenv("PROCESSOR_ARCHITEW6432") + System.getenv("NUMBER_OF_PROCESSORS");
        final String sha512hex = DigestUtils.sha512Hex(var0);
        final String key = DigestUtils.sha512Hex(sha512hex);
        try {
            String list = "MAKE HWID LIST URL";
            URL pastebin = new URL(list.toString());
            BufferedReader in = new BufferedReader(new InputStreamReader(pastebin.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.equalsIgnoreCase(key))
                    verified = true;
            }
            if (!verified) {
                JOptionPane.showMessageDialog(null, "copied hwid");
                StringSelection stringSelection = new StringSelection(key);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
            }
        } catch (Exception exception) {}
        if (!verified) {
            Runtime.getRuntime().halt(0);
        }
    }*/

    /**
     * This is the second initialization event. Initialize your managers here.
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        eventProcessor = new EventProcessor();
        eventProcessor.init();

        fontRenderer = new CFontRenderer(new Font("Arial", Font.PLAIN, 18), true, false);
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
    }

    /**
     * This is the final initialization event.
     */
    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {
        Display.setTitle(MOD_NAME + " " + VERSION);

        log.info("PostInitialization of tuxhack complete!\n");
    }

    public static Vulcan getInstance(){
        return INSTANCE;
    }
}
