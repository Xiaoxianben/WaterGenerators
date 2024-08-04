package com.xiaoxianben.watergenerators.fluids.fluidTank;

import com.xiaoxianben.watergenerators.recipe.Recipes;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;

public class FluidTankInput extends FluidTankBase {
    public Recipes<FluidStack, FluidStack> recipeList;

    public FluidTankInput(int capacity, Recipes<FluidStack, FluidStack> recipeList) {
        super(capacity);
        this.recipeList = recipeList;
    }

    public boolean canFillFluidType(FluidStack fluid) {
        return this.getInputFluid(fluid) != null;
    }


    protected FluidStack getInputFluid(FluidStack fluid) {
        return this.recipeList.getInputs().stream().filter(fluid::containsFluid).findFirst().orElse(null);
    }

    @Nullable
    public FluidStack getRecipeFluidInput() {
        if (this.getFluid() == null) return null;
        return this.getInputFluid(this.getFluid());
    }

    @Nullable
    public FluidStack getRecipeOutput() {
        if (this.getRecipeFluidInput() == null) return null;
        return this.recipeList.getOutput(getRecipeFluidInput());

    }
}
