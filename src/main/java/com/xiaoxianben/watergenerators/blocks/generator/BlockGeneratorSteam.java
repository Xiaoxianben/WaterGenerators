package com.xiaoxianben.watergenerators.blocks.generator;

import com.xiaoxianben.watergenerators.tileEntity.generator.TEGeneratorSteam;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class BlockGeneratorSteam extends BlockGeneratorFluid {


    public BlockGeneratorSteam(float level, String levelName) {
        super("steam_generator", level, levelName);
    }


    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TEGeneratorSteam(this.basePowerGeneration, this.level);
    }
}
