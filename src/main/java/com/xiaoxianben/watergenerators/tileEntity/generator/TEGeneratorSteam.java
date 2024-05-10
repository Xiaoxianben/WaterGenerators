package com.xiaoxianben.watergenerators.tileEntity.generator;

import com.xiaoxianben.watergenerators.event.ConfigLoader;
import com.xiaoxianben.watergenerators.fluid.fluidTank.FluidTankGenerator;
import net.minecraftforge.fluids.FluidRegistry;

public class TEGeneratorSteam extends TEGeneratorFluid {

    @SuppressWarnings("unused")
    public TEGeneratorSteam() {
        this(0, 999);
    }

    /**
     * @param basePowerGeneration 基础能量产生量，单位FE
     */
    public TEGeneratorSteam(long basePowerGeneration, float level) {
        super(basePowerGeneration, level);

        this.basicAmountOfFluidToProduceEnergy = ConfigLoader.basicAmountOfSteamToProduceEnergy;
        this.fluidTank = new FluidTankGenerator(Math.max((int) (65 * this.basicAmountOfFluidToProduceEnergy * level), 1000), FluidRegistry.getFluid("steam"));
        this.fluidTank.setCanDrain(false);
    }

    @Override
    public float getFluidMagnification() {
        return ConfigLoader.steamMagnification;
    }

}
