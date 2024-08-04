package com.xiaoxianben.watergenerators.items.material;

import com.xiaoxianben.watergenerators.WaterGenerators;
import com.xiaoxianben.watergenerators.items.ItemBase;
import net.minecraft.item.Item;

import java.util.LinkedHashSet;

public class ItemMaterial extends ItemBase {
    public ItemMaterial(String name, LinkedHashSet<Item> set) {
        super(name, WaterGenerators.MATERIAL_TAB, set);
    }
}
