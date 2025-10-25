package com.xiaoxianben.watergenerators.tileEntity.generator.fluid;

import net.minecraftforge.fluids.FluidRegistry;

public class TEGeneratorWater extends TEGeneratorFluid {


    public TEGeneratorWater() {
        super();
        this.fluidTank.setCanFillFluidType(FluidRegistry.WATER);
    }

    @Override
    protected float getMachineMagnification() {
        return 1.5f;
    }
}
