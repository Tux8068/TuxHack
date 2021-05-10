package me.ka1.vulcan.module.modules.combat;

        import me.ka1.vulcan.command.Command;
        import me.ka1.vulcan.module.Module;
        import me.ka1.vulcan.setting.Setting;
        import me.ka1.vulcan.util.BurrowUtil;
        import me.ka1.vulcan.util.MovementUtils;
        import me.ka1.vulcan.util.friend.Friends;
        import net.minecraft.block.BlockWeb;
        import net.minecraft.entity.Entity;
        import net.minecraft.entity.item.EntityItem;
        import net.minecraft.entity.player.EntityPlayer;
        import net.minecraft.init.Blocks;
        import net.minecraft.util.EnumHand;
        import net.minecraft.util.math.AxisAlignedBB;
        import net.minecraft.util.math.BlockPos;

        public class spiderman extends Module {

        Setting.Boolean rotate;
        Setting.Double offset;
        Setting.Boolean smart;
        Setting.Double smartDistance;

        private BlockPos originalPos;
        private int oldSlot = -1;

        public spiderman() {
        super("Spiderman", "places a web at your feet.", Category.Combat);
        }


            public String getDisplayInfo() {
                return null;
            }

            public void setup() {
        rotate = registerBoolean("Rotate", "Rotateweb", false);
        offset = registerDouble("Offset", "offsetweb", 2, -2, 4);
        smart = registerBoolean("Smart", "smartBurrow", false);
        smartDistance = registerDouble("Distance", "distanceBurrow", 2.5, 1, 7);
        }

        @Override
        public void onEnable() {
        super.onEnable();

        mc.player.sendChatMessage("webbed!");
        // Save our original pos
        originalPos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);

        // If we can't place in our actual post then toggle and return
        if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ)).getBlock().equals(Blocks.WEB) ||
        intersectsWithEntity(this.originalPos)) {
        toggle();
        return;
        }

        // Save our item slot
        oldSlot = mc.player.inventory.currentItem;
        }

        public int onUpdate() {
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

        if (BurrowUtil.findHotbarBlock(BlockWeb.class) == -1) {
        Command.sendClientMessage("NO WEBS DUMBASS");
        toggle();
        return;
        }
        // Change to obsidian slot
        BurrowUtil.switchToSlot(BurrowUtil.findHotbarBlock(BlockWeb.class));

        // Place block
        BurrowUtil.placeBlock(originalPos, EnumHand.MAIN_HAND, rotate.getValue(), true, false);

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