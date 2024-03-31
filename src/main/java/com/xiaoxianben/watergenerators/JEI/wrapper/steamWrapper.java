package com.xiaoxianben.watergenerators.JEI.wrapper;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;

public class steamWrapper implements IRecipeWrapper {
    private final FluidStack input;
    private final FluidStack output;

    public steamWrapper(FluidStack input, FluidStack output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.FLUID, this.input);
        ingredients.setOutput(VanillaTypes.FLUID, this.output);
    }
}