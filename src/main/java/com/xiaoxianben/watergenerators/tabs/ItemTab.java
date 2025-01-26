package com.xiaoxianben.watergenerators.tabs;

import com.xiaoxianben.watergenerators.init.modRegister.MinecraftRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ItemTab extends CreativeTabs {

    public ItemTab() {
        super("item_tap");
    }

    @Nonnull
    @Override
    public ItemStack getTabIconItem() {

        return new ItemStack(MinecraftRegister.information_finder);

    }


}
