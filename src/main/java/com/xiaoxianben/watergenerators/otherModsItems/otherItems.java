package com.xiaoxianben.watergenerators.otherModsItems;

import com.xiaoxianben.watergenerators.items.ItemMaterial;
import net.minecraft.item.Item;

public class otherItems {
    public static Item registryMaterial(String registryName) {
        return new ItemMaterial(registryName);
    }
}