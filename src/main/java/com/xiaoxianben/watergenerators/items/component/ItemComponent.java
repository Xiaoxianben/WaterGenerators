package com.xiaoxianben.watergenerators.items.component;

import com.xiaoxianben.watergenerators.items.ItemBase;
import com.xiaoxianben.watergenerators.util.ModInformation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.List;

public class ItemComponent extends ItemBase {

    public String componentName;

    public ItemComponent(String name) {
        super("component_" + name);
        this.componentName = name;
    }

    @ParametersAreNonnullByDefault
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("item." + ModInformation.MOD_ID + "-component.information"));
        tooltip.add(I18n.format("item." + ModInformation.MOD_ID + "-component_" + this.componentName + ".information"));
    }

}
