package me.tux.tuxhack.module.modules.misc;

import java.util.HashSet;
import java.util.Set;

import me.tux.tuxhack.command.Command;
import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.setting.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.*;
import net.minecraft.init.SoundEvents;

public class GhastNotifier extends Module {
    private final Set<Entity> ghasts = new HashSet<>();
    private final Set<Entity> donkies = new HashSet<>();
    private final Set<Entity> llamas = new HashSet<>();
    private final Set<Entity> slimes = new HashSet<>();
    private final Set<Entity> endermans = new HashSet<>();
    private final Set<Entity> ocelots = new HashSet<>();
    private final Set<Entity> villagers = new HashSet<>();
    private final Set<Entity> sHorses = new HashSet<>();
    private final Set<Entity> sShulker = new HashSet<>();

    public GhastNotifier() {
        super("MobNotifier", "Messages you coordinates of mobs", Category.MISC);


    }

    public String getDisplayInfo() {
        return null;
    }

    Setting.Boolean Ghast;
    Setting.Boolean Donkey;
    Setting.Boolean Llama;
    Setting.Boolean Slime;
    Setting.Boolean EnderMan;
    Setting.Boolean Ocelot;
    Setting.Boolean Villager;
    Setting.Boolean SkellyHorse;
    Setting.Boolean Shulker;

    public boolean setup() {
        Ghast = registerBoolean("Ghast", "GhastValue", true);
        Donkey = registerBoolean("Donkey", "DonkeyValue", false);
        Llama = registerBoolean("Llama", "LlamaValue", false);
        Slime = registerBoolean("Slime", "SlimeValue", false);
        EnderMan = registerBoolean("EnderMan", "EnderManValue", false);
        Ocelot = registerBoolean("Ocelot", "OcelotValue", false);
        Villager = registerBoolean(" Villager", " VillagerValue", false);
        SkellyHorse = registerBoolean("SkellyHorse", "SkellyHorseValue", false);
        Shulker = registerBoolean("Shulker", "ShulkerValue", false);

        return false;
    }

    @Override
    public void onEnable() {
        this.ghasts.clear();
        this.donkies.clear();
        this.llamas.clear();
        this.slimes.clear();
        this.endermans.clear();
        this.ocelots.clear();
        this.villagers.clear();
        this.sHorses.clear();
        this.sShulker.clear();
    }

    @Override
    public int onUpdate() {
        for (Entity donkeys : mc.world.loadedEntityList) {
            if (Donkey.getValue()) {
                if (!(donkeys instanceof EntityDonkey) || this.donkies.contains(donkeys)) continue;
                Command.sendRawMessage("Donkey @: " + "X: " + Math.round(donkeys.posX) + " Y: " + Math.round(donkeys.posY) + " Z: " + Math.round(donkeys.posZ));
                this.donkies.add(donkeys);
                donkeys.glowing = true;
                mc.player.playSound(SoundEvents.ENTITY_ARROW_HIT_PLAYER, 1.0f, 1.0f);
            }
        }


        for (Entity biqShaqLlamas : mc.world.loadedEntityList) {
            if (Llama.getValue()) {
                if (!(biqShaqLlamas instanceof EntityLlama) || this.llamas.contains(biqShaqLlamas)) continue;
                Command.sendRawMessage("Llama @: " + "X: " + Math.round(biqShaqLlamas.posX) + " Y: " + Math.round(biqShaqLlamas.posY) + " Z: " + Math.round(biqShaqLlamas.posZ));
                this.llamas.add(biqShaqLlamas);
                biqShaqLlamas.glowing = true;
                mc.player.playSound(SoundEvents.ENTITY_ARROW_HIT_PLAYER, 1.0f, 1.0f);
            }
        }

        for (Entity daSlimes : mc.world.loadedEntityList) {
            if (Slime.getValue()) {
                if (!(daSlimes instanceof EntitySlime) || this.slimes.contains(daSlimes)) continue;
                Command.sendRawMessage("Slime @: " + "X: " + Math.round(daSlimes.posX) + " Y: " + Math.round(daSlimes.posY) + " Z: " + Math.round(daSlimes.posZ));
                this.slimes.add(daSlimes);
                daSlimes.glowing = true;
                mc.player.playSound(SoundEvents.ENTITY_ARROW_HIT_PLAYER, 1.0f, 1.0f);
            }
        }


        for (Entity endermens : mc.world.loadedEntityList) {
            if (EnderMan.getValue()) {
                if (!(endermens instanceof EntityEnderman) || this.endermans.contains(endermens)) continue;
                Command.sendRawMessage("EnderMan @: " + "X: " + Math.round(endermens.posX) + " Y: " + Math.round(endermens.posY) + " Z: " + Math.round(endermens.posZ));
                this.endermans.add(endermens);
                endermens.glowing = true;
                mc.player.playSound(SoundEvents.ENTITY_ARROW_HIT_PLAYER, 1.0f, 1.0f);

            }
        }

        for (Entity cats : mc.world.loadedEntityList) {
            if (Ocelot.getValue()) {
                if (!(cats instanceof EntityOcelot) || this.ocelots.contains(cats)) continue;
                Command.sendRawMessage("Ocelot @: " + "X: " + Math.round(cats.posX) + " Y: " + Math.round(cats.posY) + " Z: " + Math.round(cats.posZ));
                this.ocelots.add(cats);
                cats.glowing = true;
                mc.player.playSound(SoundEvents.ENTITY_ARROW_HIT_PLAYER, 1.0f, 1.0f);
            }
        }


        for (Entity villagerinos : mc.world.loadedEntityList) {
            if (Villager.getValue()) {
                if (!(villagerinos instanceof EntityVillager) || this.villagers.contains(villagerinos)) continue;
                Command.sendRawMessage("Villager @: " + "X: " + Math.round(villagerinos.posX) + " Y: " + Math.round(villagerinos.posY) + " Z: " + Math.round(villagerinos.posZ));
                this.villagers.add(villagerinos);
                villagerinos.glowing = true;
                mc.player.playSound(SoundEvents.ENTITY_ARROW_HIT_PLAYER, 1.0f, 1.0f);
            }
        }

        for (Entity horses : mc.world.loadedEntityList) {
            if (SkellyHorse.getValue()) {
                if (!(horses instanceof EntitySkeletonHorse) || this.sHorses.contains(horses))
                    continue;
                Command.sendRawMessage("Skelly Horse @: " + "X: " + Math.round(horses.posX) + " Y: " + Math.round(horses.posY) + " Z: " + Math.round(horses.posZ));
                this.sHorses.add(horses);
                horses.glowing = true;
                mc.player.playSound(SoundEvents.ENTITY_ARROW_HIT_PLAYER, 1.0f, 1.0f);

            }
        }
        for (Entity shulka : mc.world.loadedEntityList) {
            if (Shulker.getValue()) {
                if (!(shulka instanceof EntitySkeletonHorse) || this.sHorses.contains(shulka))
                    continue;
                Command.sendRawMessage("Shulker @: " + "X: " + Math.round(shulka.posX) + " Y: " + Math.round(shulka.posY) + " Z: " + Math.round(shulka.posZ));
                this.sShulker.add(shulka);
                shulka.glowing = true;
                mc.player.playSound(SoundEvents.ENTITY_ARROW_HIT_PLAYER, 1.0f, 1.0f);
            }
        }
        return 0;
    }
}
