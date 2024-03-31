package com.xiaoxianben.watergenerators.blocks;

import com.xiaoxianben.watergenerators.Main;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class BlockMachineShell extends BlockBase {

    public float level;

    public BlockMachineShell(String name, float level) {
        super("machineshell_" + name, Material.IRON, Main.MACHINE_TAB);
        this.level = level;
    }

    public BlockMachineShell(String name, int level) {
        this(name, (float) level);
    }

    public boolean canPlaceBlockAt(@Nonnull World worldIn, @Nonnull BlockPos pos) {
        return false;
    }
}
