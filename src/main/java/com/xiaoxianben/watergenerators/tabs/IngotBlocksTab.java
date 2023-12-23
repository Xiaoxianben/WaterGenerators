package com.xiaoxianben.watergenerators.tabs;

import com.xiaoxianben.watergenerators.init.ModBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class IngotBlocksTab extends CreativeTabs {

    public IngotBlocksTab() {

        super("ingotBlock_tap");

    }

    @Override
    public ItemStack getTabIconItem() {

        return new ItemStack(Item.getItemFromBlock(ModBlocks.GOLD_PLATED_IRON_BLOCK));

    }


}
