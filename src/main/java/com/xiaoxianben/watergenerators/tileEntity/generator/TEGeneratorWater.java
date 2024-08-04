package com.xiaoxianben.watergenerators.tileEntity.generator;

import com.xiaoxianben.watergenerators.config.ConfigValue;
import net.minecraftforge.fluids.FluidRegistry;


public class TEGeneratorWater extends TEGeneratorFluid {

    @SuppressWarnings("unused")
    public TEGeneratorWater() {
        this(Long.MAX_VALUE);
    }

    /**
     * @param basePowerGeneration 基础能量产生量，单位mbT
     */
    public TEGeneratorWater(long basePowerGeneration) {
        super(basePowerGeneration);
    }

    @Override
    public void init() {
        this.basicAmountOfFluidToProduceEnergy = ConfigValue.basicAmountOfWaterToProduceEnergy;

        super.init();

        this.fluidTank.setCanFillFluidType(FluidRegistry.WATER);
    }

    @Override
    public float getEnergyMagnification() {
        return ConfigValue.waterMagnification;
    }

}
