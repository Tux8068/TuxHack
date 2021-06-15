package me.tux.tuxhack.module.modules.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.tux.tuxhack.event.events.RenderEvent;
import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.setting.Setting;
import me.tux.tuxhack.util.RenderUtil;
import me.tux.tuxhack.util.TuxHackTessellator;
import me.tux.tuxhack.util.colors.Rainbow;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.AxisAlignedBB;
import java.awt.Color;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.util.math.Vec3i;
import net.minecraft.init.Blocks;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.util.math.BlockPos;


public class HoleESP extends Module
{
    public static Setting.Integer rangeS;
    Setting.Integer redb;
    Setting.Integer greenb;
    Setting.Integer blueb;
    Setting.Integer redo;
    Setting.Integer greeno;
    Setting.Integer blueo;
    Setting.Integer outlineW;
    Setting.Boolean rainbow;
    Setting.Boolean hideOwn;
    Setting.Boolean flatOwn;
    Setting.Mode mode;
    Setting.Mode type;
    private final BlockPos[] surroundOffset;
    private ConcurrentHashMap<BlockPos, Boolean> safeHoles;

    public HoleESP() {
        super("HoleESP", Category.RENDER);
        this.surroundOffset = new BlockPos[] { new BlockPos(0, -1, 0), new BlockPos(0, 0, -1), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0) };
    }

    @Override
    public boolean setup() {
        HoleESP.rangeS = this.registerInteger("Range", "Range", 8, 1, 20);
        this.rainbow = this.registerBoolean("Rainbow", "Rainbow", false);
        this.hideOwn = this.registerBoolean("Hide Own", "HideOwn", true);
        this.flatOwn = this.registerBoolean("Flat Own", "FlatOwn", false);
        this.redb = this.registerInteger("Red Brock", "RedBrock", 215, 0, 255);
        this.greenb = this.registerInteger("Green Brock", "GreenBrock", 0, 0, 255);
        this.blueb = this.registerInteger("Blue Brock", "BlueBrock", 255, 0, 255);
        this.redo = this.registerInteger("Red Obi", "Red Obi", 60, 0, 255);
        this.greeno = this.registerInteger("Green Obi", "Green Obi", 178, 0, 255);
        this.blueo = this.registerInteger("Blue Obi", "Blue Obi", 255, 0, 255);
        this.outlineW = this.registerInteger("OutlineW", "OutlineW", 4, 1, 12);
        final ArrayList<String> render = new ArrayList<String>();
        render.add("Outline");
        render.add("Fill");
        render.add("Both");
        final ArrayList<String> modes = new ArrayList<String>();
        modes.add("Air");
        modes.add("Ground");
        modes.add("Flat");
        modes.add("Slab");
        this.type = this.registerMode("Render", "Render", render, "Both");
        this.mode = this.registerMode("Mode", "Mode", modes, "Air");
        return false;
    }

    public List<BlockPos> getSphere(final BlockPos loc, final float r, final int h, final boolean hollow, final boolean sphere, final int plus_y) {
        final List<BlockPos> circleblocks = new ArrayList<BlockPos>();
        final int cx = loc.getX();
        final int cy = loc.getY();
        final int cz = loc.getZ();
        for (int x = cx - (int)r; x <= cx + r; ++x) {
            for (int z = cz - (int)r; z <= cz + r; ++z) {
                for (int y = sphere ? (cy - (int)r) : cy; y < (sphere ? (cy + r) : ((float)(cy + h))); ++y) {
                    final double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0);
                    if (dist < r * r && (!hollow || dist >= (r - 1.0f) * (r - 1.0f))) {
                        final BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                }
            }
        }
        return circleblocks;
    }

    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(HoleESP.mc.player.posX), Math.floor(HoleESP.mc.player.posY), Math.floor(HoleESP.mc.player.posZ));
    }

    @Override
    public int onUpdate() {
        if (this.safeHoles == null) {
            this.safeHoles = new ConcurrentHashMap<BlockPos, Boolean>();
        }
        else {
            this.safeHoles.clear();
        }
        final int range = (int)Math.ceil(HoleESP.rangeS.getValue());
        final List<BlockPos> blockPosList = this.getSphere(getPlayerPos(), (float)range, range, false, true, 0);
        for (final BlockPos pos : blockPosList) {
            if (!HoleESP.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR)) {
                continue;
            }
            if (!HoleESP.mc.world.getBlockState(pos.add(0, 1, 0)).getBlock().equals(Blocks.AIR)) {
                continue;
            }
            if (!HoleESP.mc.world.getBlockState(pos.add(0, 2, 0)).getBlock().equals(Blocks.AIR)) {
                continue;
            }
            if (this.hideOwn.getValue() && pos.equals((Object)new BlockPos(HoleESP.mc.player.posX, HoleESP.mc.player.posY, HoleESP.mc.player.posZ))) {
                continue;
            }
            boolean isSafe = true;
            boolean isBedrock = true;
            for (final BlockPos offset : this.surroundOffset) {
                final Block block = HoleESP.mc.world.getBlockState(pos.add((Vec3i)offset)).getBlock();
                if (block != Blocks.BEDROCK) {
                    isBedrock = false;
                }
                if (block != Blocks.BEDROCK && block != Blocks.OBSIDIAN && block != Blocks.ENDER_CHEST && block != Blocks.ANVIL) {
                    isSafe = false;
                    break;
                }
            }
            if (!isSafe) {
                continue;
            }
            this.safeHoles.put(pos, isBedrock);
        }
        return range;
    }

    @Override
    public void onWorldRender(final RenderEvent event) {
        if (HoleESP.mc.player == null || this.safeHoles == null) {
            return;
        }
        if (this.safeHoles.isEmpty()) {
            return;
        }
        TuxHackTessellator.prepare(7);
        if (this.mode.getValue().equalsIgnoreCase("Air")) {
            this.safeHoles.forEach((blockPos, isBedrock) -> {
                if (isBedrock) {
                    this.drawBox(blockPos, this.redb.getValue(), this.greenb.getValue(), this.blueb.getValue());
                }
                else {
                    this.drawBox(blockPos, this.redo.getValue(), this.greeno.getValue(), this.blueo.getValue());
                }
                return;
            });
        }
        if (this.mode.getValue().equalsIgnoreCase("Ground")) {
            this.safeHoles.forEach((blockPos, isBedrock) -> {
                if (isBedrock) {
                    this.drawBox2(blockPos, this.redb.getValue(), this.greenb.getValue(), this.blueb.getValue());
                }
                else {
                    this.drawBox2(blockPos, this.redo.getValue(), this.greeno.getValue(), this.blueo.getValue());
                }
                return;
            });
        }
        if (this.mode.getValue().equalsIgnoreCase("Flat")) {
            this.safeHoles.forEach((blockPos, isBedrock) -> {
                if (isBedrock) {
                    this.drawFlat(blockPos, this.redb.getValue(), this.greenb.getValue(), this.blueb.getValue());
                }
                else {
                    this.drawFlat(blockPos, this.redo.getValue(), this.greeno.getValue(), this.blueo.getValue());
                }
                return;
            });
        }
        if (this.mode.getValue().equalsIgnoreCase("Slab")) {
            this.safeHoles.forEach((blockPos, isBedrock) -> {
                if (isBedrock) {
                    this.drawSlab(blockPos, this.redb.getValue(), this.greenb.getValue(), this.blueb.getValue());
                }
                else {
                    this.drawSlab(blockPos, this.redo.getValue(), this.greeno.getValue(), this.blueo.getValue());
                }
                return;
            });
        }
        TuxHackTessellator.release();
        TuxHackTessellator.prepare(7);
        if (this.mode.getValue().equalsIgnoreCase("Air")) {
            this.safeHoles.forEach((blockPos, isBedrock) -> {
                if (isBedrock) {
                    this.drawOutline(blockPos, 1, this.redb.getValue(), this.greenb.getValue(), this.blueb.getValue());
                }
                else {
                    this.drawOutline(blockPos, 1, this.redo.getValue(), this.greeno.getValue(), this.blueo.getValue());
                }
                return;
            });
        }
        if (this.mode.getValue().equalsIgnoreCase("Ground")) {
            this.safeHoles.forEach((blockPos, isBedrock) -> {
                if (isBedrock) {
                    this.drawOutline(blockPos, 1, this.redb.getValue(), this.greenb.getValue(), this.blueb.getValue());
                }
                else {
                    this.drawOutline(blockPos, 1, this.redo.getValue(), this.greeno.getValue(), this.blueo.getValue());
                }
                return;
            });
        }
        if (this.mode.getValue().equalsIgnoreCase("Flat")) {
            this.safeHoles.forEach((blockPos, isBedrock) -> {
                if (isBedrock) {
                    this.drawOutline(blockPos, 1, this.redb.getValue(), this.greenb.getValue(), this.blueb.getValue());
                }
                else {
                    this.drawOutline(blockPos, 1, this.redo.getValue(), this.greeno.getValue(), this.blueo.getValue());
                }
                return;
            });
        }
        TuxHackTessellator.release();
    }

    private void drawBox(final BlockPos blockPos, final int r, final int g, final int b) {
        if (this.type.getValue().equalsIgnoreCase("Fill") || this.type.getValue().equalsIgnoreCase("Both")) {
            final Color c = Rainbow.getColor();
            final AxisAlignedBB bb = HoleESP.mc.world.getBlockState(blockPos).getSelectedBoundingBox((World)HoleESP.mc.world, blockPos);
            Color color;
            if (this.rainbow.getValue()) {
                color = new Color(c.getRed(), c.getGreen(), c.getBlue(), 50);
            }
            else {
                color = new Color(r, g, b, 50);
            }
            if (this.mode.getValue().equalsIgnoreCase("Air")) {
                if (this.flatOwn.getValue() && blockPos.equals((Object)new BlockPos(HoleESP.mc.player.posX, HoleESP.mc.player.posY, HoleESP.mc.player.posZ))) {
                    TuxHackTessellator.drawBox(blockPos, color.getRGB(), 1);
                }
                else {
                    TuxHackTessellator.drawBox(blockPos, color.getRGB(), 63);
                }
            }
        }
    }

    public void drawBox2(final BlockPos blockPos, final int r, final int g, final int b) {
        if (this.type.getValue().equalsIgnoreCase("Fill") || this.type.getValue().equalsIgnoreCase("Both")) {
            final Color c = Rainbow.getColor();
            final AxisAlignedBB bb = HoleESP.mc.world.getBlockState(blockPos).getSelectedBoundingBox((World)HoleESP.mc.world, blockPos);
            Color color;
            if (this.rainbow.getValue()) {
                color = new Color(c.getRed(), c.getGreen(), c.getBlue(), 50);
            }
            else {
                color = new Color(r, g, b, 50);
            }
            if (this.mode.getValue().equalsIgnoreCase("Ground")) {
                TuxHackTessellator.drawBox2(blockPos, color.getRGB(), 63);
            }
        }
    }

    public void drawFlat(final BlockPos blockPos, final int r, final int g, final int b) {
        if (this.type.getValue().equalsIgnoreCase("Fill") || this.type.getValue().equalsIgnoreCase("Both")) {
            final Color c = Rainbow.getColor();
            final AxisAlignedBB bb = HoleESP.mc.world.getBlockState(blockPos).getSelectedBoundingBox((World)HoleESP.mc.world, blockPos);
            if (this.mode.getValue().equalsIgnoreCase("Flat")) {
                Color color;
                if (this.rainbow.getValue()) {
                    color = new Color(c.getRed(), c.getGreen(), c.getBlue(), 50);
                }
                else {
                    color = new Color(r, g, b, 50);
                }
                TuxHackTessellator.drawBox(blockPos, color.getRGB(), 1);
            }
        }
    }

    public void drawSlab(final BlockPos blockPos, final int r, final int g, final int b) {
        final AxisAlignedBB bb = new AxisAlignedBB(blockPos.getX() - HoleESP.mc.getRenderManager().viewerPosX, blockPos.getY() + 0.1 - HoleESP.mc.getRenderManager().viewerPosY, blockPos.getZ() - HoleESP.mc.getRenderManager().viewerPosZ, blockPos.getX() + 1 - HoleESP.mc.getRenderManager().viewerPosX, blockPos.getY() - HoleESP.mc.getRenderManager().viewerPosY, blockPos.getZ() + 1 - HoleESP.mc.getRenderManager().viewerPosZ);
        if (RenderUtil.isInViewFrustrum(new AxisAlignedBB(bb.minX + HoleESP.mc.getRenderManager().viewerPosX, bb.minY + HoleESP.mc.getRenderManager().viewerPosY, bb.minZ + HoleESP.mc.getRenderManager().viewerPosZ, bb.maxX + HoleESP.mc.getRenderManager().viewerPosX, bb.maxY + HoleESP.mc.getRenderManager().viewerPosY, bb.maxZ + HoleESP.mc.getRenderManager().viewerPosZ))) {
            RenderUtil.drawESP(bb, (float)r, (float)g, (float)b, 50.0f);
            if (this.type.getValue().equalsIgnoreCase("Outline") || this.type.getValue().equalsIgnoreCase("Both")) {
                RenderUtil.drawESPOutline(bb, (float)r, (float)g, (float)b, 255.0f, 1.0f);
            }
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    public void drawOutline(final BlockPos blockPos, final int width, final int r, final int g, final int b) {
        if (this.type.getValue().equalsIgnoreCase("Outline") || this.type.getValue().equalsIgnoreCase("Both")) {
            final float[] hue = { System.currentTimeMillis() % 11520L / 11520.0f };
            final int rgb = Color.HSBtoRGB(hue[0], 1.0f, 1.0f);
            final int r2 = rgb >> 16 & 0xFF;
            final int g2 = rgb >> 8 & 0xFF;
            final int b2 = rgb & 0xFF;
            final float[] array = hue;
            final int n = 0;
            array[n] += 0.02f;
            if (this.mode.getValue().equalsIgnoreCase("Air")) {
                if (this.flatOwn.getValue() && blockPos.equals((Object)new BlockPos(HoleESP.mc.player.posX, HoleESP.mc.player.posY, HoleESP.mc.player.posZ))) {
                    if (this.rainbow.getValue()) {
                        TuxHackTessellator.drawBoundingBoxBottom2(blockPos, (float)this.outlineW.getValue(), r2, g2, b2, 255);
                    }
                    else {
                        TuxHackTessellator.drawBoundingBoxBottom2(blockPos, (float)this.outlineW.getValue(), r, g, b, 255);
                    }
                }
                else if (this.rainbow.getValue()) {
                    TuxHackTessellator.drawBoundingBoxBlockPos(blockPos, (float)this.outlineW.getValue(), r2, g2, b2, 255);
                }
                else {
                    TuxHackTessellator.drawBoundingBoxBlockPos(blockPos, (float)this.outlineW.getValue(), r, g, b, 255);
                }
            }
            if (this.mode.getValue().equalsIgnoreCase("Flat")) {
                if (this.rainbow.getValue()) {
                    TuxHackTessellator.drawBoundingBoxBottom2(blockPos, (float)this.outlineW.getValue(), r2, g2, b2, 255);
                }
                else {
                    TuxHackTessellator.drawBoundingBoxBottom2(blockPos, (float)this.outlineW.getValue(), r, g, b, 255);
                }
            }
            if (this.mode.getValue().equalsIgnoreCase("Ground")) {
                if (this.rainbow.getValue()) {
                    TuxHackTessellator.drawBoundingBoxBlockPos2(blockPos, (float)this.outlineW.getValue(), r2, g2, b2, 255);
                }
                else {
                    TuxHackTessellator.drawBoundingBoxBlockPos2(blockPos, (float)this.outlineW.getValue(), r, g, b, 255);
                }
            }
        }
    }

    @Override
    public String getHudInfo() {
        String t = "";
        t = "[" + ChatFormatting.WHITE + this.mode.getValue() + ", " + this.type.getValue() + ChatFormatting.GRAY + "]";
        return t;
    }
}
