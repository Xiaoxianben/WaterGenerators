package com.xiaoxianben.watergenerators.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ContainerBasic extends Container {

    public final List<Rectangle> rectangles = new ArrayList<>();

    public ContainerBasic(EntityPlayer player, TileEntity tileEntity) {

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
        if (tileEntity != null && tileEntity.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) {
            for (int i = 0; i < Objects.requireNonNull(tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)).getSlots(); ++i) {
                rectangles.add(new Rectangle(-18, i * 18, 18, 18));
                this.addSlotToContainer(new SlotItemHandler(tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null), i, -17, 1 + i * 18));
            }
        }

    }

    @Override
    public boolean canInteractWith(@Nonnull EntityPlayer playerIn) {
        return true;
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(@Nonnull EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemstack = stack.copy();
            if (index < 36) {
                if (!this.mergeItemStack(stack, 36, this.inventorySlots.size(), true)) {
                    int start = (index < 9) ? 9 : 0;
                    int end = (index < 9) ? this.inventorySlots.size() : 9;
                    if (!this.mergeItemStack(stack, start, end, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else {
                if (!this.mergeItemStack(stack, 0, 36, false)) {
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

    protected boolean mergeItemStack(@Nonnull ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
        // 标记是否发生更改
        boolean flag = false;
        // 当前索引
        int i = startIndex;

        // 如果为反方向，则从后向前遍历
        if (reverseDirection) {
            i = endIndex - 1;
        }

        // 如果当前格子可以放置
        if (stack.isStackable()) {
            while (!stack.isEmpty()) {
                // 如果为反方向，则判断是否到达开始索引
                if (reverseDirection) {
                    if (i < startIndex) {
                        break;
                    }
                } else if (i >= endIndex) {
                    break;
                }

                // 获取当前格子
                Slot slot = this.inventorySlots.get(i);
                ItemStack itemstack = slot.getStack();

                // 如果当前格子的物品和stack的物品相同，且stack的子类型相同，或者没有子类型，则可以合并
                if (!itemstack.isEmpty() && itemstack.getItem() == stack.getItem() && (!stack.getHasSubtypes() || stack.getMetadata() == itemstack.getMetadata()) && ItemStack.areItemStackTagsEqual(stack, itemstack)) {
                    // 计算合并后的数量
                    int j = itemstack.getCount() + stack.getCount();
                    // 获取当前格子可放置的最大数量
                    int maxSize = slot.getSlotStackLimit();

                    // 如果可以合并，则将stack设置为空，将itemstack设置为合并后的数量，并更新slot
                    if (j <= maxSize) {
                        stack.setCount(0);
                        itemstack.setCount(j);
                        slot.onSlotChanged();
                        flag = true;
                    } else if (itemstack.getCount() < maxSize) {
                        stack.shrink(maxSize - itemstack.getCount());
                        itemstack.setCount(maxSize);
                        slot.onSlotChanged();
                        flag = true;
                    }
                }

                // 更新索引
                if (reverseDirection) {
                    --i;
                } else {
                    ++i;
                }
            }
        }

        // 如果stack不为空，则尝试放入
        if (!stack.isEmpty()) {
            // 如果为反方向，则从后向前遍历
            if (reverseDirection) {
                i = endIndex - 1;
            } else {
                i = startIndex;
            }

            // 遍历到结束
            while (true) {
                // 如果为反方向，则判断是否到达开始索引
                if (reverseDirection) {
                    if (i < startIndex) {
                        break;
                    }
                } else if (i >= endIndex) {
                    break;
                }

                // 获取当前格子
                Slot slot1 = this.inventorySlots.get(i);
                ItemStack itemstack1 = slot1.getStack();

                // 如果当前格子为空，且可以放置，则放入
                if (itemstack1.isEmpty() && slot1.isItemValid(stack)) {
                    // 如果stack数量大于格子可放置数量，则放入部分stack
                    if (stack.getCount() > slot1.getSlotStackLimit()) {
                        slot1.putStack(stack.splitStack(slot1.getSlotStackLimit()));
                    } else {
                        slot1.putStack(stack.splitStack(stack.getCount()));
                    }

                    slot1.onSlotChanged();
                    flag = true;
                    break;
                }

                // 更新索引
                if (reverseDirection) {
                    --i;
                } else {
                    ++i;
                }
            }
        }

        return flag;
    }

}
