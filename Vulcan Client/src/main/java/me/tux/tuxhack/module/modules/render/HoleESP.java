package me.tux.tuxhack.module.modules.render;

import me.tux.tuxhack.module.Module;
import me.tux.tuxhack.setting.Setting;

import me.tux.tuxhack.util.RenderUtil;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;


public class HoleESP extends Module {
    // ArrayList<String> holes = findHoles();

    Setting.Mode mode;
    static Setting.Integer range;
    Setting.Integer r;
    Setting.Integer g;
    Setting.Integer b;
    Setting.Integer a;

    /**
     * add noRender for own hole... gradient options
     */

    public HoleESP() {
        super("HoleESP", "Higlights holes in the ground", Category.Render);
    }


    public boolean setup() {
        ArrayList<String> modes = new ArrayList<>();
        modes.add("Gradient");
        modes.add("FlatOutline");
        modes.add("Flat");
        modes.add("Outline");


        mode = registerMode("Mode", "mode", modes, "Gradient");
        range = registerInteger("range", "range", 8, 4, 14);
        r = registerInteger("Red", "red", 100, 0, 255);
        g = registerInteger("Green", "green", 100, 0, 255);
        b = registerInteger("Blue", "blue", 100, 0, 255);
        a = registerInteger("Alpha", "alpha", 100, 0, 255);
        return false;
    }

    public int onUpdate() {

        if (RenderUtil.worldCheck() == false)
            return 0;
        return 0;
    }

    BlockPos blockPos;
    List<BlockPos> validBlockPos;


    List findHoles() {
        return validBlockPos;
    }


    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(HoleESP.mc.player.posX), Math.floor(HoleESP.mc.player.posY), Math.floor(HoleESP.mc.player.posZ));

    }

    static boolean IsHole(BlockPos blockPos) {

       return true;
    }
}











