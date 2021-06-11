package me.tux.tuxhack.module.modules.hud;

import me.tux.tuxhack.TuxHack;
import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.setting.Setting;
import me.tux.tuxhack.util.friend.Friends;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;

public class TextRadar extends Module {
    public TextRadar() {
        super("TextRadar", "textRadar", Category.Hud);
    }

    /**
     * @author _KA1
     * 30/3/21
     */

    TextFormatting distance;
    Setting.Boolean friends;
    TextFormatting friend;
    TextFormatting health;
    Setting.Integer range;
    Setting.Integer x;
    Setting.Integer y;
    int playerAmount;
    float enemyHealth;

    public boolean setup() {
        friends = registerBoolean("Friends", "friends", true);
        range = registerInteger("Range", "range", 100, 1, 260);
        x = registerInteger("X", "xtr", 0, 0, 1280);
        y = registerInteger("Y", "ytr", 0, 0, 960);
        return false;
    }

    public void onRender() {
        playerAmount = 0;
        if (mc.player == null || mc.world == null)
            return;

        if (range.getValue() / 16 >= mc.gameSettings.renderDistanceChunks)
            range.setValue(mc.gameSettings.renderDistanceChunks * 16);



        mc.world.loadedEntityList.stream()
                .filter(e -> e instanceof EntityPlayer)
                .filter(e -> e != mc.player)
                .forEach(e -> {

                    if (friends.getValue() == false && (Friends.isFriend(e.getName())))
                        return;

                    if (mc.player.getDistance(e) >= range.getValue())
                        return;

                    if (Friends.isFriend(e.getName()))
                        friend = TextFormatting.GREEN;
                    else if (!Friends.isFriend(e.getName()))
                        friend = TextFormatting.RED;

                    if ((((EntityPlayer) e).getHealth() + ((EntityPlayer) e).getAbsorptionAmount()) <= 5)
                        health = TextFormatting.RED;

                    if ((((EntityPlayer) e).getHealth() + ((EntityPlayer) e).getAbsorptionAmount()) > 5 && (((EntityPlayer) e).getHealth() + ((EntityPlayer) e).getAbsorptionAmount()) < 15)
                        health = TextFormatting.YELLOW;

                    if ((((EntityPlayer) e).getHealth() + ((EntityPlayer) e).getAbsorptionAmount()) >= 15)
                        health = TextFormatting.GREEN;

                    if (mc.player.getDistance(e) < 25)
                        distance = TextFormatting.RED;

                    if (mc.player.getDistance(e) >= 25 && mc.player.getDistance(e) < 50)
                        distance = TextFormatting.YELLOW;

                    if (mc.player.getDistance(e) >= 50)
                        distance = TextFormatting.GREEN;

                    enemyHealth = ((EntityPlayer) e).getHealth() + ((EntityPlayer) e).getAbsorptionAmount();

                    if (x.getValue() > 645) {
                        TuxHack.fontRenderer.drawStringWithShadow(TextFormatting.GRAY + "[" + health + (int) enemyHealth + TextFormatting.GRAY + "] " + friend + e.getName() + TextFormatting.GRAY + " [" + distance + (int) mc.player.getDistance(e) + TextFormatting.GRAY + "]", x.getValue() - getWidth(TextFormatting.GRAY + "[" + health + (int) (((EntityPlayer) e).getHealth() + ((EntityPlayer) e).getAbsorptionAmount()) + TextFormatting.GRAY + "] " + friend + e.getName() + TextFormatting.GRAY + " [" + distance + (int) mc.player.getDistance(e) + TextFormatting.GRAY + "]"), y.getValue() + (playerAmount * 10), 0xffffffff);
                    } else {
                        TuxHack.fontRenderer.drawStringWithShadow(TextFormatting.GRAY + "[" + health + (int) enemyHealth + TextFormatting.GRAY + "] " + friend + e.getName() + TextFormatting.GRAY + " [" + distance + (int) mc.player.getDistance(e) + TextFormatting.GRAY + "]", x.getValue(), y.getValue() + (playerAmount * 10), 0xffffffff);
                    }
                    playerAmount++;
                });
    }

    private int getWidth(String s) {
        return TuxHack.fontRenderer.getStringWidth(s);
    }
}