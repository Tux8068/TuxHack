package me.tux.tuxhack.module.modules.chat;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.tux.tuxhack.command.Command;
import me.tux.tuxhack.module.Module;
import net.minecraft.init.MobEffects;


public class WeaknessAlert extends Module
{
    private boolean hasAnnounced;

    public WeaknessAlert() {
        super("WeaknessAlert", Category.CHAT);
        this.hasAnnounced = false;
    }

    @Override
    public int onUpdate() {
        if (this.mc.world != null && this.mc.player != null) {
            if (this.mc.player.isPotionActive(MobEffects.WEAKNESS) && !this.hasAnnounced) {
                this.hasAnnounced = true;
                Command.sendRawMessage(ChatFormatting.WHITE + "Yo" + ChatFormatting.GRAY + ", " + ChatFormatting.AQUA + this.mc.getSession().getUsername() + ChatFormatting.GRAY + "," + ChatFormatting.WHITE + " ffs" + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + "you now have " + ChatFormatting.RED + "Weakness");
            }
            if (!this.mc.player.isPotionActive(MobEffects.WEAKNESS) && this.hasAnnounced) {
                this.hasAnnounced = false;
                Command.sendRawMessage(ChatFormatting.WHITE + "nice" + ChatFormatting.GRAY + ", " + ChatFormatting.AQUA + this.mc.getSession().getUsername() + ChatFormatting.GRAY + "," + ChatFormatting.WHITE + " Finally" + ChatFormatting.GRAY + ", " + ChatFormatting.WHITE + "you no longer have " + ChatFormatting.GREEN + "Weakness");
            }
        }
        return 0;
    }
}
