package com.xiaoxianben.watergenerators.jsonRecipe.ingredients;

import net.minecraftforge.fluids.FluidStack;

public class FluidStackAndEnergy {
    protected FluidStack fluidStack1;
    protected int energyValue;

    public FluidStackAndEnergy(FluidStack fluidStack1, int energyValue) {
        this.fluidStack1 = fluidStack1;
        this.energyValue = energyValue;
    }

    public int getEnergyValue() {
        return energyValue;
    }

    public FluidStack getFluidStack1() {
        return fluidStack1;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FluidStackAndEnergy) {
            FluidStackAndEnergy fluidStackAndEnergy1 = (FluidStackAndEnergy) obj;
            return fluidStackAndEnergy1.fluidStack1.containsFluid(fluidStack1) && fluidStackAndEnergy1.energyValue >= energyValue;
        }
        return false;
    }
}
