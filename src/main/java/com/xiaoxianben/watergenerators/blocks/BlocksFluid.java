package com.xiaoxianben.watergenerators.blocks;

import com.xiaoxianben.watergenerators.WaterGenerators;
import com.xiaoxianben.watergenerators.api.IHasInit;
import com.xiaoxianben.watergenerators.fluid.Fluids;
import com.xiaoxianben.watergenerators.util.ModInformation;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraftforge.fluids.BlockFluidClassic;

import java.util.Objects;

public class BlocksFluid implements IHasInit {
    public BlockFluidClassic blockSteam;

    @Override
    public void init() {
        MaterialLiquid MaterialSteam = (new MaterialLiquid(MapColor.SNOW));
        blockSteam = (BlockFluidClassic) new BlockFluidClassic(Fluids.steam, MaterialSteam).setRegistryName(ModInformation.MOD_ID, "steam").setUnlocalizedName(ModInformation.MOD_ID + ".steam");

        Objects.requireNonNull(WaterGenerators.BLOCKS).add(blockSteam);
    }

    @Override
    public void initRecipes() {

    }
}
