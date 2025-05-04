package com.xiaoxianben.watergenerators.fluids.fluidTank;

import com.xiaoxianben.watergenerators.jsonRecipe.Recipes;
import com.xiaoxianben.watergenerators.jsonRecipe.ingredients.FluidStackAndEnergy;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;

/**
 * 记录配方的流体池
 * */
public class FluidTankRecipe extends FluidTankBase {
    public final Recipes<FluidStack, FluidStackAndEnergy> recipeList;

    public FluidTankRecipe(int capacity, Recipes<FluidStack, FluidStackAndEnergy> recipeList) {
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
        return this.recipeList.getInput(this.recipeList.indexOfKey(this.getFluid()));
    }

    @Nullable
    public FluidStackAndEnergy getRecipeOutput() {
        return this.recipeList.getOutput(this.getFluid());
    }
}
