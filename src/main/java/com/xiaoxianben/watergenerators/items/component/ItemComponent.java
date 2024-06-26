package com.xiaoxianben.watergenerators.items.component;

import com.xiaoxianben.watergenerators.WaterGenerators;
import com.xiaoxianben.watergenerators.init.ModItems;
import com.xiaoxianben.watergenerators.items.ItemBase;
import com.xiaoxianben.watergenerators.util.ModInformation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Objects;

public class ItemComponent extends ItemBase {

    public String componentName;

    public ItemComponent(String name) {
        super("component_" + name, WaterGenerators.Item_TAB, Objects.requireNonNull(ModItems.allComponent));
        this.componentName = name;
    }

    @ParametersAreNonnullByDefault
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("item." + ModInformation.MOD_ID + "-component.tooltip"));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

}
