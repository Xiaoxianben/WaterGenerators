package com.xiaoxianben.watergenerators.items;

import com.xiaoxianben.watergenerators.API.IHasInit;
import com.xiaoxianben.watergenerators.Main;
import com.xiaoxianben.watergenerators.init.ModRecipes;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Loader;

public class ItemsMaterial implements IHasInit {
    // 齿轮
    public static Item gearIron;
    public static Item gearGoldPlated;
    public static Item gearDiamond;
    public static Item gearObsidian;
    public static Item gearEmerald;
    // 导线
    public static Item coilIron;
    public static Item coilGoldPlated;
    public static Item coilDiamond;
    public static Item coilObsidian;
    public static Item coilEmerald;
    public static Item coilSteel;
    public static Item coilInvar;
    public static Item coilElectrum;
    public static Item coilSignalum;
    public static Item coilEnderium;
    // 导管
    public static Item conduitIron;
    public static Item conduitGoldPlated;
    public static Item conduitDiamond;
    public static Item conduitObsidian;
    public static Item conduitEmerald;
    public static Item conduitSteel;
    public static Item conduitInvar;
    public static Item conduitElectrum;
    public static Item conduitSignalum;
    public static Item conduitEnderium;
    // 涡轮装片
    public static Item turbineRotorIron;
    public static Item turbineRotorGoldPlated;
    public static Item turbineRotorDiamond;
    public static Item turbineRotorObsidian;
    public static Item turbineRotorEmerald;
    public static Item turbineRotorSteel;
    public static Item turbineRotorInvar;
    public static Item turbineRotorElectrum;
    public static Item turbineRotorSignalum;
    public static Item turbineRotorEnderium;

