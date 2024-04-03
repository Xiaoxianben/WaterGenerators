package com.xiaoxianben.watergenerators.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class recipeTypes {
    public static final recipeType<ItemStack> ITEM = () -> ItemStack.class;
    public static final recipeType<ListItemStack> ITEMS = () -> ListItemStack.class;
    public static final recipeType<FluidStack> FLUID = () -> FluidStack.class;
}
