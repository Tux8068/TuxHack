package me.tux.tuxhack.module.modules.combat;

import me.tux.tuxhack.command.Command;
import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.setting.Setting;
import me.tux.tuxhack.util.BurrowUtil;
import me.tux.tuxhack.util.MovementUtils;
import me.tux.tuxhack.util.friend.Friends;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class Burrow extends Module {

   Setting.Boolean rotate;
   Setting.Double offset;
   Setting.Boolean smart;
   Setting.Double smartDistance;

   private BlockPos originalPos;
   private int oldSlot = -1;

   public Burrow() {
      super("Burrow", "Glitches you into a block", Category.COMBAT);
   }

   public boolean setup() {
      rotate = registerBoolean("Rotate", "RotateBurrow", false);
      offset = registerDouble("Offset", "offsetBurrow", 7, -10, 10);
      smart = registerBoolean("Smart", "smartBurrow", false);
      smartDistance = registerDouble("Distance", "distanceBurrow", 2.5, 1, 7);
      return false;
   }

   @Override
   public void onEnable() {
      super.onEnable();

      // Save our original pos
      originalPos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);

      // If we can't place in our actual post then toggle and return
      if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ)).getBlock().equals(Blocks.OBSIDIAN) ||
              intersectsWithEntity(this.originalPos)) {
         toggle();
         return;
      }

      // Save our item slot
      oldSlot = mc.player.inventory.currentItem;
   }

   public int onUpdate() {


      /**
       *  if smart is on check if in hole
       * add obsidian/web mode
       * rewrite some bits?
       */
      if (smart.getValue() && MovementUtils.isInHole(mc.player)) {
         mc.world.loadedEntityList.stream()
                 .filter(e -> e instanceof EntityPlayer)
                 .filter(e -> e != mc.player)
                 .forEach(e -> {
                    if (Friends.isFriend(e.getName()))
                       return;

                    if (e.getDistance(mc.player) + 0.22f <= smartDistance.getValue())
                       doShit();
                 });
             } else
                doShit();
      return 0;
   }

   public void doShit() {

      // If we don't have obsidian in hotbar toggle and return
      if (BurrowUtil.findHotbarBlock(BlockObsidian.class) == -1) {
         Command.sendClientMessage("Can't find obsidian in hotbar!");
         toggle();
         return;
      }

      // Change to obsidian slot
      BurrowUtil.switchToSlot(BurrowUtil.findHotbarBlock(BlockObsidian.class));

      // Fake jump
      mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.41999998688698D, mc.player.posZ, true));
      mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.7531999805211997D, mc.player.posZ, true));
      mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.00133597911214D, mc.player.posZ, true));
      mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.16610926093821D, mc.player.posZ, true));

      // Place block
      BurrowUtil.placeBlock(originalPos, EnumHand.MAIN_HAND, rotate.getValue(), true, false);

      // Rubberband
      mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + offset.getValue(), mc.player.posZ, false));

      // SwitchBack
      BurrowUtil.switchToSlot(oldSlot);

      // AutoDisable
      toggle();
   }


   private boolean intersectsWithEntity(final BlockPos pos) {
      for (final Entity entity : mc.world.loadedEntityList) {
         if (entity.equals(mc.player)) continue;
         if (entity instanceof EntityItem) continue;
         if (new AxisAlignedBB(pos).intersects(entity.getEntityBoundingBox())) return true;
      }
      return false;
   }
}