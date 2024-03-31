package com.xiaoxianben.watergenerators.items;

import com.xiaoxianben.watergenerators.API.IHasModel;
import com.xiaoxianben.watergenerators.Main;
import com.xiaoxianben.watergenerators.util.ModInformation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item implements IHasModel {
    public ItemBase(String name, CreativeTabs tab) {
        setUnlocalizedName(ModInformation.MOD_ID + '.' + name);
        setRegistryName(name);

        setCreativeTab(tab);
        Main.ITEMS.add(this);
    }

    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
