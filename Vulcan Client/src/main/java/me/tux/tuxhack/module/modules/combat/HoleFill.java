package me.tux.tuxhack.module.modules.combat;

import me.tux.tuxhack.command.Command;
import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.setting.Setting;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.util.math.MathHelper;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumHand;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.block.material.Material;
import java.util.Arrays;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import java.util.List;
import net.minecraft.util.math.BlockPos;
import java.util.ArrayList;

public class HoleFill extends Module {
    private ArrayList<BlockPos> holes;
    private final List<Block> obbyonly;
    private final List<Block> bothonly;
    private final List<Block> echestonly;
    private final List<Block> webonly;
    private List<Block> list;
    Setting.Double range;
    Setting.Integer yRange;
    Setting.Integer waitTick;
    Setting.Boolean chat;
    Setting.Boolean rotate;
    Setting.Boolean Toggle;
    Setting.Mode type;
    BlockPos pos;
    private int waitCounter;

    public HoleFill() {
        super("HoleFill", Category.COMBAT);
        this.holes = new ArrayList<BlockPos>();
        this.obbyonly = Arrays.asList(Blocks.OBSIDIAN);
        this.bothonly = Arrays.asList(Blocks.OBSIDIAN, Blocks.ENDER_CHEST);
        this.echestonly = Arrays.asList(Blocks.ENDER_CHEST);
        this.webonly = Arrays.asList(Blocks.WEB);
        this.list = Arrays.asList(new Block[0]);
    }

    @Override
    public boolean setup() {
        final ArrayList<String> blockmode = new ArrayList<String>();
        blockmode.add("Obby");
        blockmode.add("EChest");
        blockmode.add("Both");
        blockmode.add("Web");
        this.type = this.registerMode("Block", "BlockMode", blockmode, "Obby");
        this.range = this.registerDouble("Place Range", "PlaceRange", 5.0, 0.0, 10.0);
        this.yRange = this.registerInteger("Y Range", "YRange", 2, 0, 10);
        this.waitTick = this.registerInteger("Tick Delay", "TickDelay", 1, 0, 20);
        this.rotate = this.registerBoolean("Rotate", "Rotate", false);
        this.chat = this.registerBoolean("Toggle Msg", "ToggleMsg", false);
        this.Toggle = this.registerBoolean("Toggle", "Toggle", true);
        return false;
    }

