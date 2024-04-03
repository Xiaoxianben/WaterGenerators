package com.xiaoxianben.watergenerators.recipe;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ListItemStack {
    public final List<ItemStack> itemStackList = new ArrayList<>();

    public void add(ItemStack itemStack) {
        this.itemStackList.add(itemStack);
    }

    public void add(List<ItemStack> itemStackList) {
        this.itemStackList.addAll(itemStackList);
    }

    public void remove(ItemStack itemStack) {
        this.itemStackList.remove(itemStack);
    }

    public void remove(List<ItemStack> itemStackList) {
        this.itemStackList.removeAll(itemStackList);
    }
}
