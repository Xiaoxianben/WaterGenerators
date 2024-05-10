package com.xiaoxianben.watergenerators.tabs;

import com.xiaoxianben.watergenerators.init.ModBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ItemTab extends CreativeTabs {

    public ItemTab() {
        super("ingotBlock_tap");
    }

    @Nonnull
    @Override
    public ItemStack getTabIconItem() {

        return new ItemStack(Item.getItemFromBlock(ModBlocks.GOLD_PLATED_IRON_BLOCK));

    }


}
