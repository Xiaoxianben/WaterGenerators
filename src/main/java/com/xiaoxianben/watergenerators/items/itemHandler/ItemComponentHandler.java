package com.xiaoxianben.watergenerators.items.itemHandler;

import com.xiaoxianben.watergenerators.items.ItemsComponent;
import com.xiaoxianben.watergenerators.items.component.ItemComponent;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ItemComponentHandler extends ItemStackHandler {

    public static final List<ItemComponent> canPutItem_generator = Collections.singletonList(ItemsComponent.component_powerGeneration);
    public static final List<ItemComponent> canPutItem_fluidGenerator = Arrays.asList(ItemsComponent.component_extract, ItemsComponent.component_powerGeneration);
    public static final List<ItemComponent> canPutItem_vaporization = Collections.singletonList(ItemsComponent.component_efficiency);


    public final List<ItemComponent> canPutItem;


    public ItemComponentHandler(@Nullable List<ItemComponent> canPutItem) {
        super(canPutItem != null ? canPutItem.size() : 0);
        this.canPutItem = canPutItem != null ? canPutItem : new ArrayList<>();
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        boolean isTrueSlot = false;
        if (stack.getItem() instanceof ItemComponent) {
            isTrueSlot = canPutItem.indexOf((ItemComponent) stack.getItem()) == slot;
        }
        return super.isItemValid(slot, stack) && isTrueSlot;
    }

    public int getComponentCount(ItemComponent itemComponent) {
        ItemStack itemStack = this.stacks.get(canPutItem.indexOf(itemComponent));
        return itemStack.getCount();
    }

}
