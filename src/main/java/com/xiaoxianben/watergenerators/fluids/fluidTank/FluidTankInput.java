package com.xiaoxianben.watergenerators.fluids.fluidTank;

import com.xiaoxianben.watergenerators.jsonRecipe.Recipes;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.stream.Collectors;

public class FluidTankInput extends FluidTankBase {
    public final Recipes<FluidStack, FluidStack> recipeList;

    public FluidTankInput(int capacity, Recipes<FluidStack, FluidStack> recipeList) {
        super(capacity);
        this.recipeList = recipeList;
    }

    @Override
    public boolean canFillFluidType(FluidStack fluid) {
        return this.recipeList.containsKay(fluid);
    }


    @Nullable
    public FluidStack getRecipeFluidInput() {
        if (this.getFluid() == null || !this.recipeList.containsKay(this.getFluid())) return null;
        return this.recipeList.getInput(this.recipeList.getInputs().stream().collect(Collectors.toList()).indexOf(this.getFluid()));
    }

    @Nullable
    public FluidStack getRecipeOutput() {
        return this.recipeList.getOutput(this.getFluid());
    }
}
