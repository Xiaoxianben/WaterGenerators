package com.xiaoxianben.watergenerators.tileEntity.generator;

import com.xiaoxianben.watergenerators.config.ConfigValue;
import net.minecraftforge.fluids.FluidRegistry;

import java.math.BigDecimal;

public class TEGeneratorWater extends TEGeneratorFluid {

    @SuppressWarnings("unused")
    public TEGeneratorWater() {
        this(0, 999);
    }

    /**
     * @param basePowerGeneration 基础能量产生量，单位mbT
     */
    public TEGeneratorWater(long basePowerGeneration, float level) {
        super(basePowerGeneration, level);

        this.basicAmountOfFluidToProduceEnergy = ConfigValue.basicAmountOfWaterToProduceEnergy;

        this.fluidTank.setCanFillFluidType(FluidRegistry.WATER);
        this.fluidTank.setCapacity(Math.max(new BigDecimal(65 * basicAmountOfFluidToProduceEnergy).intValue(), 1000));

    }

    @Override
    public float getFluidMagnification() {
        return ConfigValue.waterMagnification;
    }

}
