package com.xiaoxianben.watergenerators.tileEntity.generator;

import net.minecraftforge.fluids.FluidRegistry;

public class TEGeneratorWater extends TEGeneratorFluid {


    @SuppressWarnings("unused")
    public TEGeneratorWater() {
        this(Long.MAX_VALUE);
    }

    /**
     * @param basePowerGeneration 基础能量产生量，单位FE
     */
    public TEGeneratorWater(long basePowerGeneration) {
        super(basePowerGeneration);
        this.fluidTank.setCanFillFluidType(FluidRegistry.WATER);
    }

}
