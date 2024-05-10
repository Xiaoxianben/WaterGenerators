package com.xiaoxianben.watergenerators.items.itemHandler;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import java.util.Objects;

public class ItemStackNBTUnit {
    public static NBTTagCompound getNBT(ItemStack stack) {
        NBTTagCompound itemNBT = new NBTTagCompound();

        ResourceLocation resourcelocation = stack.getItem().getRegistryName();
        itemNBT.setString("id", resourcelocation == null ? "minecraft:air" : resourcelocation.toString());
        itemNBT.setInteger("Count", stack.getCount());
        itemNBT.setShort("Damage", (short) stack.getMetadata());

        if (stack.getTagCompound() != null) {
            itemNBT.setTag("tag", stack.getTagCompound());
        }

        if (stack.writeToNBT(new NBTTagCompound()).hasKey("ForgeCaps")) {
            itemNBT.setTag("ForgeCaps", stack.writeToNBT(new NBTTagCompound()).getCompoundTag("ForgeCaps"));
        }

        return itemNBT;
    }

    public static ItemStack getItemStack(NBTTagCompound nbt) {
        Item item = nbt.hasKey("id") ? Item.getByNameOrId(nbt.getString("id")) : Items.AIR;

        ItemStack itemStack = new ItemStack(Objects.requireNonNull(item), nbt.getInteger("Count"), nbt.getShort("Damage"), nbt.getCompoundTag("ForgeCaps"));

        if (nbt.hasKey("tag")) {
            itemStack.setTagCompound(nbt.getCompoundTag("tag"));
        }

        return itemStack;
    }
}
