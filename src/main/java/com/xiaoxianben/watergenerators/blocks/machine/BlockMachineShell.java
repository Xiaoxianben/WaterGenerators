package com.xiaoxianben.watergenerators.blocks.machine;

import com.xiaoxianben.watergenerators.WaterGenerators;
import com.xiaoxianben.watergenerators.blocks.BlockBase;
import com.xiaoxianben.watergenerators.init.ModBlocks;
import com.xiaoxianben.watergenerators.util.ModInformation;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class BlockMachineShell extends BlockBase {

    public float level;
    public String levelName;


    public BlockMachineShell(float level, String levelName) {
        super("machineshell_" + levelName, Material.IRON, WaterGenerators.MACHINE_TAB, SoundType.METAL, ModBlocks.allMachineShell);
        this.level = level;
        this.levelName = levelName;
    }


    public String getLevelName() {
        return I18n.format("level." + this.levelName + ".name");
    }

    @Nonnull
    public String getLocalizedName() {
        return I18n.format(this.getUnlocalizedName() + ".name") + "-" + this.getLevelName();
    }

    @Nonnull
    public String getUnlocalizedName() {
        return "tile." + ModInformation.MOD_ID + "-machineshell";
    }

    public boolean canPlaceBlockAt(@Nonnull World worldIn, @Nonnull BlockPos pos) {
        return false;
    }
}
