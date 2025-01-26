package com.xiaoxianben.watergenerators.items;

import com.xiaoxianben.watergenerators.WaterGenerators;
import com.xiaoxianben.watergenerators.api.IHasModel;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.List;

public class ItemBase extends Item implements IHasModel {
    public ItemBase(String name, CreativeTabs tab) {
        setUnlocalizedName(WaterGenerators.MOD_ID + '-' + name);
        setRegistryName(name);

        setCreativeTab(tab);
    }

    public ItemBase(String name) {
        this(name, WaterGenerators.Item_TAB);
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
