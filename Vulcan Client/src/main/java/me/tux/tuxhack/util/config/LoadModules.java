package me.tux.tuxhack.util.config;

import me.tux.tuxhack.TuxHack;
import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.module.ModuleManager;
import me.tux.tuxhack.setting.Setting;

import java.io.*;

/**
 * Made by Hoosiers on 08/02/20 for GameSense, some functions were modified and ported from the original ConfigUtils
 */

public class LoadModules {

    //loads all functions for modules
    public LoadModules(){
        loadHud();
        loadCombat();
        loadPLAYER();
        loadClient();
        loadMisc();
        loadMovement();
        loadRender();
    }

    //loads COMBAT-related configs
    public void loadCombat(){
        File file;
        FileInputStream fstream;
        DataInputStream in;
        BufferedReader br;
        String line;
        String curLine;
        String configname;
        String isOn;
        String m;
        Setting mod;
        try {
            file = new File(SaveConfiguration.Combat.getAbsolutePath(), "Value.json");
            fstream = new FileInputStream(file.getAbsolutePath());
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));
            while((line = br.readLine()) != null) {
                curLine = line.trim();
                configname = curLine.split(":")[0];
                isOn = curLine.split(":")[1];
                m = curLine.split(":")[2];
                for(Module mm : ModuleManager.getModulesInCategory(Module.Category.COMBAT)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = TuxHack.getInstance().settingsManager.getSettingByNameAndModConfig(configname, mm);

                        if (mod instanceof Setting.Integer) {
                            ((Setting.Integer) mod).setValue(java.lang.Integer.parseInt(isOn));
                        } else if (mod instanceof Setting.Double){
                            ((Setting.Double) mod).setValue(java.lang.Double.parseDouble(isOn));
                        }
                    }
                }
            }
            br.close();
        } catch (Exception var13) {
            var13.printStackTrace();
        }
        try {
            file = new File(SaveConfiguration.Combat.getAbsolutePath(), "Boolean.json");
            fstream = new FileInputStream(file.getAbsolutePath());
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));

            while((line = br.readLine()) != null) {
                curLine = line.trim();
                configname = curLine.split(":")[0];
                isOn = curLine.split(":")[1];
                m = curLine.split(":")[2];
                for(Module mm : ModuleManager.getModulesInCategory(Module.Category.COMBAT)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = TuxHack.getInstance().settingsManager.getSettingByNameAndMod(configname, mm);
                        ((Setting.Boolean) mod).setValue(java.lang.Boolean.parseBoolean(isOn));
                    }
                }
            }
            br.close();
        } catch (Exception var12) {
            var12.printStackTrace();
        }
        try {
            file = new File(SaveConfiguration.Combat.getAbsolutePath(), "String.json");
            fstream = new FileInputStream(file.getAbsolutePath());
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));
            while((line = br.readLine()) != null) {
                curLine = line.trim();
                configname = curLine.split(":")[0];
                isOn = curLine.split(":")[1];
                m = curLine.split(":")[2];
                for(Module mm : ModuleManager.getModulesInCategory(Module.Category.COMBAT)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = TuxHack.getInstance().settingsManager.getSettingByNameAndMod(configname, mm);
                        ((Setting.Mode) mod).setValue(isOn);
                    }
                }
            }
            br.close();
        } catch (Exception var11) {
            var11.printStackTrace();
        }
    }

    //loads exploit-related configs
    public void loadPLAYER(){
        File file;
        FileInputStream fstream;
        DataInputStream in;
        BufferedReader br;
        String line;
        String curLine;
        String configname;
        String isOn;
        String m;
        Setting mod;
        try {
            file = new File(SaveConfiguration.Player.getAbsolutePath(), "Value.json");
            fstream = new FileInputStream(file.getAbsolutePath());
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));
            while((line = br.readLine()) != null) {
                curLine = line.trim();
                configname = curLine.split(":")[0];
                isOn = curLine.split(":")[1];
                m = curLine.split(":")[2];
                for(Module mm : ModuleManager.getModulesInCategory(Module.Category.PLAYER)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = TuxHack.getInstance().settingsManager.getSettingByNameAndModConfig(configname, mm);

                        if (mod instanceof Setting.Integer) {
                            ((Setting.Integer) mod).setValue(java.lang.Integer.parseInt(isOn));
                        } else if (mod instanceof Setting.Double){
                            ((Setting.Double) mod).setValue(java.lang.Double.parseDouble(isOn));
                        }
                    }
                }
            }
            br.close();
        } catch (Exception var13) {
            var13.printStackTrace();
        }
        try {
            file = new File(SaveConfiguration.Player.getAbsolutePath(), "Boolean.json");
            fstream = new FileInputStream(file.getAbsolutePath());
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));

            while((line = br.readLine()) != null) {
                curLine = line.trim();
                configname = curLine.split(":")[0];
                isOn = curLine.split(":")[1];
                m = curLine.split(":")[2];
                for(Module mm : ModuleManager.getModulesInCategory(Module.Category.PLAYER)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = TuxHack.getInstance().settingsManager.getSettingByNameAndMod(configname, mm);
                        ((Setting.Boolean) mod).setValue(java.lang.Boolean.parseBoolean(isOn));
                    }
                }
            }
            br.close();
        } catch (Exception var12) {
            var12.printStackTrace();
        }
        try {
            file = new File(SaveConfiguration.Player.getAbsolutePath(), "String.json");
            fstream = new FileInputStream(file.getAbsolutePath());
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));
            while((line = br.readLine()) != null) {
                curLine = line.trim();
                configname = curLine.split(":")[0];
                isOn = curLine.split(":")[1];
                m = curLine.split(":")[2];
                for(Module mm : ModuleManager.getModulesInCategory(Module.Category.PLAYER)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = TuxHack.getInstance().settingsManager.getSettingByNameAndMod(configname, mm);
                        ((Setting.Mode) mod).setValue(isOn);
                    }
                }
            }
            br.close();
        } catch (Exception var11) {
            var11.printStackTrace();
        }
    }

    //loads Client-related configs
    public void loadClient(){
        File file;
        FileInputStream fstream;
        DataInputStream in;
        BufferedReader br;
        String line;
        String curLine;
        String configname;
        String isOn;
        String m;
        Setting mod;
        try {
            file = new File(SaveConfiguration.Client.getAbsolutePath(), "Value.json");
            fstream = new FileInputStream(file.getAbsolutePath());
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));
            while((line = br.readLine()) != null) {
                curLine = line.trim();
                configname = curLine.split(":")[0];
                isOn = curLine.split(":")[1];
                m = curLine.split(":")[2];
                for(Module mm : ModuleManager.getModulesInCategory(Module.Category.CLIENT)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = TuxHack.getInstance().settingsManager.getSettingByNameAndModConfig(configname, mm);

                        if (mod instanceof Setting.Integer) {
                            ((Setting.Integer) mod).setValue(java.lang.Integer.parseInt(isOn));
                        } else if (mod instanceof Setting.Double){
                            ((Setting.Double) mod).setValue(java.lang.Double.parseDouble(isOn));
                        }
                    }
                }
            }
            br.close();
        } catch (Exception var13) {
            var13.printStackTrace();
        }
        try {
            file = new File(SaveConfiguration.Client.getAbsolutePath(), "Boolean.json");
            fstream = new FileInputStream(file.getAbsolutePath());
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));

            while((line = br.readLine()) != null) {
                curLine = line.trim();
                configname = curLine.split(":")[0];
                isOn = curLine.split(":")[1];
                m = curLine.split(":")[2];
                for(Module mm : ModuleManager.getModulesInCategory(Module.Category.CLIENT)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = TuxHack.getInstance().settingsManager.getSettingByNameAndMod(configname, mm);
                        ((Setting.Boolean) mod).setValue(java.lang.Boolean.parseBoolean(isOn));
                    }
                }
            }
            br.close();
        } catch (Exception var12) {
            var12.printStackTrace();
        }
        try {
            file = new File(SaveConfiguration.Client.getAbsolutePath(), "String.json");
            fstream = new FileInputStream(file.getAbsolutePath());
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));
            while((line = br.readLine()) != null) {
                curLine = line.trim();
                configname = curLine.split(":")[0];
                isOn = curLine.split(":")[1];
                m = curLine.split(":")[2];
                for(Module mm : ModuleManager.getModulesInCategory(Module.Category.CLIENT)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = TuxHack.getInstance().settingsManager.getSettingByNameAndMod(configname, mm);
                        ((Setting.Mode) mod).setValue(isOn);
                    }
                }
            }
            br.close();
        } catch (Exception var11) {
            var11.printStackTrace();
        }
    }

    //loads misc-related configs
    public void loadMisc(){
        File file;
        FileInputStream fstream;
        DataInputStream in;
        BufferedReader br;
        String line;
        String curLine;
        String configname;
        String isOn;
        String m;
        Setting mod;
        try {
            file = new File(SaveConfiguration.Misc.getAbsolutePath(), "Value.json");
            fstream = new FileInputStream(file.getAbsolutePath());
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));
            while((line = br.readLine()) != null) {
                curLine = line.trim();
                configname = curLine.split(":")[0];
                isOn = curLine.split(":")[1];
                m = curLine.split(":")[2];
                for(Module mm : ModuleManager.getModulesInCategory(Module.Category.MISC)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = TuxHack.getInstance().settingsManager.getSettingByNameAndModConfig(configname, mm);

                        if (mod instanceof Setting.Integer) {
                            ((Setting.Integer) mod).setValue(java.lang.Integer.parseInt(isOn));
                        } else if (mod instanceof Setting.Double){
                            ((Setting.Double) mod).setValue(java.lang.Double.parseDouble(isOn));
                        }
                    }
                }
            }
            br.close();
        } catch (Exception var13) {
            var13.printStackTrace();
        }
        try {
            file = new File(SaveConfiguration.Misc.getAbsolutePath(), "Boolean.json");
            fstream = new FileInputStream(file.getAbsolutePath());
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));

            while((line = br.readLine()) != null) {
                curLine = line.trim();
                configname = curLine.split(":")[0];
                isOn = curLine.split(":")[1];
                m = curLine.split(":")[2];
                for(Module mm : ModuleManager.getModulesInCategory(Module.Category.MISC)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = TuxHack.getInstance().settingsManager.getSettingByNameAndMod(configname, mm);
                        ((Setting.Boolean) mod).setValue(java.lang.Boolean.parseBoolean(isOn));
                    }
                }
            }
            br.close();
        } catch (Exception var12) {
            var12.printStackTrace();
        }
        try {
            file = new File(SaveConfiguration.Misc.getAbsolutePath(), "String.json");
            fstream = new FileInputStream(file.getAbsolutePath());
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));
            while((line = br.readLine()) != null) {
                curLine = line.trim();
                configname = curLine.split(":")[0];
                isOn = curLine.split(":")[1];
                m = curLine.split(":")[2];
                for(Module mm : ModuleManager.getModulesInCategory(Module.Category.MISC)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = TuxHack.getInstance().settingsManager.getSettingByNameAndMod(configname, mm);
                        ((Setting.Mode) mod).setValue(isOn);
                    }
                }
            }
            br.close();
        } catch (Exception var11) {
            var11.printStackTrace();
        }
    }

    //loads movement-related configs
    public void loadMovement(){
        File file;
        FileInputStream fstream;
        DataInputStream in;
        BufferedReader br;
        String line;
        String curLine;
        String configname;
        String isOn;
        String m;
        Setting mod;
        try {
            file = new File(SaveConfiguration.Movement.getAbsolutePath(), "Value.json");
            fstream = new FileInputStream(file.getAbsolutePath());
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));
            while((line = br.readLine()) != null) {
                curLine = line.trim();
                configname = curLine.split(":")[0];
                isOn = curLine.split(":")[1];
                m = curLine.split(":")[2];
                for(Module mm : ModuleManager.getModulesInCategory(Module.Category.MOVEMENT)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = TuxHack.getInstance().settingsManager.getSettingByNameAndModConfig(configname, mm);

                        if (mod instanceof Setting.Integer) {
                            ((Setting.Integer) mod).setValue(java.lang.Integer.parseInt(isOn));
                        } else if (mod instanceof Setting.Double){
                            ((Setting.Double) mod).setValue(java.lang.Double.parseDouble(isOn));
                        }
                    }
                }
            }
            br.close();
        } catch (Exception var13) {
            var13.printStackTrace();
        }
        try {
            file = new File(SaveConfiguration.Movement.getAbsolutePath(), "Boolean.json");
            fstream = new FileInputStream(file.getAbsolutePath());
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));

            while((line = br.readLine()) != null) {
                curLine = line.trim();
                configname = curLine.split(":")[0];
                isOn = curLine.split(":")[1];
                m = curLine.split(":")[2];
                for(Module mm : ModuleManager.getModulesInCategory(Module.Category.MOVEMENT)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = TuxHack.getInstance().settingsManager.getSettingByNameAndMod(configname, mm);
                        ((Setting.Boolean) mod).setValue(java.lang.Boolean.parseBoolean(isOn));
                    }
                }
            }
            br.close();
        } catch (Exception var12) {
            var12.printStackTrace();
        }
        try {
            file = new File(SaveConfiguration.Movement.getAbsolutePath(), "String.json");
            fstream = new FileInputStream(file.getAbsolutePath());
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));
            while((line = br.readLine()) != null) {
                curLine = line.trim();
                configname = curLine.split(":")[0];
                isOn = curLine.split(":")[1];
                m = curLine.split(":")[2];
                for(Module mm : ModuleManager.getModulesInCategory(Module.Category.MOVEMENT)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = TuxHack.getInstance().settingsManager.getSettingByNameAndMod(configname, mm);
                        ((Setting.Mode) mod).setValue(isOn);
                    }
                }
            }
            br.close();
        } catch (Exception var11) {
            var11.printStackTrace();
        }
    }

    //loads render-related configs
    public void loadRender(){
        File file;
        FileInputStream fstream;
        DataInputStream in;
        BufferedReader br;
        String line;
        String curLine;
        String configname;
        String isOn;
        String m;
        Setting mod;
        try {
            file = new File(SaveConfiguration.Render.getAbsolutePath(), "Value.json");
            fstream = new FileInputStream(file.getAbsolutePath());
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));
            while((line = br.readLine()) != null) {
                curLine = line.trim();
                configname = curLine.split(":")[0];
                isOn = curLine.split(":")[1];
                m = curLine.split(":")[2];
                for(Module mm : ModuleManager.getModulesInCategory(Module.Category.RENDER)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = TuxHack.getInstance().settingsManager.getSettingByNameAndModConfig(configname, mm);

                        if (mod instanceof Setting.Integer) {
                            ((Setting.Integer) mod).setValue(java.lang.Integer.parseInt(isOn));
                        } else if (mod instanceof Setting.Double){
                            ((Setting.Double) mod).setValue(java.lang.Double.parseDouble(isOn));
                        }
                    }
                }
            }
            br.close();
        } catch (Exception var13) {
            var13.printStackTrace();
        }
        try {
            file = new File(SaveConfiguration.Render.getAbsolutePath(), "Boolean.json");
            fstream = new FileInputStream(file.getAbsolutePath());
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));

            while((line = br.readLine()) != null) {
                curLine = line.trim();
                configname = curLine.split(":")[0];
                isOn = curLine.split(":")[1];
                m = curLine.split(":")[2];
                for(Module mm : ModuleManager.getModulesInCategory(Module.Category.RENDER)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = TuxHack.getInstance().settingsManager.getSettingByNameAndMod(configname, mm);
                        ((Setting.Boolean) mod).setValue(java.lang.Boolean.parseBoolean(isOn));
                    }
                }
            }
            br.close();
        } catch (Exception var12) {
            var12.printStackTrace();
        }
        try {
            file = new File(SaveConfiguration.Render.getAbsolutePath(), "String.json");
            fstream = new FileInputStream(file.getAbsolutePath());
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));
            while((line = br.readLine()) != null) {
                curLine = line.trim();
                configname = curLine.split(":")[0];
                isOn = curLine.split(":")[1];
                m = curLine.split(":")[2];
                for(Module mm : ModuleManager.getModulesInCategory(Module.Category.RENDER)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = TuxHack.getInstance().settingsManager.getSettingByNameAndMod(configname, mm);
                        ((Setting.Mode) mod).setValue(isOn);
                    }
                }
            }
            br.close();
        } catch (Exception var11) {
            var11.printStackTrace();
        }
    }

    // loads hud modules
    public void loadHud(){
        File file;
        FileInputStream fstream;
        DataInputStream in;
        BufferedReader br;
        String line;
        String curLine;
        String configname;
        String isOn;
        String m;
        Setting mod;
        try {
            file = new File(SaveConfiguration.Hud.getAbsolutePath(), "Value.json");
            fstream = new FileInputStream(file.getAbsolutePath());
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));
            while((line = br.readLine()) != null) {
                curLine = line.trim();
                configname = curLine.split(":")[0];
                isOn = curLine.split(":")[1];
                m = curLine.split(":")[2];
                for(Module mm : ModuleManager.getModulesInCategory(Module.Category.HUD)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = TuxHack.getInstance().settingsManager.getSettingByNameAndModConfig(configname, mm);

                        if (mod instanceof Setting.Integer) {
                            ((Setting.Integer) mod).setValue(java.lang.Integer.parseInt(isOn));
                        } else if (mod instanceof Setting.Double){
                            ((Setting.Double) mod).setValue(java.lang.Double.parseDouble(isOn));
                        }
                    }
                }
            }
            br.close();
        } catch (Exception var13) {
            var13.printStackTrace();
        }
        try {
            file = new File(SaveConfiguration.Hud.getAbsolutePath(), "Boolean.json");
            fstream = new FileInputStream(file.getAbsolutePath());
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));

            while((line = br.readLine()) != null) {
                curLine = line.trim();
                configname = curLine.split(":")[0];
                isOn = curLine.split(":")[1];
                m = curLine.split(":")[2];
                for(Module mm : ModuleManager.getModulesInCategory(Module.Category.HUD)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = TuxHack.getInstance().settingsManager.getSettingByNameAndMod(configname, mm);
                        ((Setting.Boolean) mod).setValue(java.lang.Boolean.parseBoolean(isOn));
                    }
                }
            }
            br.close();
        } catch (Exception var12) {
            var12.printStackTrace();
        }
        try {
            file = new File(SaveConfiguration.Hud.getAbsolutePath(), "String.json");
            fstream = new FileInputStream(file.getAbsolutePath());
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));
            while((line = br.readLine()) != null) {
                curLine = line.trim();
                configname = curLine.split(":")[0];
                isOn = curLine.split(":")[1];
                m = curLine.split(":")[2];
                for(Module mm : ModuleManager.getModulesInCategory(Module.Category.HUD)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = TuxHack.getInstance().settingsManager.getSettingByNameAndMod(configname, mm);
                        ((Setting.Mode) mod).setValue(isOn);
                    }
                }
            }
            br.close();
        } catch (Exception var11) {
            var11.printStackTrace();
        }
    }
}

