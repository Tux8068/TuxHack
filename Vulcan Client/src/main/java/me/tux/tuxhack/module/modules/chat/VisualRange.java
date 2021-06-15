package me.tux.tuxhack.module.modules.chat;

import me.tux.tuxhack.command.Command;
import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.setting.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VisualRange extends Module {
    public VisualRange() {
        super("VisualRange", "tells you when someone comes close", Category.CHAT);
    }
    List<Entity> knownPlayers = new ArrayList<>();;
    List<Entity> players;
    Setting.Boolean  sound;

    public boolean setup() {
        sound = registerBoolean("ping", "ping", true);
        return false;
    }
    public int onUpdate(){
        if(mc.player == null) return 0;
        players = mc.world.loadedEntityList.stream().filter(e-> e instanceof EntityPlayer).collect(Collectors.toList());
        try {
            for (Entity e : players) {
                if (e instanceof EntityPlayer && !e.getName().equalsIgnoreCase(mc.player.getName())) {
                    if (!knownPlayers.contains(e)) {
                        knownPlayers.add(e);
                        Command.sendClientMessage(TextFormatting.AQUA + e.getName() + " IS in range!");
                       if (sound.getValue() == true);
                        mc.player.playSound(SoundEvents.BLOCK_ANVIL_BREAK, 1.0f, 1.0f);
                    }
                }
            }
        } catch(Exception e){} // ez no crasherino
        try {
            for (Entity e : knownPlayers) {
                if (e instanceof EntityPlayer && !e.getName().equalsIgnoreCase(mc.player.getName())) {
                    if (!players.contains(e)) {
                        knownPlayers.remove(e);;
                    }
                }
            }
        } catch(Exception e){} // ez no crasherino pt.2
        return 0;
    }

    public int onDisable(){
        knownPlayers.clear();
        return 0;
    }
}
