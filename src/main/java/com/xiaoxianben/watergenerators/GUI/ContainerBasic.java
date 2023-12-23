package com.xiaoxianben.watergenerators.GUI;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBasic extends Container {

    public int sizeInventory = 9;

    public ContainerBasic(EntityPlayer player) {

        //将玩家物品槽第一行（快捷栏）加入容器
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142));
        }
        // 将背包槽添加进容器
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemstack = stack.copy();
            if (index < this.sizeInventory) { // 从玩家的物品栏中移动物品到容器中
                if (!this.mergeItemStack(stack, this.sizeInventory, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else { // 从容器中移动物品到玩家的物品栏中
                if (!this.mergeItemStack(stack, 0, this.sizeInventory, false)) {
                    return ItemStack.EMPTY;
                }
            }
            slot.onSlotChange(stack, itemstack);
            if (stack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
            if (stack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(playerIn, stack);
        }
        return itemstack;
    }

}
