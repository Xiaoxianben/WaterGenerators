package com.xiaoxianben.watergenerators.otherModsItems;

import com.xiaoxianben.watergenerators.API.IHasInit;
import com.xiaoxianben.watergenerators.Main;
import com.xiaoxianben.watergenerators.items.ItemBase;
import net.minecraft.item.Item;

public class otherItems implements IHasInit {
    @Override
    public void init() {
    }

    @Override
    public void initRecipes() {
    }

    public Item registryMaterial(String registryName) {
        return new ItemBase(registryName, Main.MATERIAL_TAB);
    }

}