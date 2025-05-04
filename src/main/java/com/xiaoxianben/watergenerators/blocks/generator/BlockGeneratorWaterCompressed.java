package com.xiaoxianben.watergenerators.blocks.generator;

import com.xiaoxianben.watergenerators.tileEntity.generator.TEGeneratorWaterCompressed;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class BlockGeneratorWaterCompressed extends BlockGeneratorFluid {


    public BlockGeneratorWaterCompressed(float level, String levelName) {
        super("watercompressed_generator", levelName, level);
    }


    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TEGeneratorWaterCompressed(this.basePowerGeneration);
    }
}