    public void init() {
        boolean hasTF = Loader.isModLoaded("thermalfoundation");
        // 齿轮
        gearIron = registryMaterial("gear_iron");
        gearGoldPlated = registryMaterial("gear_goldPlated");
        gearDiamond = registryMaterial("gear_diamond");
        gearObsidian = registryMaterial("gear_obsidian");
        gearEmerald = registryMaterial("gear_emerald");
        // 线圈
        coilIron = registryMaterial("coil_iron");
        coilGoldPlated = registryMaterial("coil_goldPlated");
        coilDiamond = registryMaterial("coil_diamond");
        coilObsidian = registryMaterial("coil_obsidian");
        coilEmerald = registryMaterial("coil_emerald");
        if (hasTF) {
            coilSteel = registryMaterial("coil_steel");
            coilInvar = registryMaterial("coil_invar");
            coilElectrum = registryMaterial("coil_electrum");
            coilSignalum = registryMaterial("coil_signalum");
            coilEnderium = registryMaterial("coil_enderium");

        }
        // 导线
        conduitIron = registryMaterial("conduit_iron");
        conduitGoldPlated = registryMaterial("conduit_goldPlated");
        conduitDiamond = registryMaterial("conduit_diamond");
        conduitObsidian = registryMaterial("conduit_obsidian");
        conduitEmerald = registryMaterial("conduit_emerald");
        if (hasTF) {
            conduitSteel = registryMaterial("conduit_steel");
            conduitInvar = registryMaterial("conduit_invar");
            conduitElectrum = registryMaterial("conduit_electrum");
            conduitSignalum = registryMaterial("conduit_signalum");
            conduitEnderium = registryMaterial("conduit_enderium");
        }
        // 涡轮转片
        turbineRotorIron = registryMaterial("turbine_rotor_iron");
        turbineRotorGoldPlated = registryMaterial("turbine_rotor_goldPlated");
        turbineRotorDiamond = registryMaterial("turbine_rotor_diamond");
        turbineRotorObsidian = registryMaterial("turbine_rotor_obsidian");
        turbineRotorEmerald = registryMaterial("turbine_rotor_emerald");
        if (hasTF) {
            turbineRotorSteel = registryMaterial("turbine_rotor_steel");
            turbineRotorInvar = registryMaterial("turbine_rotor_invar");
            turbineRotorElectrum = registryMaterial("turbine_rotor_electrum");
            turbineRotorSignalum = registryMaterial("turbine_rotor_signalum");
            turbineRotorEnderium = registryMaterial("turbine_rotor_enderium");
        }
    }
    public void initRegistry() {
        boolean hasTF = Loader.isModLoaded("thermalfoundation");
        // 齿轮
        ModRecipes.registryGear(gearIron, "ingotIron");
        ModRecipes.registryGear(gearGoldPlated, "ingotGoldPlatedIron");
        ModRecipes.registryGear(gearObsidian, "obsidian");
        ModRecipes.registryGear(gearEmerald, "gemEmerald");
        ModRecipes.registryGear(gearDiamond, "gemDiamond");

        // 线圈
        ModRecipes.registryCoil(coilIron, "ingotIron", Items.AIR);
        ModRecipes.registryCoil(coilGoldPlated, "ingotGoldPlatedIron", coilIron);
        ModRecipes.registryCoil(coilDiamond, "gemDiamond", coilGoldPlated);
        ModRecipes.registryCoil(coilObsidian, "obsidian", coilDiamond);
        ModRecipes.registryCoil(coilEmerald, "gemEmerald", coilObsidian);
        if (hasTF) {
            ModRecipes.registryCoil(coilSteel, "ingotSteel", coilEmerald);
            ModRecipes.registryCoil(coilInvar, "ingotInvar", coilSteel);
            ModRecipes.registryCoil(coilElectrum, "ingotElectrum", coilInvar);
            ModRecipes.registryCoil(coilSignalum, "ingotSignalum", coilElectrum);
            ModRecipes.registryCoil(coilEnderium, "ingotEnderium", coilSignalum);
        }
        // 导管
        ModRecipes.registryConduit(conduitIron, coilIron);
        ModRecipes.registryConduit(conduitGoldPlated, coilGoldPlated);
        ModRecipes.registryConduit(conduitDiamond, coilDiamond);
        ModRecipes.registryConduit(conduitObsidian, coilObsidian);
        ModRecipes.registryConduit(conduitEmerald, coilEmerald);
        if (hasTF) {
            ModRecipes.registryConduit(conduitSteel, coilSteel);
            ModRecipes.registryConduit(conduitInvar, coilInvar);
            ModRecipes.registryConduit(conduitElectrum, coilElectrum);
            ModRecipes.registryConduit(conduitSignalum, coilSignalum);
            ModRecipes.registryConduit(conduitEnderium, coilEnderium);
        }
        // 涡轮转片
        ModRecipes.registryTurbineRotor(turbineRotorIron, "ingotIron", "gearIron");
        ModRecipes.registryTurbineRotor(turbineRotorGoldPlated, "ingotGoldPlatedIron", "gearGoldPlated");
        ModRecipes.registryTurbineRotor(turbineRotorDiamond, "gemDiamond", "gearDiamond");
        ModRecipes.registryTurbineRotor(turbineRotorObsidian, "obsidian", "gearObsidian");
        ModRecipes.registryTurbineRotor(turbineRotorEmerald, "gemEmerald", "gearEmerald");
        if (hasTF) {
            ModRecipes.registryTurbineRotor(turbineRotorSteel, "ingotSteel", "gearSteel");
            ModRecipes.registryTurbineRotor(turbineRotorInvar, "ingotInvar", "gearInvar");
            ModRecipes.registryTurbineRotor(turbineRotorElectrum, "ingotElectrum", "gearElectrum");
            ModRecipes.registryTurbineRotor(turbineRotorSignalum, "ingotSignalum", "gearSignalum");
            ModRecipes.registryTurbineRotor(turbineRotorEnderium, "ingotEnderium", "gearEnderium");
        }
    }
    public Item registryMaterial(String registryName) {
        return new ItemBase(registryName, Main.MATERIAL_TAB);
    }
}
