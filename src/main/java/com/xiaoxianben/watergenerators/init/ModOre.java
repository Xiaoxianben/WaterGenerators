package com.xiaoxianben.watergenerators.init;

import com.xiaoxianben.watergenerators.init.modRegister.MinecraftRegister;
import net.minecraftforge.oredict.OreDictionary;

public class ModOre {
    public void init() {

        OreDictionary.registerOre("itemConduitBinder", MinecraftRegister.ductShell.getDefaultInstance());
        OreDictionary.registerOre("itemBinderComposite", MinecraftRegister.ductShell_bank.getDefaultInstance());
        OreDictionary.registerOre("ingotGoldPlatedIron", MinecraftRegister.GOLD_PLATED_IRON_INGOT.getDefaultInstance());

    }
}
