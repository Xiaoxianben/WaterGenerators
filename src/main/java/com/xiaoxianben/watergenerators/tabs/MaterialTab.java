package com.xiaoxianben.watergenerators.tabs;

import com.xiaoxianben.watergenerators.items.ItemsMaterial;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class MaterialTab extends CreativeTabs {

    public static List<Item> materialTabItems = new ArrayList<>();

    public MaterialTab() {
        super("material_tap");
        for (Item itemTabItem : materialTabItems) {
            if (itemTabItem instanceof ItemBlock) {
                ((ItemBlock) itemTabItem).getBlock().setCreativeTab(this);
            } else {
                itemTabItem.setCreativeTab(this);
            }
        }
    }

    @Override
    @Nonnull
    public ItemStack getTabIconItem() {
        return new ItemStack(ItemsMaterial.gearGoldPlated);
    }
}
