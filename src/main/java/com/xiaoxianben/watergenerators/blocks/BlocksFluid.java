package com.xiaoxianben.watergenerators.blocks;

import com.xiaoxianben.watergenerators.Main;
import com.xiaoxianben.watergenerators.api.IHasInit;
import com.xiaoxianben.watergenerators.fluid.Fluids;
import com.xiaoxianben.watergenerators.util.ModInformation;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraftforge.fluids.BlockFluidClassic;

public class BlocksFluid implements IHasInit {
    public static BlockFluidClassic blockSteam;

    @Override
    public void init() {
        MaterialLiquid MaterialSteam = (new MaterialLiquid(MapColor.SNOW));
        blockSteam = (BlockFluidClassic) new BlockFluidClassic(Fluids.steam, MaterialSteam).setRegistryName(ModInformation.MOD_ID, "steam").setUnlocalizedName(ModInformation.MOD_ID + ".steam");

        Main.BLOCKS.add(blockSteam);
    }

    @Override
    public void initRecipes() {

    }
}
