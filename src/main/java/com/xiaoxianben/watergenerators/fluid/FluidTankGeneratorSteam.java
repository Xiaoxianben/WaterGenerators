package com.xiaoxianben.watergenerators.fluid;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class FluidTankGeneratorSteam extends FluidTankBase {
    public FluidTankGeneratorSteam(int capacity) {
        super(capacity);
    }

    @Override
    public boolean canFillFluidType(FluidStack fluid) {
        return fluid.getFluid() == FluidRegistry.getFluid("steam"); // 返回true，表示该流体可以填充到这个流体罐中
    }
}
