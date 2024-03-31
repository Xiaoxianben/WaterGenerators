package com.xiaoxianben.watergenerators.otherModsItems;

import com.xiaoxianben.watergenerators.init.ModRecipes;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class TFItems extends otherItems {
    public static Item TFConduit = new Item();
    public static Item[] TFTurbine = new Item[5];
    public static Item turbineRotorSteel;
    public static Item turbineRotorInvar;
    public static Item turbineRotorElectrum;
    public static Item turbineRotorSignalum;
    public static Item turbineRotorEnderium;

    @Override
    public void init() {
        for (int i = 0; i < 5; i++) {
            TFTurbine[i] = registryMaterial("turbine_rotor_" + TFBlocks.TFName[i]);
        }

        // 支持旧代码
        turbineRotorSteel = TFTurbine[0];
        turbineRotorInvar = TFTurbine[1];
        turbineRotorElectrum = TFTurbine[2];
        turbineRotorSignalum = TFTurbine[3];
        turbineRotorEnderium = TFTurbine[4];

    }

    @Override
    public void initRecipes() {
        TFConduit = Item.getByNameOrId("thermaldynamics:duct_0");
        if (TFConduit == null)
            TFConduit = Items.AIR;

        ModRecipes.registryTurbineRotor(turbineRotorSteel, "ingotSteel", "gearSteel");
        ModRecipes.registryTurbineRotor(turbineRotorInvar, "ingotInvar", "gearInvar");
        ModRecipes.registryTurbineRotor(turbineRotorElectrum, "ingotElectrum", "gearElectrum");
        ModRecipes.registryTurbineRotor(turbineRotorSignalum, "ingotSignalum", "gearSignalum");
        ModRecipes.registryTurbineRotor(turbineRotorEnderium, "ingotEnderium", "gearEnderium");
    }
}
