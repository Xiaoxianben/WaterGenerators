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
        this.fluidTank.setCanFillFluidType(FluidRegistry.getFluid("steam"));
    }


    @Override
    public float getEnergyMagnification() {
        if (this.fluidTank.getFluid() != null) {
            return super.getEnergyMagnification() + ConfigValue.steamMagnification;
        }
        return 0.0f;
    }

}
