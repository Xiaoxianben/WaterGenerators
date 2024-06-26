package com.xiaoxianben.watergenerators.items;

import com.xiaoxianben.watergenerators.WaterGenerators;
import net.minecraft.item.Item;

import java.util.LinkedHashSet;

public class ItemMaterial extends ItemBase {
    public ItemMaterial(String name, LinkedHashSet<Item> set) {
        super(name, WaterGenerators.MATERIAL_TAB, set);
    }
}
