package me.tux.tuxhack.module.modules.player;

import me.tux.tuxhack.command.Command;
import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.setting.Setting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class PotionDetect extends Module {
    public PotionDetect() {
        super("PotionDetect", "Notifies you when other players consume a potion", Category.Player);
    }

    /**
     * @author _KA1
     * 3/5/21
     */

    Setting.Boolean str;
    Setting.Boolean spd;
    Setting.Boolean wks;
    Setting.Boolean jmb;

    public boolean setup() {
        str = registerBoolean("Strength", "str", true);
        spd = registerBoolean("Speed", "spd", false);
        wks = registerBoolean("Weakness", "wks", true);
        jmb = registerBoolean("JumpBoost", "jmb", false);
        return false;
    }

    private List<String> strength = new ArrayList<>();
    private List<String> speed = new ArrayList<>();
    private List<String> weakness = new ArrayList<>();
    private List<String> jumpBoost = new ArrayList<>();

    public int onUpdate() {

        mc.world.loadedEntityList.stream()
                .filter(e -> e instanceof EntityPlayer)
                .filter(e -> e != mc.player)
                .forEach(e -> {

                    if (str.getValue() && ((EntityPlayer) e).isPotionActive(MobEffects.STRENGTH) && !this.strength.contains(e.getName())) { //has pot effect and is not in list
                        Command.sendRawMessage(TextFormatting.RED + " " + TextFormatting.BOLD + e.getName() + " has strength!"); // send chat notif
                        strength.add(e.getName()); // add to list
                    } else if (!((EntityPlayer) e).isPotionActive(MobEffects.STRENGTH) && this.strength.contains(e.getName())) {  // does not  have pot effect and is in list
                        strength.remove(e.getName()); // removes from list
                    }

                    if (spd.getValue() && ((EntityPlayer) e).isPotionActive(MobEffects.SPEED) && !speed.contains(e.getName())) {
                        Command.sendRawMessage(TextFormatting.BLUE + e.getName() + " has speed! ");
                        speed.add(e.getName());
                    } else if (!((EntityPlayer) e).isPotionActive(MobEffects.SPEED) && this.speed.contains(e.getName())) {
                        speed.remove(e.getName());
                    }

                    if (wks.getValue() && ((EntityPlayer)  e).isPotionActive(MobEffects.WEAKNESS) && !weakness.contains(e.getName())) {
                        Command.sendRawMessage(TextFormatting.GRAY + e.getName() + " has weakness! ");
                        weakness.add(e.getName());
                    } else if (!((EntityPlayer) e).isPotionActive(MobEffects.WEAKNESS) && this.weakness.contains(e.getName())) {
                        weakness.remove(e.getName());
                    }

                    if (jmb.getValue() && ((EntityPlayer) e).isPotionActive(MobEffects.JUMP_BOOST) && !jumpBoost.contains(e.getName())) {
                        Command.sendRawMessage(TextFormatting.GREEN + e.getName() + " has jump boost! ");
                        jumpBoost.add(e.getName());
                    } else if (!((EntityPlayer) e).isPotionActive(MobEffects.JUMP_BOOST) && this.jumpBoost.contains(e.getName())) {
                        jumpBoost.remove(e.getName());
                    }

                });

        return 0;
    }

}
