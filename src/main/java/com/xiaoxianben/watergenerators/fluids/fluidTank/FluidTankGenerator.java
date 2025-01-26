package com.xiaoxianben.watergenerators.fluids.fluidTank;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class FluidTankGenerator extends FluidTankBase {

    private List<Fluid> canFillFluidType;

    public FluidTankGenerator(int capacity, Collection<Fluid> canFillFluidType) {
        super(capacity);
        this.canFillFluidType = new ArrayList<>(canFillFluidType);
    }

    public FluidTankGenerator(int capacity, Fluid canFillFluidType) {
        this(capacity, Collections.singletonList(canFillFluidType));
    }

    public void setCanFillFluidType(Fluid fluid) {
        canFillFluidType = Collections.singletonList(fluid);
    }

    @Override
    public boolean canFillFluidType(FluidStack fluid) {
        return canFillFluidType.contains(fluid.getFluid());
    }
}
