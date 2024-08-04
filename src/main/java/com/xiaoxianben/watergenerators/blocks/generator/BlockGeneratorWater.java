package com.xiaoxianben.watergenerators.blocks.generator;

import com.xiaoxianben.watergenerators.init.ModBlocks;
import com.xiaoxianben.watergenerators.tileEntity.generator.TEGeneratorWater;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class BlockGeneratorWater extends BlockGeneratorFluid {


    public BlockGeneratorWater(float level, String levelName) {
        super("water_generator", levelName, level, ModBlocks.allGeneratorWater);
    }


    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TEGeneratorWater(this.basePowerGeneration);
    }

}
