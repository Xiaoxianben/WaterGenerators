package com.xiaoxianben.watergenerators.tileEntity.generator;

import com.xiaoxianben.watergenerators.config.ConfigValue;
import net.minecraftforge.fluids.FluidRegistry;

import java.math.BigDecimal;

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

        this.basicAmountOfFluidToProduceEnergy = ConfigValue.basicAmountOfSteamToProduceEnergy;

        this.fluidTank.setCanFillFluidType(FluidRegistry.getFluid("steam"));
        this.fluidTank.setCapacity(Math.max(new BigDecimal(65 * basicAmountOfFluidToProduceEnergy).intValue(), 1000));

    }

    @Override
    public float getFluidMagnification() {
        return ConfigValue.steamMagnification;
    }

}
