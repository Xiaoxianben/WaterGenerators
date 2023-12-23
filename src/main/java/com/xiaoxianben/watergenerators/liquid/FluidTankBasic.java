package com.xiaoxianben.watergenerators.liquid;

import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.init.Blocks;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nullable;
import java.util.Arrays;

public class FluidTankBasic extends FluidTank {

    private FluidStack[] canFillFluidType = new FluidStack[6];

    public FluidTankBasic(int capacity) {
        super(capacity);
        canFillFluidType[0] = new FluidStack(FluidRegistry.WATER, 0);
    }

    public FluidTankBasic(@Nullable FluidStack fluidStack, int capacity) {
        super(fluidStack, capacity);
    }

    public FluidTankBasic(Fluid fluid, int amount, int capacity) {
        super(fluid, amount, capacity);
    }

    @Override
    public boolean canFillFluidType(FluidStack fluid) {
        return fluid.getFluid().getBlock() == Blocks.WATER;
    }
}
