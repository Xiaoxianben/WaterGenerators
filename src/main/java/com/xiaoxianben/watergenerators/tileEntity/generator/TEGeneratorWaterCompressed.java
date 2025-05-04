package com.xiaoxianben.watergenerators.tileEntity.generator;

import net.minecraftforge.fluids.FluidRegistry;

public class TEGeneratorWaterCompressed extends TEGeneratorFluid {


    @SuppressWarnings("unused")
    public TEGeneratorWaterCompressed() {
        this(Long.MAX_VALUE);
    }

    /**
     * @param basePowerGeneration 基础能量产生量，单位FE
     */
    public TEGeneratorWaterCompressed(long basePowerGeneration) {
        super(basePowerGeneration);
        this.fluidTank.setCanFillFluidType(FluidRegistry.getFluid("watercompressed"));
    }

}
