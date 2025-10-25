package com.xiaoxianben.watergenerators.init;

import com.xiaoxianben.watergenerators.items.material.ItemMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.EnumMap;

public enum EnumModItems {
    OTHER(null),
    GEAR((name) -> new ItemMaterial("gear_" + name)),
    TURBINE_ROTOR((name) -> new ItemMaterial("turbine_rotor_" + name)),
    COIL((name) -> new ItemMaterial("coil_" + name)),
    CONDUIT((name) -> new ItemMaterial("conduit_" + name));


    public final EnumMap<EnumModRegister, ItemStack[]> itemMap = new EnumMap<>(EnumModRegister.class);
    private final MaterialCreate func;

    EnumModItems(MaterialCreate func) {
        this.func = func;
    }

    public void addItems(EnumModRegister register, Item[] items) {
        addItems(register, Arrays.stream(items).map(Item::getDefaultInstance).toArray(ItemStack[]::new));
    }
    public void addItems(EnumModRegister register, ItemStack[] items) {
        itemMap.put(register, items);
    }

    public Item create(String levelName) {
        return func.create(levelName);
    }


    interface MaterialCreate {
        Item create(String levelName);
    }
}