    @Override
    public int onUpdate() {
        this.holes = new ArrayList<BlockPos>();
        if (this.type.getValue().equalsIgnoreCase("Obby")) {
            this.list = this.obbyonly;
        }
        if (this.type.getValue().equalsIgnoreCase("EChest")) {
            this.list = this.echestonly;
        }
        if (this.type.getValue().equalsIgnoreCase("Both")) {
            this.list = this.bothonly;
        }
        if (this.type.getValue().equalsIgnoreCase("Web")) {
            this.list = this.webonly;
        }
        final Iterable<BlockPos> blocks = (Iterable<BlockPos>) BlockPos.getAllInBox(HoleFill.mc.player.getPosition().add(-this.range.getValue(), (double) (-this.yRange.getValue()), -this.range.getValue()), HoleFill.mc.player.getPosition().add(this.range.getValue(), (double) this.yRange.getValue(), this.range.getValue()));
        for (final BlockPos pos : blocks) {
            if (!HoleFill.mc.world.getBlockState(pos).getMaterial().blocksMovement() && !HoleFill.mc.world.getBlockState(pos.add(0, 1, 0)).getMaterial().blocksMovement()) {
                final boolean solidNeighbours = (HoleFill.mc.world.getBlockState(pos.add(1, 0, 0)).getBlock() == Blocks.BEDROCK | HoleFill.mc.world.getBlockState(pos.add(1, 0, 0)).getBlock() == Blocks.OBSIDIAN) && (HoleFill.mc.world.getBlockState(pos.add(0, 0, 1)).getBlock() == Blocks.BEDROCK | HoleFill.mc.world.getBlockState(pos.add(0, 0, 1)).getBlock() == Blocks.OBSIDIAN) && (HoleFill.mc.world.getBlockState(pos.add(-1, 0, 0)).getBlock() == Blocks.BEDROCK | HoleFill.mc.world.getBlockState(pos.add(-1, 0, 0)).getBlock() == Blocks.OBSIDIAN) && (HoleFill.mc.world.getBlockState(pos.add(0, 0, -1)).getBlock() == Blocks.BEDROCK | HoleFill.mc.world.getBlockState(pos.add(0, 0, -1)).getBlock() == Blocks.OBSIDIAN) && HoleFill.mc.world.getBlockState(pos.add(0, 0, 0)).getMaterial() == Material.AIR && HoleFill.mc.world.getBlockState(pos.add(0, 1, 0)).getMaterial() == Material.AIR && HoleFill.mc.world.getBlockState(pos.add(0, 2, 0)).getMaterial() == Material.AIR;
                if (!solidNeighbours) {
                    continue;
                }
                this.holes.add(pos);
            }
        }
        int newSlot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = HoleFill.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                if (stack.getItem() instanceof ItemBlock) {
                    final Block block = ((ItemBlock) stack.getItem()).getBlock();
                    if (this.list.contains(block)) {
                        newSlot = i;
                        break;
                    }
                }
            }
        }
        if (newSlot == -1) {
            return newSlot;
        }
        final int oldSlot = HoleFill.mc.player.inventory.currentItem;
        if (this.waitTick.getValue() > 0) {
            if (this.waitCounter < this.waitTick.getValue()) {
                HoleFill.mc.player.inventory.currentItem = newSlot;
                this.holes.forEach(this::place);
                HoleFill.mc.player.inventory.currentItem = oldSlot;
                return newSlot;
            }
            this.waitCounter = 0;
        }
        this.disable();
        return newSlot;
    }

    public void onEnable() {
        if (HoleFill.mc.player != null && this.chat.getValue()) {
            Command.sendRawMessage("§aHolefill turned ON!");
        }
    }

    public int onDisable() {
        if (HoleFill.mc.player != null && this.chat.getValue()) {
            Command.sendRawMessage("§cHolefill turned OFF!");
        }
        return 0;
    }

    private void place(final BlockPos blockPos) {
        for (final Entity entity : HoleFill.mc.world.getEntitiesWithinAABBExcludingEntity((Entity) null, new AxisAlignedBB(blockPos))) {
            if (entity instanceof EntityLivingBase) {
                return;
            }
        }
        placeBlockScaffold(blockPos, this.rotate.getValue());
        ++this.waitCounter;
    }

    public static boolean placeBlockScaffold(final BlockPos pos, final boolean rotate) {
        final Vec3d eyesPos = new Vec3d(HoleFill.mc.player.posX, HoleFill.mc.player.posY + HoleFill.mc.player.getEyeHeight(), HoleFill.mc.player.posZ);
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = pos.offset(side);
            final EnumFacing side2 = side.getOpposite();
            if (canBeClicked(neighbor)) {
                final Vec3d hitVec = new Vec3d((Vec3i) neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
                if (rotate) {
                    faceVectorPacketInstant(hitVec);
                }
                HoleFill.mc.player.connection.sendPacket((Packet) new CPacketEntityAction((Entity) HoleFill.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                processRightClickBlock(neighbor, side2, hitVec);
                HoleFill.mc.player.swingArm(EnumHand.MAIN_HAND);
                HoleFill.mc.rightClickDelayTimer = 0;
                HoleFill.mc.player.connection.sendPacket((Packet) new CPacketEntityAction((Entity) HoleFill.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                return true;
            }
        }
        return false;
    }
    public static boolean canBeClicked(final BlockPos pos) {
        return getBlock(pos).canCollideCheck(getState(pos), false);
    }

    public static IBlockState getState(final BlockPos pos) {
        return HoleFill.mc.world.getBlockState(pos);
    }

    public static Block getBlock(final BlockPos pos) {
        return getState(pos).getBlock();
    }

    public static void faceVectorPacketInstant(final Vec3d vec) {
        final float[] rotations = getNeededRotations2(vec);
        HoleFill.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rotations[0], rotations[1], HoleFill.mc.player.onGround));
    }

    private static float[] getNeededRotations2(final Vec3d vec) {
        final Vec3d eyesPos = getEyesPos();
        final double diffX = vec.x - eyesPos.x;
        final double diffY = vec.y - eyesPos.y;
        final double diffZ = vec.z - eyesPos.z;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[] { HoleFill.mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - HoleFill.mc.player.rotationYaw), HoleFill.mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - HoleFill.mc.player.rotationPitch) };
    }

    public static Vec3d getEyesPos() {
        return new Vec3d(HoleFill.mc.player.posX, HoleFill.mc.player.posY + HoleFill.mc.player.getEyeHeight(), HoleFill.mc.player.posZ);
    }

    public static void processRightClickBlock(final BlockPos pos, final EnumFacing side, final Vec3d hitVec) {
        getPlayerController().processRightClickBlock(HoleFill.mc.player, HoleFill.mc.world, pos, side, hitVec, EnumHand.MAIN_HAND);
    }

    private static PlayerControllerMP getPlayerController() {
        return HoleFill.mc.playerController;
    }
}

