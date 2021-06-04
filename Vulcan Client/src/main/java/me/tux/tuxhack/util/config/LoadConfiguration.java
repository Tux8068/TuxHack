package me.tux.tuxhack.util.config;

import me.tux.tuxhack.TuxHack;
import me.tux.tuxhack.clickgui.ClickGUI;
import me.tux.tuxhack.command.Command;
import me.tux.tuxhack.macro.Macro;
import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.module.ModuleManager;
import me.tux.tuxhack.util.enemy.Enemies;
import me.tux.tuxhack.util.friend.Friends;
import me.tux.tuxhack.util.font.CFontRenderer;
import me.tux.tuxhack.clickgui.Frames;
import com.mojang.realmsclient.gui.ChatFormatting;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.*;
import java.util.Iterator;

/**
 * Made by Hoosiers on 08/02/20 for GameSense, some functions were modified and ported from the original ConfigUtils
 */

public class LoadConfiguration {

    public LoadConfiguration(){

        //loads functions on client startup
        loadBinds();
        loadDrawn();
        loadEnabled();
        loadEnemies();
        loadFont();
        loadFriends();
        loadGUI();
        loadMacros();
        loadMessages();
        loadPrefix();
    }

    //loads gui settings
    public void loadGUI(){
        try {
            File file = new File(SaveConfiguration.Miscellaneous.getAbsolutePath(), "ClickGUI.json");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null){
                String curLine = line.trim();
                String name = curLine.split(":")[0];
                String x = curLine.split(":")[1];
                String y = curLine.split(":")[2];
                String e = curLine.split(":")[3];
                int x1 = Integer.parseInt(x);
                int y1 = Integer.parseInt(y);
                boolean open = Boolean.parseBoolean(e);
                Frames frames = ClickGUI.getFrameByName(name);
                if (frames != null) {
                    frames.x = x1;
                    frames.y = y1;
                    frames.open = open;
                }
            }
            br.close();
        }
        catch (Exception var6){
            var6.printStackTrace();
            SaveConfiguration.saveGUI();
        }
    }

    //loads client macros
    public void loadMacros(){
        try {
            File file = new File(SaveConfiguration.Miscellaneous.getAbsolutePath(), "ClientMacros.json");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line = br.readLine()) != null) {
                String curLine = line.trim();
                String bind = curLine.split(":")[0];
                String value = curLine.split(":")[1];
                TuxHack.getInstance().macroManager.addMacro(new Macro(Keyboard.getKeyIndex(bind), value.replace("_", " ")));
            }
            br.close();
        }
        catch (Exception var6){
            var6.printStackTrace();
            SaveConfiguration.saveMacros();
        }
    }

    //loads friends
    public void loadFriends(){
        try {
            File file = new File(SaveConfiguration.Miscellaneous.getAbsolutePath(), "Friends.json");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            Friends.friends.clear();
            String line;
            while((line = br.readLine()) != null) {
                TuxHack.getInstance().friends.addFriend(line);
            }
            br.close();
        }
        catch (Exception var6){
            var6.printStackTrace();
            SaveConfiguration.saveFriends();
        }
    }

    //loads enemies
    public void loadEnemies(){
        try {
            File file = new File(SaveConfiguration.Miscellaneous.getAbsolutePath(), "Enemies.json");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            Enemies.enemies.clear();
            String line;
            while((line = br.readLine()) != null) {
                Enemies.addEnemy(line);
            }
            br.close();
        }
        catch (Exception var6){
            var6.printStackTrace();
            SaveConfiguration.saveEnemies();
        }
    }

    //loads client command prefix
    public void loadPrefix(){
        try {
            File file = new File(SaveConfiguration.Miscellaneous.getAbsolutePath(), "CommandPrefix.json");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line = br.readLine()) != null) {
                Command.setPrefix(line);
            }
            br.close();
        }
        catch (Exception var6){
            var6.printStackTrace();
            SaveConfiguration.savePrefix();
        }
    }

    //loads custom font
    public void loadFont(){
        try {
            File file = new File(SaveConfiguration.Miscellaneous.getAbsolutePath(), "CustomFont.json");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line = br.readLine()) != null) {
                String name = line.split(":")[0];
                String size = line.split(":")[1];
                int sizeInt = Integer.parseInt(size);
                TuxHack.fontRenderer = new CFontRenderer(new Font(name, Font.PLAIN, sizeInt), true, false);
                TuxHack.fontRenderer.setFont(new Font(name, Font.PLAIN, sizeInt));
                TuxHack.fontRenderer.setAntiAlias(true);
                TuxHack.fontRenderer.setFractionalMetrics(false);
                TuxHack.fontRenderer.setFontName(name);
                TuxHack.fontRenderer.setFontSize(sizeInt);
            }
            br.close();
        }
        catch (Exception var6){
            var6.printStackTrace();
            SaveConfiguration.saveFont();
        }
    }

    //loads client messages such as the watermark
    public void loadMessages(){
        try {
            File file = new File(SaveConfiguration.Messages.getAbsolutePath(), "ClientMessages.json");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line = br.readLine()) != null) {
                String curLine = line.trim();
                String watermark = curLine.split(",")[0];
                String color = curLine.split(",")[1];
                boolean w = Boolean.parseBoolean(watermark);
                ChatFormatting c = ChatFormatting.getByName(color);
                Command.cf = c;
                Command.MsgWaterMark = w;
            }
            br.close();
        }
        catch (Exception var6){
            var6.printStackTrace();
            SaveConfiguration.saveMessages();
        }
    }

    //loads drawn modules
    public void loadDrawn(){
        try {
            File file = new File(SaveConfiguration.Miscellaneous.getAbsolutePath(), "DrawnModules.json");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line = br.readLine()) != null) {
                String curLine = line.trim();
                String name = curLine.split(":")[0];
                String isOn = curLine.split(":")[1];
                boolean drawn = Boolean.parseBoolean(isOn);
                for(Module m : ModuleManager.getModules()) {
                    if (m.getName().equalsIgnoreCase(name)) {
                        m.setDrawn(drawn);
                    }
                }
            }
            br.close();
        }
        catch (Exception var6){
            var6.printStackTrace();
            SaveConfiguration.saveDrawn();
        }
    }

    //loads enabled/disabled modules
    public void loadEnabled(){
        try {
            File file = new File(SaveConfiguration.Miscellaneous.getAbsolutePath(), "EnabledModules.json");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line = br.readLine()) != null) {
                Iterator var6 = ModuleManager.getModules().iterator();
                while(var6.hasNext()) {
                    Module m = (Module)var6.next();
                    if (m.getName().equals(line)) {
                        m.enable();
                    }
                }
            }
            br.close();
        }
        catch (Exception var6){
            var6.printStackTrace();
            SaveConfiguration.saveEnabled();
        }
    }

    //loads module binds
    public void loadBinds(){
        try {
            File file = new File(SaveConfiguration.Miscellaneous.getAbsolutePath(), "ModuleBinds.json");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line = br.readLine()) != null) {
                String curLine = line.trim();
                String name = curLine.split(":")[0];
                String bind = curLine.split(":")[1];
                for(Module m : ModuleManager.getModules()) {
                    if (m != null && m.getName().equalsIgnoreCase(name)) {
                        m.setBind(Keyboard.getKeyIndex(bind));
                    }
                }
            }
            br.close();
        }
        catch (Exception var6){
            var6.printStackTrace();
            SaveConfiguration.saveBinds();
        }
    }
}
