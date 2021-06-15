package me.tux.tuxhack.module.modules.movement;

import me.tux.tuxhack.event.events.PacketSendEvent;
import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.setting.Setting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import java.util.Comparator;
import net.minecraft.world.chunk.EmptyChunk;
import net.minecraft.item.ItemBoat;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.server.SPacketMoveVehicle;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraft.entity.item.EntityBoat;

public class BoatFly extends Module {
    public BoatFly() {
        super("BoatFly", "Fly with boats!", Category.MOVEMENT);
    }


    Setting.Boolean Bypass;
    Setting.Boolean NoGravity;
    Setting.Boolean FixYaw;
    Setting.Boolean PlaceBypass;
    Setting.Integer Speed;
    Setting.Integer UpSpeed;
    private int packetCounter;
    private boolean stopFlying;

    public boolean setup() {


        Bypass = registerBoolean("Bypass","Bypass",true);
        NoGravity = registerBoolean("NoGravity","Nogravity", false);
        FixYaw = registerBoolean("FixYaw","FixYaw",true);
        PlaceBypass = registerBoolean("PlaceBypass","PlaceBypass",false);
        Speed = registerInteger("Speed","Speed",10,1,50);
        UpSpeed = registerInteger("UpSpeed","upspeed",1,1,10);

        this.packetCounter = 0;
        this.stopFlying = false;

        return false;
    }

    @Override
    public int onDisable() {
        super.onDisable();
        if (this.mc.player.getRidingEntity() instanceof EntityBoat) {
            this.mc.player.getRidingEntity().setNoGravity(false);
        }
        return 0;
    }

    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (this.mc.world != null && this.mc.player.getRidingEntity() != null) {
            final Entity riding = this.mc.player.getRidingEntity();
            if (riding instanceof EntityBoat) {
                this.steerBoat(riding);
            }
        }
    }

    @SubscribeEvent
    public void onSendPacket(final PacketSendEvent p_Event) {
        if (this.mc.world != null && this.mc.player != null) {
            if (this.mc.player.getRidingEntity() instanceof EntityBoat) {
                if (this.Bypass.getValue() && p_Event.getPacket() instanceof CPacketInput && !this.mc.gameSettings.keyBindSneak.isKeyDown() && !this.mc.player.getRidingEntity().onGround) {
                    ++this.packetCounter;
                    if (this.packetCounter == 3) {
                        this.NCPPacketTrick();
                    }
                }
                if ((this.Bypass.getValue() && p_Event.getPacket() instanceof SPacketPlayerPosLook) || (p_Event.getPacket() instanceof SPacketMoveVehicle && !this.stopFlying)) {
                    p_Event.setCanceled(true);
                }
            }
            if ((this.PlaceBypass.getValue() && p_Event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock && this.mc.player.getHeldItemMainhand().getItem() instanceof ItemBoat) || this.mc.player.getHeldItemOffhand().getItem() instanceof ItemBoat) {
                p_Event.setCanceled(true);
            }
        }
    }

    private void steerBoat(final Entity boat) {
        if (this.FixYaw.getValue()) {
            boat.rotationYaw = this.mc.player.rotationYaw;
            boat.rotationPitch = this.mc.player.rotationPitch;
        }
        boat.setNoGravity(this.NoGravity.getValue());
        int angle = 0;
        final boolean forward = this.mc.gameSettings.keyBindForward.isKeyDown();
        final boolean left = this.mc.gameSettings.keyBindLeft.isKeyDown();
        final boolean right = this.mc.gameSettings.keyBindRight.isKeyDown();
        final boolean back = this.mc.gameSettings.keyBindBack.isKeyDown();
        if (!forward || !back) {
            boat.motionY = 0.0;
        }
        if (this.mc.gameSettings.keyBindJump.isKeyDown()) {
            boat.motionY += this.UpSpeed.getValue() / 2.0f;
        }
        if (this.mc.gameSettings.keyBindSprint.isKeyDown()) {
            boat.motionY -= this.UpSpeed.getValue() / 2.0f;
        }
        if (!forward && !left && !right && !back) {
            return;
        }
        if (left && right) {
            if (forward) {
                angle = 0;
            }
            else if (back) {
                angle = 180;
            }
        }
        else if (forward && back) {
            if (left) {
                angle = -90;
            }
            else if (right) {
                angle = 90;
            }
            else {
                angle = -1;
            }
        }
        else if (left) {
            angle = -90;
        }
        else if (right) {
            angle = 90;
        }
        else {
            angle = 0;
        }
        if (forward) {
            angle /= 2;
        }
        else if (back) {
            angle = 180 - angle / 2;
        }
        if (angle == -1) {
            return;
        }
        if (this.isBorderingChunk(boat, boat.motionX, boat.motionZ)) {
            this.stopFlying = true;
        }
        else {
            this.stopFlying = false;
        }
        final float yaw = this.mc.player.rotationYaw + angle;
        boat.motionX = this.getRelativeX(yaw) * this.Speed.getValue();
        boat.motionZ = this.getRelativeZ(yaw) * this.Speed.getValue();
    }

    private double getRelativeX(final float yaw) {
        return Math.sin(Math.toRadians(-yaw));
    }

    private double getRelativeZ(final float yaw) {
        return Math.cos(Math.toRadians(yaw));
    }

    private boolean isBorderingChunk(final Entity boat, final Double motX, final Double motZ) {
        return this.mc.world.getChunk((int)(boat.posX + motX) / 16, (int)(boat.posZ + motZ) / 16) instanceof EmptyChunk;
    }

    private void NCPPacketTrick() {
        this.packetCounter = 0;
        this.mc.player.getRidingEntity().dismountRidingEntity();
        final Entity l_Entity = (Entity)this.mc.world.loadedEntityList.stream().filter(p_Entity -> p_Entity instanceof EntityBoat).min(Comparator.comparing(p_Entity -> this.mc.player.getDistance(p_Entity))).orElse(null);
        if (l_Entity != null) {
            this.mc.playerController.interactWithEntity((EntityPlayer)this.mc.player, l_Entity, EnumHand.MAIN_HAND);
        }
    }
}

