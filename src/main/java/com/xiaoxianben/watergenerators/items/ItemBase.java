package com.xiaoxianben.watergenerators.items;

import com.xiaoxianben.watergenerators.WaterGenerators;
import com.xiaoxianben.watergenerators.api.IHasModel;
import com.xiaoxianben.watergenerators.util.ModInformation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

public class ItemBase extends Item implements IHasModel {
    public ItemBase(String name, CreativeTabs tab, LinkedHashSet<Item> linkedHashSet) {
        setUnlocalizedName(ModInformation.MOD_ID + '-' + name);
        setRegistryName(name);

        setCreativeTab(tab);
        linkedHashSet.add(this);
    }

    public ItemBase(String name) {
        this(name, WaterGenerators.Item_TAB, Objects.requireNonNull(WaterGenerators.ITEMS));
    }

    @ParametersAreNonnullByDefault
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (I18n.hasKey(this.getUnlocalizedName() + ".tooltip")) {
            tooltip.addAll(Arrays.asList(I18n.format(this.getUnlocalizedName() + ".tooltip").split("\n")));
        }
    }


    @Override
    public void registerModels() {
        WaterGenerators.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
