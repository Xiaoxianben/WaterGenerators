package com.xiaoxianben.watergenerators.tileEntity.generator;

import com.xiaoxianben.watergenerators.event.ConfigLoader;
import com.xiaoxianben.watergenerators.fluid.fluidTank.FluidTankGenerator;
import net.minecraftforge.fluids.FluidRegistry;

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

        this.basicAmountOfFluidToProduceEnergy = ConfigLoader.basicAmountOfWaterToProduceEnergy;

        this.fluidTank = new FluidTankGenerator(this.fluidTank.getCapacity(), FluidRegistry.WATER);
        this.fluidTank.setCanDrain(false);
    }

    @Override
    public float getFluidMagnification() {
        return ConfigLoader.waterMagnification;
    }

}
