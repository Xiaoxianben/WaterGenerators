package com.xiaoxianben.watergenerators.fluid.fluidTank;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.Collections;
import java.util.List;

public class FluidTankGenerator extends FluidTankBase {

    private final List<Fluid> canFillFluidType;

    public FluidTankGenerator(int capacity, List<Fluid> canFillFluidType) {
        super(capacity);
        this.canFillFluidType = canFillFluidType;
    }

    public FluidTankGenerator(int capacity, Fluid canFillFluidType) {
        this(capacity, Collections.singletonList(canFillFluidType));
    }

    @Override
    public boolean canFillFluidType(FluidStack fluid) {
        return canFillFluidType.contains(fluid.getFluid());
    }
}
