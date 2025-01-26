package com.xiaoxianben.watergenerators.tileEntity.generator;

import com.xiaoxianben.watergenerators.config.ConfigValue;
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


    @Override
    public float getEnergyMagnification() {
        if (this.fluidTank.getFluid() != null) {
            return super.getEnergyMagnification() + ConfigValue.waterMagnification;
        }
        return 0.0f;
    }

}
