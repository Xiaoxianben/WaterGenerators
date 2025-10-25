package com.xiaoxianben.watergenerators.tileEntity.generator.fluid;

import net.minecraftforge.fluids.FluidRegistry;

public class TEGeneratorWaterCompressed extends TEGeneratorFluid {


    public TEGeneratorWaterCompressed() {
        super();
        this.fluidTank.setCanFillFluidType(FluidRegistry.getFluid("watercompressed"));
    }

    @Override
    protected float getMachineMagnification() {
        return 4f;
    }
}
