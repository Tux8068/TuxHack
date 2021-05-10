package me.ka1.vulcan.util.config;

import me.ka1.vulcan.Vulcan;
import me.ka1.vulcan.module.Module;
import me.ka1.vulcan.module.ModuleManager;
import me.ka1.vulcan.setting.Setting;

import java.io.*;

/**
 * Made by Hoosiers on 08/02/20 for GameSense, some functions were modified and ported from the original ConfigUtils
 */

public class LoadModules {

    //loads all functions for modules
    public LoadModules(){
        loadHud();
        loadCombat();
        loadPlayer();
        loadClient();
        loadMisc();
        loadMovement();
        loadRender();
    }

    //loads combat-related configs
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
                for(Module mm : ModuleManager.getModulesInCategory(Module.Category.Combat)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = Vulcan.getInstance().settingsManager.getSettingByNameAndModConfig(configname, mm);

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
                for(Module mm : ModuleManager.getModulesInCategory(Module.Category.Combat)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = Vulcan.getInstance().settingsManager.getSettingByNameAndMod(configname, mm);
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
                for(Module mm : ModuleManager.getModulesInCategory(Module.Category.Combat)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = Vulcan.getInstance().settingsManager.getSettingByNameAndMod(configname, mm);
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
    public void loadPlayer(){
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
                for(Module mm : ModuleManager.getModulesInCategory(Module.Category.Player)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = Vulcan.getInstance().settingsManager.getSettingByNameAndModConfig(configname, mm);

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
                for(Module mm : ModuleManager.getModulesInCategory(Module.Category.Player)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = Vulcan.getInstance().settingsManager.getSettingByNameAndMod(configname, mm);
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
                for(Module mm : ModuleManager.getModulesInCategory(Module.Category.Player)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = Vulcan.getInstance().settingsManager.getSettingByNameAndMod(configname, mm);
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
                for(Module mm : ModuleManager.getModulesInCategory(Module.Category.Client)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = Vulcan.getInstance().settingsManager.getSettingByNameAndModConfig(configname, mm);

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
                for(Module mm : ModuleManager.getModulesInCategory(Module.Category.Client)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = Vulcan.getInstance().settingsManager.getSettingByNameAndMod(configname, mm);
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
                for(Module mm : ModuleManager.getModulesInCategory(Module.Category.Client)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = Vulcan.getInstance().settingsManager.getSettingByNameAndMod(configname, mm);
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
                for(Module mm : ModuleManager.getModulesInCategory(Module.Category.Misc)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = Vulcan.getInstance().settingsManager.getSettingByNameAndModConfig(configname, mm);

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
                for(Module mm : ModuleManager.getModulesInCategory(Module.Category.Misc)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = Vulcan.getInstance().settingsManager.getSettingByNameAndMod(configname, mm);
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
                for(Module mm : ModuleManager.getModulesInCategory(Module.Category.Misc)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = Vulcan.getInstance().settingsManager.getSettingByNameAndMod(configname, mm);
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
                for(Module mm : ModuleManager.getModulesInCategory(Module.Category.Movement)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = Vulcan.getInstance().settingsManager.getSettingByNameAndModConfig(configname, mm);

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
                for(Module mm : ModuleManager.getModulesInCategory(Module.Category.Movement)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = Vulcan.getInstance().settingsManager.getSettingByNameAndMod(configname, mm);
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
                for(Module mm : ModuleManager.getModulesInCategory(Module.Category.Movement)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = Vulcan.getInstance().settingsManager.getSettingByNameAndMod(configname, mm);
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
                for(Module mm : ModuleManager.getModulesInCategory(Module.Category.Render)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = Vulcan.getInstance().settingsManager.getSettingByNameAndModConfig(configname, mm);

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
                for(Module mm : ModuleManager.getModulesInCategory(Module.Category.Render)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = Vulcan.getInstance().settingsManager.getSettingByNameAndMod(configname, mm);
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
                for(Module mm : ModuleManager.getModulesInCategory(Module.Category.Render)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = Vulcan.getInstance().settingsManager.getSettingByNameAndMod(configname, mm);
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
                for(Module mm : ModuleManager.getModulesInCategory(Module.Category.Hud)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = Vulcan.getInstance().settingsManager.getSettingByNameAndModConfig(configname, mm);

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
                for(Module mm : ModuleManager.getModulesInCategory(Module.Category.Hud)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = Vulcan.getInstance().settingsManager.getSettingByNameAndMod(configname, mm);
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
                for(Module mm : ModuleManager.getModulesInCategory(Module.Category.Hud)) {
                    if (mm != null && mm.getName().equalsIgnoreCase(m)) {
                        mod = Vulcan.getInstance().settingsManager.getSettingByNameAndMod(configname, mm);
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

