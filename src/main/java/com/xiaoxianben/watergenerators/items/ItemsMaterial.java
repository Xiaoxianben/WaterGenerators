package com.xiaoxianben.watergenerators.items;

import com.xiaoxianben.watergenerators.api.IHasInit;
import com.xiaoxianben.watergenerators.init.ModItems;
import com.xiaoxianben.watergenerators.init.ModRecipes;
import com.xiaoxianben.watergenerators.items.material.ItemMaterial;
import com.xiaoxianben.watergenerators.items.material.ItemTurbineRotor;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;

import java.util.LinkedHashSet;

public class ItemsMaterial implements IHasInit {
    public static Item[] gears = new Item[5];
    public static Item[] coils = new Item[5];
    public static Item[] conduits = new Item[5];
    public static Item[] turbines = new Item[5];

    public String[] itemName = {"iron", "goldPlated", "diamond", "obsidian", "emerald"};


    public void init() {
        for (int i = 0; i < itemName.length; i++) {
            int level = i + 1;
            gears[i] = registryMaterial("gear_" + itemName[i], ModItems.allGear);
            coils[i] = registryMaterial("coil_" + itemName[i], ModItems.allCoil);
            conduits[i] = registryMaterial("conduit_" + itemName[i], ModItems.allConduit);
            turbines[i] = new ItemTurbineRotor("level" + level);
        }
    }

    public void initRecipes() {

        OreDictionary.registerOre("turbineRotorIron", turbines[0]);
        OreDictionary.registerOre("turbineRotorGoldPlatedIron", turbines[1]);
        OreDictionary.registerOre("turbineRotorDiamond", turbines[2]);
        OreDictionary.registerOre("turbineRotorObsidian", turbines[3]);
        OreDictionary.registerOre("turbineRotorEmerald", turbines[4]);

        String[] OreName = {"ingotIron", "ingotGoldPlatedIron", "gemDiamond", "obsidian", "gemEmerald"};
        String[] gearOreName = {"gearIron", "gearGoldPlatedIron", "gearDiamond", "gearObsidian", "gearEmerald"};
        // 齿轮
        for (int i = 0; i < itemName.length; i++) {
            ModRecipes.addRecipeGear(gears[i], OreName[i]);
            ModRecipes.addRecipeCoil(coils[i], i == 0 ? Items.AIR : coils[i - 1], OreName[i]);
            ModRecipes.addRecipeConduit(conduits[i], coils[i]);
            ModRecipes.addRecipeTurbineRotor(turbines[i], OreName[i], gearOreName[i]);
        }
    }

    public Item registryMaterial(String registryName, LinkedHashSet<Item> set) {
        return new ItemMaterial(registryName, set);
    }
}
