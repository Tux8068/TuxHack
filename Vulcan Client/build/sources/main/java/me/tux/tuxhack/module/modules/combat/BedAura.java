package me.tux.tuxhack.module.modules.combat;

        import me.tux.tuxhack.command.Command;
        import me.tux.tuxhack.event.events.DamageBlockEvent;
        import me.tux.tuxhack.module.Module;
        import me.tux.tuxhack.setting.Setting;
        import me.tux.tuxhack.util.friend.Friends;
        import net.minecraft.entity.EntityLivingBase;
        import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
        import net.minecraft.util.math.Vec3d;
        import java.util.List;
        import net.minecraft.util.math.AxisAlignedBB;
        import net.minecraft.init.Blocks;
        import net.minecraft.inventory.ClickType;
        import net.minecraft.init.Items;
        import net.minecraft.client.gui.inventory.GuiContainer;
        import net.minecraft.item.ItemStack;
        import net.minecraft.util.math.Vec3i;
        import net.minecraft.network.play.client.CPacketPlayer;
        import net.minecraft.entity.Entity;
        import net.minecraft.network.play.client.CPacketEntityAction;
        import net.minecraft.item.ItemBed;
        import net.minecraft.client.entity.EntityPlayerSP;
        import net.minecraft.network.Packet;
        import net.minecraft.util.EnumHand;
        import net.minecraft.util.EnumFacing;
        import java.util.Comparator;
        import net.minecraft.tileentity.TileEntityBed;
        import com.mojang.realmsclient.gui.ChatFormatting;
        import net.minecraft.util.text.TextFormatting;
        import net.minecraftforge.common.MinecraftForge;
        import net.minecraft.util.math.BlockPos;
        import net.minecraft.entity.player.EntityPlayer;

        public class BedAura extends Module {
            Setting.Double range;
            Setting.Boolean announceUsage;
            Setting.Double placedelay;
            Setting placeesp;
            private int playerHotbarSlot;
            private int lastHotbarSlot;
            private EntityPlayer closestTarget;
            private String lastTickTargetName;
            private int bedSlot;
            private BlockPos placeTarget;
            private float rotVar;
            private int blocksPlaced;
            private double diffXZ;
            private boolean firstRun;
            private boolean nowTop;

            public BedAura() {
                super("BedAura", Category.Combat);
                this.playerHotbarSlot = -1;
                this.lastHotbarSlot = -1;
                this.bedSlot = -1;
                this.nowTop = false;
            }

            @Override
            public boolean setup() {
                range = registerDouble("Range", "range", 6, 1, 6);
                placedelay = registerDouble("placeDelay", "Delay", 10, 8, 20);
                announceUsage = registerBoolean("announce", "announce", false);

                return false;
            }

            @Override
            public void onEnable() {
                if (this.mc.player == null) {
                    this.toggle();
                    return;
                }
                MinecraftForge.EVENT_BUS.register((Object) this);
                this.firstRun = true;
                this.blocksPlaced = 0;
                this.playerHotbarSlot = this.mc.player.inventory.currentItem;
                this.lastHotbarSlot = -1;
            }

            @Override
            public int onDisable() {
                if (this.mc.player == null) {
                    return 0;
                }
                MinecraftForge.EVENT_BUS.unregister((Object) this);
                if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
                    this.mc.player.inventory.currentItem = this.playerHotbarSlot;
                }
                this.playerHotbarSlot = -1;
                this.lastHotbarSlot = -1;
                if (this.announceUsage.getValue()) {
                    Command.sendRawMessage(TextFormatting.GOLD + "[" + TextFormatting.AQUA + "BedAura" + TextFormatting.GOLD + "]" + ChatFormatting.RED.toString() + " Disabled" + ChatFormatting.RESET.toString() + "!");
                }
                this.blocksPlaced = 0;
                return 0;
            }

            @Override
            public int onUpdate() {
                if (this.mc.player == null) {
                    return 0;
                }
                if (this.mc.player.dimension == 0) {
                    Command.sendRawMessage("You are in the overworld!");
                    this.toggle();
                }
                try {
                    this.findClosestTarget();
                } catch (NullPointerException ex) {
                }
                if (this.closestTarget == null && this.mc.player.dimension != 0 && this.firstRun) {
                    this.firstRun = false;
                    if (this.announceUsage.getValue()) {
                        Command.sendRawMessage(TextFormatting.GOLD + "[" + TextFormatting.AQUA + "BedAura" + TextFormatting.GOLD + "]" + TextFormatting.WHITE + " enabled, " + TextFormatting.WHITE + "waiting for target.");
                    }
                }
                if (this.firstRun && this.closestTarget != null && this.mc.player.dimension != 0) {
                    this.firstRun = false;
                    this.lastTickTargetName = this.closestTarget.getName();
                    if (this.announceUsage.getValue()) {
                        Command.sendRawMessage(TextFormatting.GOLD + "[" + TextFormatting.AQUA + "BedAura" + TextFormatting.GOLD + "]" + TextFormatting.WHITE + " enabled" + TextFormatting.WHITE + ", target: " + ChatFormatting.BLUE.toString() + this.lastTickTargetName);
                    }
                }
                if (this.closestTarget != null && this.lastTickTargetName != null && !this.lastTickTargetName.equals(this.closestTarget.getName())) {
                    this.lastTickTargetName = this.closestTarget.getName();
                    if (this.announceUsage.getValue()) {
                        Command.sendRawMessage(TextFormatting.GOLD + "[" + TextFormatting.AQUA + "BedAura" + TextFormatting.GOLD + "]" + TextFormatting.WHITE + " New target: " + ChatFormatting.BLUE.toString() + this.lastTickTargetName);
                    }
                }
                try {
                    this.diffXZ = this.mc.player.getPositionVector().distanceTo(this.closestTarget.getPositionVector());
                } catch (NullPointerException ex2) {
                }
                try {
                    if (this.closestTarget != null) {
                        this.placeTarget = new BlockPos(this.closestTarget.getPositionVector().add(1.0, 1.0, 0.0));
                        this.nowTop = false;
                        this.rotVar = 90.0f;
                        final BlockPos block1 = this.placeTarget;
                        if (!this.canPlaceBed(block1)) {
                            this.placeTarget = new BlockPos(this.closestTarget.getPositionVector().add(-1.0, 1.0, 0.0));
                            this.rotVar = -90.0f;
                            this.nowTop = false;
                        }
                        final BlockPos block2 = this.placeTarget;
                        if (!this.canPlaceBed(block2)) {
                            this.placeTarget = new BlockPos(this.closestTarget.getPositionVector().add(0.0, 1.0, 1.0));
                            this.rotVar = 180.0f;
                            this.nowTop = false;
                        }
                        final BlockPos block3 = this.placeTarget;
                        if (!this.canPlaceBed(block3)) {
                            this.placeTarget = new BlockPos(this.closestTarget.getPositionVector().add(0.0, 1.0, -1.0));
                            this.rotVar = 0.0f;
                            this.nowTop = false;
                        }
                        final BlockPos block4 = this.placeTarget;
                        if (!this.canPlaceBed(block4)) {
                            this.placeTarget = new BlockPos(this.closestTarget.getPositionVector().add(0.0, 2.0, -1.0));
                            this.rotVar = 0.0f;
                            this.nowTop = true;
                        }
                        final BlockPos blockt1 = this.placeTarget;
                        if (this.nowTop && !this.canPlaceBed(blockt1)) {
                            this.placeTarget = new BlockPos(this.closestTarget.getPositionVector().add(-1.0, 2.0, 0.0));
                            this.rotVar = -90.0f;
                        }
                        final BlockPos blockt2 = this.placeTarget;
                        if (this.nowTop && !this.canPlaceBed(blockt2)) {
                            this.placeTarget = new BlockPos(this.closestTarget.getPositionVector().add(0.0, 2.0, 1.0));
                            this.rotVar = 180.0f;
                        }
                        final BlockPos blockt3 = this.placeTarget;
                        if (this.nowTop && !this.canPlaceBed(blockt3)) {
                            this.placeTarget = new BlockPos(this.closestTarget.getPositionVector().add(1.0, 2.0, 0.0));
                            this.rotVar = 90.0f;
                        }
                    }
                    mc.world.loadedTileEntityList.stream()
                            .filter(e -> e instanceof TileEntityBed)
                            .filter(e -> mc.player.getDistance(e.getPos().getX(), e.getPos().getY(), e.getPos().getZ()) <= range.getValue())
                            .sorted(Comparator.comparing(e -> mc.player.getDistance(e.getPos().getX(), e.getPos().getY(), e.getPos().getZ())))
                            .forEach(bed -> {
                            });


                    if (this.mc.player.ticksExisted % this.placedelay.getValue() == 0 && this.closestTarget != null) {
                        this.findBeds();
                        final EntityPlayerSP player = this.mc.player;
                        ++player.ticksExisted;
                        this.doDaMagic();
                    }
                } catch (NullPointerException npe) {
                    npe.printStackTrace();
                }
                return 0;
            }

            private void doDaMagic() {
                if (this.diffXZ <= this.range.getValue()) {
                    int i = 0;
                    while (i < 9) {
                        if (this.bedSlot != -1) {
                            break;
                        }
                        final ItemStack stack = this.mc.player.inventory.getStackInSlot(i);
                        if (stack.getItem() instanceof ItemBed) {
                            if ((this.bedSlot = i) != -1) {
                                this.mc.player.inventory.currentItem = this.bedSlot;
                                break;
                            }
                            break;
                        } else {
                            ++i;
                        }
                    }
                    this.bedSlot = -1;
                    if (this.blocksPlaced == 0 && this.mc.player.inventory.getStackInSlot(this.mc.player.inventory.currentItem).getItem() instanceof ItemBed) {
                        this.mc.player.connection.sendPacket((Packet) new CPacketEntityAction((Entity) this.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                        this.mc.player.connection.sendPacket((Packet) new CPacketPlayer.Rotation(this.rotVar, 0.0f, this.mc.player.onGround));
                        this.placeBlock(new BlockPos((Vec3i) this.placeTarget), EnumFacing.DOWN);
                        this.mc.player.connection.sendPacket((Packet) new CPacketEntityAction((Entity) this.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                        this.blocksPlaced = 1;
                        DamageBlockEvent bed = null;
                        mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(bed.getPos(), EnumFacing.UP, EnumHand.MAIN_HAND, 0, 0, 0));
                        this.nowTop = false;
                    }
                    this.blocksPlaced = 0;
                }
            }

            private void findBeds() {
                if ((this.mc.currentScreen == null || !(this.mc.currentScreen instanceof GuiContainer)) && this.mc.player.inventory.getStackInSlot(0).getItem() != Items.BED) {
                    for (int i = 9; i < 36; ++i) {
                        if (this.mc.player.inventory.getStackInSlot(i).getItem() == Items.BED) {
                            this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, i, 0, ClickType.SWAP, (EntityPlayer) this.mc.player);
                            break;
                        }
                    }
                }
            }

            private boolean canPlaceBed(final BlockPos pos) {
                return (this.mc.world.getBlockState(pos).getBlock() == Blocks.AIR || this.mc.world.getBlockState(pos).getBlock() == Blocks.BED) && this.mc.world.getEntitiesWithinAABB((Class) Entity.class, new AxisAlignedBB(pos)).isEmpty();
            }

            private void findClosestTarget() {
                final List<EntityPlayer> playerList = (List<EntityPlayer>) this.mc.world.playerEntities;
                this.closestTarget = null;
                for (final EntityPlayer target : playerList) {
                    if (target == this.mc.player) {
                        continue;
                    }
                    if (Friends.isFriend(target.getName())) {
                        continue;
                    }
                    if (!isLiving((Entity) target)) {
                        continue;
                    }
                    if (target.getHealth() <= 0.0f) {
                        continue;
                    }
                    if (this.closestTarget == null) {
                        this.closestTarget = target;
                    } else {
                        if (this.mc.player.getDistance((Entity) target) >= this.mc.player.getDistance((Entity) this.closestTarget)) {
                            continue;
                        }
                        this.closestTarget = target;
                    }
                }
            }

            private void placeBlock(final BlockPos pos, final EnumFacing side) {
                final BlockPos neighbour = pos.offset(side);
                final EnumFacing opposite = side.getOpposite();
                final Vec3d hitVec = new Vec3d((Vec3i) neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
                this.mc.playerController.processRightClickBlock(this.mc.player, this.mc.world, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
            }

            public static boolean isLiving(final Entity e) {
                return e instanceof EntityLivingBase;
            }
        }

