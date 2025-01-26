package com.xiaoxianben.watergenerators.jei.wrapper;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraftforge.fluids.FluidStack;

public class FluidGeneratorWrapper implements IRecipeWrapper {
    private final FluidStack input;

    public FluidGeneratorWrapper(FluidStack input) {
        this.input = input;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.FLUID, this.input);
    }
}