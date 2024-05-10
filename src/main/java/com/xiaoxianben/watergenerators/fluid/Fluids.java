package com.xiaoxianben.watergenerators.fluid;

import com.xiaoxianben.watergenerators.api.IHasInit;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.UniversalBucket;

public class Fluids implements IHasInit {

    public static Fluid steam;

    public static ItemStack steamBucket;

    @Override
    public void init() {
        steam = new Fluid("steam", new ResourceLocation("watergenerators:blocks/fluid/steam_still"), new ResourceLocation("watergenerators:blocks/fluid/steam_flow"));
        steam.setTemperature(1000).setGaseous(true).setLuminosity(0).setDensity(-10);

        FluidRegistry.addBucketForFluid(steam);
    }

    @Override
    public void initRecipes() {
        steamBucket = this.getBucket(steam);
    }

    public ItemStack getBucket(Fluid fluid) {
        UniversalBucket bucket = ForgeModContainer.getInstance().universalBucket;
        ItemStack itemBucket = new ItemStack(bucket);

        FluidStack fluidContents = new FluidStack(FluidRegistry.getFluid(fluid.getName()), bucket.getCapacity());

        itemBucket.setTagCompound(fluidContents.writeToNBT(new NBTTagCompound()));
        return itemBucket;
    }
}
