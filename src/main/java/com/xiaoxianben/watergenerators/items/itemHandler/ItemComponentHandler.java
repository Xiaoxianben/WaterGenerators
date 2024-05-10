package com.xiaoxianben.watergenerators.items.itemHandler;

import com.xiaoxianben.watergenerators.items.component.ItemComponent;
import com.xiaoxianben.watergenerators.items.component.ItemsComponent;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ItemComponentHandler extends ItemStackHandler {

    public static List<ItemComponent> canPutItem_generator = Collections.singletonList(ItemsComponent.component_powerGeneration);
    public static List<ItemComponent> canPutItem_fluidGenerator = Arrays.asList(ItemsComponent.component_extract, ItemsComponent.component_powerGeneration);


    public List<ItemComponent> canPutItem;

    /**
     * @param level 等级, 必须 >= 1
     */
    public ItemComponentHandler(float level, @Nullable List<ItemComponent> canPutItem) {
        super(canPutItem != null ? canPutItem.size() : 0, level);
        this.canPutItem = canPutItem != null ? canPutItem : new ArrayList<>();
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        boolean isTrueSlot = false;
        for (ItemComponent itemComponent : canPutItem) {
            if (itemComponent == stack.getItem()) {
                isTrueSlot = true;
                break;
            }
        }
        return super.isItemValid(slot, stack) && isTrueSlot;
    }

    public int getComponentCount(ItemComponent itemComponent) {
        int count = 0;
        for (ItemStack stack : this.stacks) {
            if (stack.getItem() == itemComponent) {
                count += stack.getCount();
            }
        }
        return count;
    }

}
