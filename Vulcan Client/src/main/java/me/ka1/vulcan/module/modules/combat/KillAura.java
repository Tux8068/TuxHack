package me.ka1.vulcan.module.modules.combat;

import me.ka1.vulcan.Vulcan;
import me.ka1.vulcan.event.events.PacketEvent;
import me.ka1.vulcan.module.Module;
import me.ka1.vulcan.module.ModuleManager;
import me.ka1.vulcan.setting.Setting;
import me.ka1.vulcan.util.friend.Friends;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class KillAura extends Module {
    public KillAura(){
        super("KillAura", "Automatically smacks people", Category.Combat);
    }

    private Setting.Boolean swordOnly;
    private Setting.Boolean caCheck;
    private Setting.Boolean criticals;
    private Setting.Double range;

    private String targetName;

    public void setup(){
        range = registerDouble("Range", "Range", 5,0,10);
        swordOnly = registerBoolean("Sword Only", "SwordOnly",true);
        criticals = registerBoolean("Criticals", "Criticals",true);
        caCheck = registerBoolean("AC Check", "ACCheck",false);
    }

    private boolean isAttacking = false;

    public int onUpdate(){
        if(mc.player == null || mc.player.isDead) return 0;
        List<Entity> targets = mc.world.loadedEntityList.stream()
                .filter(entity -> entity != mc.player)
                .filter(entity -> mc.player.getDistance(entity) <= range.getValue())
                .filter(entity -> !entity.isDead)
                .filter(entity -> entity instanceof EntityPlayer)
                .filter(entity -> ((EntityPlayer) entity).getHealth() > 0)
                .filter(entity -> !Friends.isFriend(entity.getName()))
                .sorted(Comparator.comparing(e->mc.player.getDistance(e)))
                .collect(Collectors.toList());


        targets.forEach(target ->{
            if(swordOnly.getValue())
                if(!(mc.player.getHeldItemMainhand().getItem() instanceof ItemSword)) return;

            if(caCheck.getValue())
                if(((AutoCrystal) ModuleManager.getModuleByName("AutoCrystal")).isActive) return;

               if (target.getDistance(mc.player) < range.getValue() && target != null) {
                   targetName = target.getName();
               } else if (target == null || target.getDistance(mc.player) > range.getValue()) {
                   targetName = "No target!";
               }
               attack(target);

        });

        return 0;
    }

    @EventHandler
    private final Listener<PacketEvent.Send> listener = new Listener<>(event -> {
        if (event.getPacket() instanceof CPacketUseEntity) {
            if(criticals.getValue() && ((CPacketUseEntity) event.getPacket()).getAction() == CPacketUseEntity.Action.ATTACK && mc.player.onGround && isAttacking) {
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.1f, mc.player.posZ, false));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
            }
        }
    });

    public void onEnable(){
        Vulcan.EVENT_BUS.subscribe(this);
    }

    public void onDisable(){
        Vulcan.EVENT_BUS.unsubscribe(this);
    }

    public String  getHudInfo() {
        String s = TextFormatting.GRAY + "[" + TextFormatting.WHITE + targetName + TextFormatting.GRAY + "]";
        return s;
    }

    public void attack(Entity e){
        if(mc.player.getCooledAttackStrength(0) >= 1) {
            isAttacking = true;
            mc.playerController.attackEntity(mc.player, e);
            mc.player.swingArm(EnumHand.MAIN_HAND);
            isAttacking = false;
        }
    }
}
