package com.xiaoxianben.watergenerators.fluid;

import com.xiaoxianben.watergenerators.enery.EnergyLiquid;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class FluidTankGenerator extends FluidTankBase {

    private final List<Fluid> canFillFluidType;

    public FluidTankGenerator(int capacity) {
        super(capacity);
        canFillFluidType = Arrays.asList(EnergyLiquid.liquidEnergy.keySet().toArray(new Fluid[0]));
    }

    @Override
    public boolean canFillFluidType(FluidStack fluid) {
        return canFillFluidType.contains(fluid.getFluid());
    }
}
