package com.xiaoxianben.watergenerators.tileEntity.generator.fluid;

import net.minecraftforge.fluids.FluidRegistry;

public class TEGeneratorSteam extends TEGeneratorFluid {

    public TEGeneratorSteam() {
        super();
        this.fluidTank.setCanFillFluidType(FluidRegistry.getFluid("steam"));
    }

    @Override
    protected float getMachineMagnification() {
        return 20;
    }
}
