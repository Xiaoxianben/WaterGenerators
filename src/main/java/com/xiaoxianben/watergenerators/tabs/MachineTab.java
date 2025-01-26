package com.xiaoxianben.watergenerators.tabs;

import com.xiaoxianben.watergenerators.init.modRegister.MinecraftRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class MachineTab extends CreativeTabs {

    public MachineTab() {
        super("machine_tap");
    }

    @Override
    @Nonnull
    public ItemStack getTabIconItem() {
        return new ItemStack(MinecraftRegister.machineShell_frame);
    }
}
