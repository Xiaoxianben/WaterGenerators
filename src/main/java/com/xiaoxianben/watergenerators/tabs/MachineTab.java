package com.xiaoxianben.watergenerators.tabs;

import com.xiaoxianben.watergenerators.init.ModBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class MachineTab extends CreativeTabs {

    public MachineTab() {
        super("machine_tap");
    }

    @Override
    @Nonnull
    public ItemStack getTabIconItem() {
        return new ItemStack(ModBlocks.machineShell_frame);
    }
}
