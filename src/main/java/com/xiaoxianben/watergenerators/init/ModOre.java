package com.xiaoxianben.watergenerators.init;

import com.xiaoxianben.watergenerators.api.IModInit;
import com.xiaoxianben.watergenerators.items.ItemsMaterial;
import net.minecraftforge.oredict.OreDictionary;

public class ModOre implements IModInit {
    @Override
    public void init() {

        OreDictionary.registerOre("itemConduitBinder", ModItems.ductShell.getDefaultInstance());
        OreDictionary.registerOre("itemBinderComposite", ModItems.ductShell_bank.getDefaultInstance());
        OreDictionary.registerOre("ingotGoldPlatedIron", ModItems.GOLD_PLATED_IRON_INGOT.getDefaultInstance());

        String[] gearOreName = {"gearIron", "gearGoldPlatedIron", "gearDiamond", "gearObsidian", "gearEmerald"};
        for (int i = 0; i < ItemsMaterial.gears.length; i++) {
            OreDictionary.registerOre(gearOreName[i], ItemsMaterial.gears[i].getDefaultInstance());
        }

    }

    public void preInit() {
    }
}
