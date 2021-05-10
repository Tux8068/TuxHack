package me.ka1.vulcan.module.modules.Hud;

import me.ka1.vulcan.Vulcan;
import me.ka1.vulcan.clickgui.ClickGUI;
import me.ka1.vulcan.module.Module;
import me.ka1.vulcan.module.ModuleManager;
import me.ka1.vulcan.module.modules.client.ClickGuiModule;
import me.ka1.vulcan.module.modules.combat.AutoCrystal;
import me.ka1.vulcan.setting.Setting;
import me.ka1.vulcan.util.friend.Friends;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class CombatInfo extends Module {

    public CombatInfo() {
        super("CombatInfo", Category.Hud);
    }

    /* displays ping, totem count, PLR, HTR and LBY
     *  By the one and only _KA1
     */

    public static
    Setting.Mode CombatInfoWatermark;
    Setting.Integer infoX;
    Setting.Integer infoY;
    Setting.Mode GUIColorSync;
    Setting.Integer colorRed;
    Setting.Integer colorGreen;
    Setting.Integer colorBlue;
    Setting.Integer colorAlpha;
    Setting.Mode Mode;
    Color c;
    int totems;
    int color;
    private BlockPos[] surroundOffset;

    public void setup() {
        // holy shit this took ages bc im a retard

        infoX = registerInteger("Combat info X", "Combat info X", 0, 0, 1280);
        infoY = registerInteger("Combat info Y", "Combat info Y", 150, 0, 960);

        /* colorRed = registerInteger("Red", "colorRed", 255, 0, 255);
          colorGreen = registerInteger("Green", "colorGreen", 255, 0, 255);
          colorBlue = registerInteger("Blue", "colorBlue", 255, 0, 255);
          colorAlpha = registerInteger("Alpha", "colorAlpha", 255, 0, 255);
         */

        //GUIColorSync = registerMode("GUIColorSync", "GUIColorSync")

        ArrayList<String> Modes;
        Modes = new ArrayList<>();
        Modes.add("VULCAN");
        Modes.add("KAiKLiENT");
        Modes.add("YoshiWare");

        Mode = registerMode("Mode", "combatInfoMode", Modes, "VULCAN");
    }

    final AutoCrystal a = (AutoCrystal) ModuleManager.getModuleByName("AutoCrystal");

    public int getPing() {
        int p = -1;
        if (mc.player == null || mc.getConnection() == null || mc.getConnection().getPlayerInfo(mc.player.getName()) == null) {
        } else {
            p = mc.getConnection().getPlayerInfo(mc.player.getName()).getResponseTime();
        }
        return p;
    }

    public void onRender() {

        EntityOtherPlayerMP players = mc.world.loadedEntityList.stream()
                .filter(entity -> entity instanceof EntityOtherPlayerMP)
                .filter(e -> mc.player.getDistance(e) <= AutoCrystal.placeRange.getValue())
                .map(entity -> (EntityOtherPlayerMP) entity)
                .min(Comparator.comparing(c -> mc.player.getDistance(c)))
                .orElse(null);

        totems = mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();

        if (mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) totems++;


        this.surroundOffset = new BlockPos[]{new BlockPos(0, 0, -1), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0)};
        final List<EntityPlayer> entities = mc.world.playerEntities.stream().filter(entityPlayer -> !Friends.isFriend(entityPlayer.getName())).collect(Collectors.toList());

        Color on = new Color(0, 255, 0);
        Color off = new Color(255, 0, 0);


        ClickGUI.color = new Color(ClickGuiModule.red.getValue(), ClickGuiModule.green.getValue(), ClickGuiModule.blue.getValue()).getRGB();

        // color = new Color(colorRed.getValue(), colorGreen.getValue(), colorBlue.getValue(), colorAlpha.getValue()).getRGB();


        Vulcan.fontRenderer.drawStringWithShadow(Mode.getValue(), infoX.getValue(), infoY.getValue(), ClickGUI.color);

        if (players != null && mc.player.getDistance(players) <= AutoCrystal.range.getValue()) {
           Vulcan.fontRenderer.drawStringWithShadow("HTR", infoX.getValue(), infoY.getValue() + 10, on.getRGB());
        } else {
            Vulcan.fontRenderer.drawStringWithShadow("HTR", infoX.getValue(), infoY.getValue() + 10, off.getRGB());
        }
        if (players != null && mc.player.getDistance(players) <= AutoCrystal.placeRange.getValue()) {
            Vulcan.fontRenderer.drawStringWithShadow("PLR", infoX.getValue(), infoY.getValue() + 20, on.getRGB());
        } else {
            Vulcan.fontRenderer.drawStringWithShadow("PLR", infoX.getValue(), infoY.getValue() + 20, off.getRGB());
        }
        if (totems > 1) {
            Vulcan.fontRenderer.drawStringWithShadow(totems + "", infoX.getValue(), infoY.getValue() + 30, on.getRGB());
        } else {
            Vulcan.fontRenderer.drawStringWithShadow(totems + "", infoX.getValue(), infoY.getValue() + 30, off.getRGB());
        }
        if (getPing() > 100) {
            Vulcan.fontRenderer.drawStringWithShadow("PING " + getPing(), infoX.getValue(), infoY.getValue() + 40, off.getRGB());
        } else {
            Vulcan.fontRenderer.drawStringWithShadow("PING " + getPing(), infoX.getValue(), infoY.getValue() + 40, on.getRGB());
        }

        for (final EntityPlayer e : entities) {
            int i = 0;
            for (final BlockPos add : this.surroundOffset) {
                ++i;
                final BlockPos o = new BlockPos(e.getPositionVector().x, e.getPositionVector().y, e.getPositionVector().z).add(add.getX(), add.getY(), add.getZ());
                if (mc.world.getBlockState(o).getBlock() == Blocks.OBSIDIAN) {
                    if (i == 1 && a.canPlaceCrystal(o.north(1).down())) {
                        Vulcan.fontRenderer.drawStringWithShadow( "LBY", infoX.getValue(), infoY.getValue() + 50, on.getRGB());
                    }
                    if (i == 2 && a.canPlaceCrystal(o.east(1).down())) {
                        Vulcan.fontRenderer.drawStringWithShadow( "LBY", infoX.getValue(), infoY.getValue() + 50, on.getRGB());
                    }
                    if (i == 3 && a.canPlaceCrystal(o.south(1).down())) {
                        Vulcan.fontRenderer.drawStringWithShadow("LBY", infoX.getValue(), infoY.getValue() + 50, on.getRGB());
                    }
                    if (i == 4 && a.canPlaceCrystal(o.west(1).down())) {
                        Vulcan.fontRenderer.drawStringWithShadow( "LBY", infoX.getValue(), infoY.getValue() + 50, on.getRGB());
                    }
                } else
                    Vulcan.fontRenderer.drawStringWithShadow( "LBY", infoX.getValue(), infoY.getValue() + 50, off.getRGB());
            }

        }

    }

}
