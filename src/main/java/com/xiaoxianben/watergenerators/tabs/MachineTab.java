package com.xiaoxianben.watergenerators.tabs;

import com.xiaoxianben.watergenerators.init.ModBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class MachineTab extends CreativeTabs {

    public static List<Item> MachineTabItems = new ArrayList<>();

    public MachineTab() {
        super("machine_tap");
        for (Item itemTabItem : MachineTabItems) {
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
        return new ItemStack(ModBlocks.machineShell_frame);
    }
}
