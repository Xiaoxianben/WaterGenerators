package com.xiaoxianben.watergenerators.items.itemHandler;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;

public class ItemStackHandler extends net.minecraftforge.items.ItemStackHandler {

    /**
     * @param size 槽位数量
     */
    public ItemStackHandler(int size) {
        super(size);
    }

//    @Override
//    public int getSlotLimit(int slot) {
//        return (int) (64 * this.level);
//    }
//
//    protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
//        return Math.min(getSlotLimit(slot), Integer.MAX_VALUE);
//    }

    @Override
    @Nonnull
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (isItemValid(slot, stack)) return super.insertItem(slot, stack, simulate);
        return stack;
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return slot < this.stacks.size();
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagList nbtTagList = new NBTTagList();
        for (int i = 0; i < stacks.size(); i++) {
            if (!stacks.get(i).isEmpty()) {
                NBTTagCompound itemTag = ItemStackNBTUnit.getNBT(stacks.get(i));
                itemTag.setInteger("Slot", i);
                nbtTagList.appendTag(itemTag);
            }
        }
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setTag("Items", nbtTagList);
        nbt.setInteger("Size", stacks.size());
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        setSize(nbt.hasKey("Size", Constants.NBT.TAG_INT) ? nbt.getInteger("Size") : stacks.size());
        NBTTagList tagList = nbt.getTagList("Items", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound itemTags = tagList.getCompoundTagAt(i);
            int slot = itemTags.getInteger("Slot");

            if (slot >= 0 && slot < stacks.size()) {
                stacks.set(slot, ItemStackNBTUnit.getItemStack(itemTags));
            }
        }
        onLoad();
    }

}
