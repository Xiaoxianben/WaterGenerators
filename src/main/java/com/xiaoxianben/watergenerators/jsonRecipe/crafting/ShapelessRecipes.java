package com.xiaoxianben.watergenerators.jsonRecipe.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

public class ShapelessRecipes extends net.minecraft.item.crafting.ShapelessRecipes {
    public ShapelessRecipes(String group, ItemStack output, NonNullList<Ingredient> ingredients) {
        super(group, output, ingredients);
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull InventoryCrafting inv) {
        ItemStack itemStack = super.getCraftingResult(inv);
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            NBTTagCompound itemStackNBT = inv.getStackInSlot(i).getTagCompound();
            if (itemStackNBT != null && itemStackNBT.hasKey("itemNBT")) {
                itemStack.setTagCompound(itemStackNBT.copy());
                break;
            }
        }
        return itemStack;
    }

}
