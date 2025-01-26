package com.xiaoxianben.watergenerators.jsonRecipe.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

public class ShapedRecipes extends net.minecraft.item.crafting.ShapedRecipes {

    protected final int index;

    public ShapedRecipes(String group, int width, int height, NonNullList<Ingredient> ingredients, ItemStack result, int index) {
        super(group, width, height, ingredients, result);
        this.index = index;
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull InventoryCrafting inv) {
        ItemStack itemStack = super.getCraftingResult(inv);
        NBTTagCompound itemNBT = inv.getStackInSlot(this.index).getTagCompound();
        if (itemNBT != null) {
            // 复制tag
            itemStack.setTagCompound(itemNBT.copy());
        }
        return itemStack;
    }

}
