package me.ka1.vulcan.module.modules.misc;

import java.util.HashSet;
import java.util.Set;

import me.ka1.vulcan.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.init.SoundEvents;

import static me.ka1.vulcan.command.Command.sendRawMessage;

public class GhastNotifier extends Module {
    private static GhastNotifier instance;
    private Set<Entity> entities = new HashSet<Entity>();

    public GhastNotifier() {
        super("GhastNotifier", "tells you when ghasts spawn for EZ crystals", Category.Misc);
        instance = this;
    }

    public String getDisplayInfo() {
        return null;
    }


    @Override
    public void onEnable() {
        this.entities.clear();
    }

    @Override
    public int onUpdate() {
        for (Entity entity : GhastNotifier.mc.world.loadedEntityList) {
            if (!(entity instanceof EntityGhast) || this.entities.contains(entity)) continue;
            sendRawMessage("Ghast @: " + "X: " + entity.posX + " Y: " + entity.posY + " Z: " + entity.posZ);
            this.entities.add(entity);
            entity.glowing = true;
            GhastNotifier.mc.player.playSound(SoundEvents.ENTITY_ARROW_HIT_PLAYER, 1.0f, 1.0f);
            }
return 0;
        }
    }