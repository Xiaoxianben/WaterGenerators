package com.xiaoxianben.watergenerators.tileEntity.generator;

import com.xiaoxianben.watergenerators.config.ConfigValue;
import net.minecraftforge.fluids.FluidRegistry;

public class TEGeneratorSteam extends TEGeneratorFluid {

    @SuppressWarnings("unused")
    public TEGeneratorSteam() {
        this(Long.MAX_VALUE);
    }

    /**
     * @param basePowerGeneration 基础能量产生量，单位FE
     */
    public TEGeneratorSteam(long basePowerGeneration) {
        super(basePowerGeneration);
    }

    @Override
    public void init() {
        this.basicAmountOfFluidToProduceEnergy = ConfigValue.basicAmountOfSteamToProduceEnergy;

        super.init();

        this.fluidTank.setCanFillFluidType(FluidRegistry.getFluid("steam"));
    }

    @Override
    public float getEnergyMagnification() {
        return ConfigValue.steamMagnification;
    }

}
