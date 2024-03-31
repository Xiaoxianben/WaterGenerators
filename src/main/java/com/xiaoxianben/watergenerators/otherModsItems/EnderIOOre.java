package com.xiaoxianben.watergenerators.otherModsItems;

import net.minecraftforge.oredict.OreDictionary;

public class EnderIOOre {
    public static void init() {
        for (int i = 0; i < EnderIOInit.enderIO_Gear.length; i++) {
            if (EnderIOInit.booleans[i + 2])
                OreDictionary.registerOre("gear" + EnderIOInit.EnderIOIngotOre[i + 2].replace("Alloy", ""), EnderIOInit.enderIO_Gear[i]);
        }
    }
}
