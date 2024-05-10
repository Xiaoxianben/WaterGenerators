package com.xiaoxianben.watergenerators.blocks.machine;

import com.xiaoxianben.watergenerators.Main;
import com.xiaoxianben.watergenerators.blocks.BlockBase;
import com.xiaoxianben.watergenerators.util.ModInformation;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class BlockMachineShell extends BlockBase {

    public float level;
    public String levelName;

    public BlockMachineShell(String levelName, float level) {
        super("machineshell_" + levelName, Material.IRON, Main.MACHINE_TAB);
        this.level = level;
        this.levelName = levelName;
    }

    public BlockMachineShell(String levelName, int level) {
        this(levelName, (float) level);
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
