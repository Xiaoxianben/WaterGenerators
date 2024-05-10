package com.xiaoxianben.watergenerators.items;

import com.xiaoxianben.watergenerators.api.IHasInit;
import com.xiaoxianben.watergenerators.init.ModRecipes;
import com.xiaoxianben.watergenerators.otherModsItems.otherInit;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

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
    // 导管
    public static Item conduitIron;
    public static Item conduitGoldPlated;
    public static Item conduitDiamond;
    public static Item conduitObsidian;
    public static Item conduitEmerald;
    public static Item[] conduits = new Item[5];
    // 涡轮装片
    public static Item turbineRotorIron;
    public static Item turbineRotorGoldPlated;
    public static Item turbineRotorDiamond;
    public static Item turbineRotorObsidian;
    public static Item turbineRotorEmerald;
    public static Item[] turbines = new Item[5];
    public static String[] itemName = {"iron", "goldPlated", "diamond", "obsidian", "emerald"};

    public void init() {
        // 齿轮
        gearIron = registryMaterial("gear_iron");
        gearGoldPlated = registryMaterial("gear_goldPlated");
        gearDiamond = registryMaterial("gear_diamond");
        gearObsidian = registryMaterial("gear_obsidian");
        gearEmerald = registryMaterial("gear_emerald");
        otherInit.initGear();

        // 线圈
        coilIron = registryMaterial("coil_iron");
        coilGoldPlated = registryMaterial("coil_goldPlated");
        coilDiamond = registryMaterial("coil_diamond");
        coilObsidian = registryMaterial("coil_obsidian");
        coilEmerald = registryMaterial("coil_emerald");
        // 导线
        conduitIron = registryMaterial("conduit_iron");
        conduitGoldPlated = registryMaterial("conduit_goldPlated");
        conduitDiamond = registryMaterial("conduit_diamond");
        conduitObsidian = registryMaterial("conduit_obsidian");
        conduitEmerald = registryMaterial("conduit_emerald");
        conduits = new Item[]{conduitIron, conduitGoldPlated, conduitDiamond, conduitObsidian, conduitEmerald};
        // 涡轮转片
        for (int i = 0; i < 5; i++) {
            turbines[i] = registryMaterial("turbine_rotor_" + itemName[i]);
        }
        otherInit.initTurbineRotor();
        turbineRotorIron = turbines[0];
        turbineRotorGoldPlated = turbines[1];
        turbineRotorDiamond = turbines[2];
        turbineRotorObsidian = turbines[3];
        turbineRotorEmerald = turbines[4];
    }

    public void initRecipes() {
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
        // 导管
        ModRecipes.registryConduit(conduitIron, coilIron);
        ModRecipes.registryConduit(conduitGoldPlated, coilGoldPlated);
        ModRecipes.registryConduit(conduitDiamond, coilDiamond);
        ModRecipes.registryConduit(conduitObsidian, coilObsidian);
        ModRecipes.registryConduit(conduitEmerald, coilEmerald);
        // 涡轮转片
        ModRecipes.registryTurbineRotor(turbineRotorIron, "ingotIron", "gearIron");
        ModRecipes.registryTurbineRotor(turbineRotorGoldPlated, "ingotGoldPlatedIron", "gearGoldPlated");
        ModRecipes.registryTurbineRotor(turbineRotorDiamond, "gemDiamond", "gearDiamond");
        ModRecipes.registryTurbineRotor(turbineRotorObsidian, "obsidian", "gearObsidian");
        ModRecipes.registryTurbineRotor(turbineRotorEmerald, "gemEmerald", "gearEmerald");
    }

    public Item registryMaterial(String registryName) {
        return new ItemMaterial(registryName);
    }
}
