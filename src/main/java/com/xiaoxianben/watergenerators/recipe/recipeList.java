package com.xiaoxianben.watergenerators.recipe;

import com.xiaoxianben.watergenerators.blocks.BlocksFluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class recipeList {

    public static List<recipeIngredient> recipeVaporizations;

    public static void init() {
        recipeVaporizations = new ArrayList<>();
        recipeVaporizations.add(addRecipeIngredient(recipeTypes.FLUID,
                new FluidStack[]{new FluidStack(FluidRegistry.WATER, 1)},
                new FluidStack[]{new FluidStack(FluidRegistry.getFluid("steam"), 1)})
        );

    }

    public static <T> recipeIngredient addRecipeIngredient(recipeType<T> tClass, T[] ints, T[] outs) {
        recipeIngredient recipeVaporization = new recipeIngredient();
        recipeVaporization.setInputs(tClass, getList(tClass, ints));
        recipeVaporization.setOutputs(tClass, getList(tClass, outs));
        return recipeVaporization;
    }

    @SafeVarargs
    public static <T> List<T> getList(recipeType<T> tClass, T... recipes) {
        ArrayList<T> arrayList = new ArrayList<>(recipes.length);
        arrayList.addAll(Arrays.asList(recipes));
        return arrayList;
    }
}
