package com.xiaoxianben.watergenerators.blocks;

import com.xiaoxianben.watergenerators.API.IHasInit;
import com.xiaoxianben.watergenerators.Main;
import com.xiaoxianben.watergenerators.util.ModInformation;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class BlocksFluid implements IHasInit {
    public static BlockFluidClassic blockSteam;
    public static Fluid steam;

    @Override
    public void init() {
        MaterialLiquid MaterialSteam = (new MaterialLiquid(MapColor.SNOW));
        steam = new Fluid("steam", new ResourceLocation("watergenerators:blocks/fluid/steam_still"), new ResourceLocation("watergenerators:blocks/fluid/steam_flow"));
        steam.setTemperature(1000).setGaseous(true).setLuminosity(0).setDensity(-10);

        FluidRegistry.addBucketForFluid(steam);
        blockSteam = (BlockFluidClassic) new BlockFluidClassic(steam, MaterialSteam).setRegistryName(ModInformation.MOD_ID,"steam").setUnlocalizedName(ModInformation.MOD_ID+".steam");

        Main.BLOCKS.add(blockSteam);
    }

    @Override
    public void initRecipes() {

    }
}
