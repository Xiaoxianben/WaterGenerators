package com.xiaoxianben.watergenerators.tabs;

import com.xiaoxianben.watergenerators.items.ItemsMaterial;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class MaterialTab extends CreativeTabs {

    public MaterialTab() {
        super("material_tap");
    }

    @Override
    @Nonnull
    public ItemStack getTabIconItem() {
        return new ItemStack(ItemsMaterial.gearGoldPlated);
    }
}
