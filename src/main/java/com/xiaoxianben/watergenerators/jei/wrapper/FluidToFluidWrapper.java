package com.xiaoxianben.watergenerators.jei.wrapper;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraftforge.fluids.FluidStack;

public class FluidToFluidWrapper implements IRecipeWrapper {
    private final FluidStack input;
    private final FluidStack output;

    public FluidToFluidWrapper(FluidStack input, FluidStack output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.FLUID, this.input);
        ingredients.setOutput(VanillaTypes.FLUID, this.output);
    }
}