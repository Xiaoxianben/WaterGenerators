package com.xiaoxianben.watergenerators.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Objects;

public class ItemBlockPrivate extends ItemBlock {
    public ItemBlockPrivate(Block block) {
        super(block);
        Objects.requireNonNull(block.getRegistryName(), "Block " + block + " must have a registry name");
        this.setRegistryName(block.getRegistryName());
    }

    @Nonnull
    public String getItemStackDisplayName(@Nonnull ItemStack stack) {
        return this.getBlock().getLocalizedName().trim();
    }

}
