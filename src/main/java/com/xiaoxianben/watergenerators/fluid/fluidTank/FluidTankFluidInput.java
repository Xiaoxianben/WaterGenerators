package com.xiaoxianben.watergenerators.fluid.fluidTank;

import com.xiaoxianben.watergenerators.recipe.Recipes;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;

public class FluidTankFluidInput<Input extends FluidStack, Output extends FluidStack> extends FluidTankBase {
    public Recipes<Input, Output> recipeList;

    public FluidTankFluidInput(int capacity, Recipes<Input, Output> recipeList) {
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
    public Output getRecipeOutput() {
        if (this.getRecipeFluidInput() == null) return null;
        FluidStack input = getRecipeFluidInput();
        return this.recipeList.getOutput((Input) input);

    }
}
