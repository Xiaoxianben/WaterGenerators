package com.xiaoxianben.watergenerators.init;

import com.xiaoxianben.watergenerators.items.ItemsMaterial;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashMap;

public class ModOre {
    public static void preInit() {
        OreDictionary.registerOre("itemConduitBinder", ModItems.ductShell.getDefaultInstance());
        OreDictionary.registerOre("itemBinderComposite", ModItems.ductShell_bank.getDefaultInstance());
        OreDictionary.registerOre("ingotGoldPlatedIron", ModItems.GOLD_PLATED_IRON_INGOT.getDefaultInstance());

        OreDictionary.registerOre("gearIron", ItemsMaterial.gearIron.getDefaultInstance());
        OreDictionary.registerOre("gearGoldPlated", ItemsMaterial.gearGoldPlated.getDefaultInstance());
        OreDictionary.registerOre("gearDiamond", ItemsMaterial.gearDiamond.getDefaultInstance());
        OreDictionary.registerOre("gearObsidian", ItemsMaterial.gearObsidian.getDefaultInstance());
        OreDictionary.registerOre("gearEmerald", ItemsMaterial.gearEmerald.getDefaultInstance());
    }
}
