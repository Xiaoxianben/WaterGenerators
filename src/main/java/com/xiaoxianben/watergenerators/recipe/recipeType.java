package com.xiaoxianben.watergenerators.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class recipeType<T> {
    public static final recipeType<ItemStack> ITEM = new recipeType<>(ItemStack.class, "item");
    public static final recipeType<FluidStack> FLUID = new recipeType<>(FluidStack.class, "fluid");
    public static final recipeType<Integer> INT = new recipeType<>(Integer.class, "int");
    public static final recipeType<Float> FLOAT = new recipeType<>(Float.class, "float");

    public Class<T> i;
    public String type;

    public recipeType(Class<T> iClass, String type) {
        this.i = iClass;
        this.type = type;
    }

    public Class<T> getRecipeClass() {
        return i;
    }

    public String getType() {
        return this.type;
    }
}
