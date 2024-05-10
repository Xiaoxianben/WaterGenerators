package com.xiaoxianben.watergenerators.blocks.generator;

import com.xiaoxianben.watergenerators.tileEntity.generator.TEGeneratorWater;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class BlockGeneratorWater extends BlockGeneratorFluid {


    public BlockGeneratorWater(float level, String levelName) {
        super("water_generator", level, levelName);
    }


    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TEGeneratorWater(this.basePowerGeneration, this.level);
    }

}
