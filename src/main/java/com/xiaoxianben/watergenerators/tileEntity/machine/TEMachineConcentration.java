package com.xiaoxianben.watergenerators.tileEntity.machine;

import com.xiaoxianben.watergenerators.fluids.fluidTank.FluidTankRecipe;
import com.xiaoxianben.watergenerators.jsonRecipe.ModJsonRecipe;

public class TEMachineConcentration extends TEMachineVaporization {

    public TEMachineConcentration() {
        super(999);
    }

    public TEMachineConcentration(float level) {
        super(level);
        fluidTankInt = new FluidTankRecipe((int) (10000 * level), ModJsonRecipe.recipeConcentration);
    }

}
