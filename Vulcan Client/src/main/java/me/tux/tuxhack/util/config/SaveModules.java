package me.tux.tuxhack.util.config;

import me.tux.tuxhack.TuxHack;
import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.setting.Setting;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;

public class SaveModules {

    //saves module settings for each category
    public void saveModules(){
        saveHud();
        saveCombat();
        savePlayer();
        saveClient();
        saveMisc();
        saveMovement();
        saveRender();
        saveWorld();
    }

    //saves combat-related modules
    public void saveCombat(){
        File file;
        BufferedWriter out;
        Iterator var3;
        Setting i;
        try {
            file = new File(SaveConfiguration.Combat.getAbsolutePath(), "Value.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = TuxHack.getInstance().settingsManager.getSettingsByCategory(Module.Category.COMBAT).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.DOUBLE) {
                    out.write(i.getConfigName() + ":" +((Setting.Double) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
                if (i.getType() == Setting.Type.INT) {
                    out.write(i.getConfigName() + ":" +((Setting.Integer) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var7) {
        }
        try {
            file = new File(SaveConfiguration.Combat.getAbsolutePath(), "Boolean.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = TuxHack.getInstance().settingsManager.getSettingsByCategory(Module.Category.COMBAT).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.BOOLEAN) {
                    out.write(i.getConfigName() + ":" + ((Setting.Boolean) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var6) {
        }
        try {
            file = new File(SaveConfiguration.Combat.getAbsolutePath(), "String.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = TuxHack.getInstance().settingsManager.getSettingsByCategory(Module.Category.COMBAT).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.MODE) {
                    out.write(i.getConfigName() + ":" + ((Setting.Mode) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var5) {
        }
    }

    //saves exploit-related modules
    public void savePlayer(){
        File file;
        BufferedWriter out;
        Iterator var3;
        Setting i;
        try {
            file = new File(SaveConfiguration.Player.getAbsolutePath(), "Value.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = TuxHack.getInstance().settingsManager.getSettingsByCategory(Module.Category.PLAYER).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.DOUBLE) {
                    out.write(i.getConfigName() + ":" +((Setting.Double) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
                if (i.getType() == Setting.Type.INT) {
                    out.write(i.getConfigName() + ":" +((Setting.Integer) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var7) {
        }
        try {
            file = new File(SaveConfiguration.Player.getAbsolutePath(), "Boolean.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = TuxHack.getInstance().settingsManager.getSettingsByCategory(Module.Category.PLAYER).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.BOOLEAN) {
                    out.write(i.getConfigName() + ":" + ((Setting.Boolean) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var6) {
        }
        try {
            file = new File(SaveConfiguration.Player.getAbsolutePath(), "String.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = TuxHack.getInstance().settingsManager.getSettingsByCategory(Module.Category.PLAYER).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.MODE) {
                    out.write(i.getConfigName() + ":" + ((Setting.Mode) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var5) {
        }
    }

    //saves Client-related modules
    public void saveClient(){
        File file;
        BufferedWriter out;
        Iterator var3;
        Setting i;
        try {
            file = new File(SaveConfiguration.Client.getAbsolutePath(), "Value.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = TuxHack.getInstance().settingsManager.getSettingsByCategory(Module.Category.CLIENT).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.DOUBLE) {
                    out.write(i.getConfigName() + ":" +((Setting.Double) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
                if (i.getType() == Setting.Type.INT) {
                    out.write(i.getConfigName() + ":" +((Setting.Integer) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var7) {
        }
        try {
            file = new File(SaveConfiguration.Client.getAbsolutePath(), "Boolean.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = TuxHack.getInstance().settingsManager.getSettingsByCategory(Module.Category.CLIENT).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.BOOLEAN) {
                    out.write(i.getConfigName() + ":" + ((Setting.Boolean) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var6) {
        }
        try {
            file = new File(SaveConfiguration.Client.getAbsolutePath(), "String.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = TuxHack.getInstance().settingsManager.getSettingsByCategory(Module.Category.CLIENT).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.MODE) {
                    out.write(i.getConfigName() + ":" + ((Setting.Mode) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var5) {
        }
    }

    //saves misc-related modules
    public void saveMisc(){
        File file;
        BufferedWriter out;
        Iterator var3;
        Setting i;
        try {
            file = new File(SaveConfiguration.Misc.getAbsolutePath(), "Value.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = TuxHack.getInstance().settingsManager.getSettingsByCategory(Module.Category.MISC).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.DOUBLE) {
                    out.write(i.getConfigName() + ":" +((Setting.Double) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
                if (i.getType() == Setting.Type.INT) {
                    out.write(i.getConfigName() + ":" +((Setting.Integer) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var7) {
        }
        try {
            file = new File(SaveConfiguration.Misc.getAbsolutePath(), "Boolean.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = TuxHack.getInstance().settingsManager.getSettingsByCategory(Module.Category.MISC).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.BOOLEAN) {
                    out.write(i.getConfigName() + ":" + ((Setting.Boolean) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var6) {
        }
        try {
            file = new File(SaveConfiguration.Misc.getAbsolutePath(), "String.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = TuxHack.getInstance().settingsManager.getSettingsByCategory(Module.Category.MISC).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.MODE) {
                    out.write(i.getConfigName() + ":" + ((Setting.Mode) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var5) {
        }
    }

    //saves movement-related modules
    public void saveMovement(){
        File file;
        BufferedWriter out;
        Iterator var3;
        Setting i;
        try {
            file = new File(SaveConfiguration.Movement.getAbsolutePath(), "Value.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = TuxHack.getInstance().settingsManager.getSettingsByCategory(Module.Category.MOVEMENT).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.DOUBLE) {
                    out.write(i.getConfigName() + ":" +((Setting.Double) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
                if (i.getType() == Setting.Type.INT) {
                    out.write(i.getConfigName() + ":" +((Setting.Integer) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var7) {
        }
        try {
            file = new File(SaveConfiguration.Movement.getAbsolutePath(), "Boolean.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = TuxHack.getInstance().settingsManager.getSettingsByCategory(Module.Category.MOVEMENT).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.BOOLEAN) {
                    out.write(i.getConfigName() + ":" + ((Setting.Boolean) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var6) {
        }
        try {
            file = new File(SaveConfiguration.Movement.getAbsolutePath(), "String.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = TuxHack.getInstance().settingsManager.getSettingsByCategory(Module.Category.MOVEMENT).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.MODE) {
                    out.write(i.getConfigName() + ":" + ((Setting.Mode) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var5) {
        }
    }

    //saves render-related modules
    public void saveRender(){
        File file;
        BufferedWriter out;
        Iterator var3;
        Setting i;
        try {
            file = new File(SaveConfiguration.Render.getAbsolutePath(), "Value.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = TuxHack.getInstance().settingsManager.getSettingsByCategory(Module.Category.RENDER).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.DOUBLE) {
                    out.write(i.getConfigName() + ":" +((Setting.Double) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
                if (i.getType() == Setting.Type.INT) {
                    out.write(i.getConfigName() + ":" +((Setting.Integer) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var7) {
        }
        try {
            file = new File(SaveConfiguration.Render.getAbsolutePath(), "Boolean.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = TuxHack.getInstance().settingsManager.getSettingsByCategory(Module.Category.RENDER).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.BOOLEAN) {
                    out.write(i.getConfigName() + ":" + ((Setting.Boolean) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var6) {
        }
        try {
            file = new File(SaveConfiguration.Render.getAbsolutePath(), "String.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = TuxHack.getInstance().settingsManager.getSettingsByCategory(Module.Category.RENDER).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.MODE) {
                    out.write(i.getConfigName() + ":" + ((Setting.Mode) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var5) {
        }
    }

    //saves Client-related modules
    public void saveHud(){
        File file;
        BufferedWriter out;
        Iterator var3;
        Setting i;
        try {
            file = new File(SaveConfiguration.Hud.getAbsolutePath(), "Value.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = TuxHack.getInstance().settingsManager.getSettingsByCategory(Module.Category.HUD).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.DOUBLE) {
                    out.write(i.getConfigName() + ":" +((Setting.Double) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
                if (i.getType() == Setting.Type.INT) {
                    out.write(i.getConfigName() + ":" +((Setting.Integer) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var7) {
        }
        try {
            file = new File(SaveConfiguration.Hud.getAbsolutePath(), "Boolean.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = TuxHack.getInstance().settingsManager.getSettingsByCategory(Module.Category.HUD).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.BOOLEAN) {
                    out.write(i.getConfigName() + ":" + ((Setting.Boolean) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var6) {
        }
        try {
            file = new File(SaveConfiguration.Hud.getAbsolutePath(), "String.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = TuxHack.getInstance().settingsManager.getSettingsByCategory(Module.Category.HUD).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.MODE) {
                    out.write(i.getConfigName() + ":" + ((Setting.Mode) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var5) {
        }
    }
    public void saveWorld(){
        File file;
        BufferedWriter out;
        Iterator var3;
        Setting i;
        try {
            file = new File(SaveConfiguration.Hud.getAbsolutePath(), "Value.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = TuxHack.getInstance().settingsManager.getSettingsByCategory(Module.Category.HUD).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.DOUBLE) {
                    out.write(i.getConfigName() + ":" +((Setting.Double) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
                if (i.getType() == Setting.Type.INT) {
                    out.write(i.getConfigName() + ":" +((Setting.Integer) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var7) {
        }
        try {
            file = new File(SaveConfiguration.Hud.getAbsolutePath(), "Boolean.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = TuxHack.getInstance().settingsManager.getSettingsByCategory(Module.Category.WORLD).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.BOOLEAN) {
                    out.write(i.getConfigName() + ":" + ((Setting.Boolean) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var6) {
        }
        try {
            file = new File(SaveConfiguration.Hud.getAbsolutePath(), "String.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = TuxHack.getInstance().settingsManager.getSettingsByCategory(Module.Category.WORLD).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.MODE) {
                    out.write(i.getConfigName() + ":" + ((Setting.Mode) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var5) {
        }
    }

}
