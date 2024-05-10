package com.xiaoxianben.watergenerators.otherModsItems;

import com.xiaoxianben.watergenerators.init.ModRecipes;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class EnderIOItems extends otherItems {

    public static void initGear() {
        for (int i = 2; i < 5; i++) {
            if (EnderIOInit.booleans[i]) {
                EnderIOInit.enderIO_Gear[i - 2] = registryMaterial("gear_" + EnderIOInit.EnderIOName[i].replace("alloy", ""));
            }
        }
    }

    public static void initTurbineRotor() {
        for (int i = 0; i < 5; i++) {
            if (EnderIOInit.booleans[i]) {
                EnderIOInit.enderIO_TurbineRotor[i] = registryMaterial("turbine_rotor_" + EnderIOInit.EnderIOName[i]);
            }
        }
    }

    public static void initRecipes() {
        if (EnderIOInit.booleans[1])
            EnderIOInit.enderIO_Conduit[0] = GameRegistry.findRegistry(Item.class).getValue(new ResourceLocation("enderio", "item_power_conduit"));
        if (EnderIOInit.booleans[3])
            EnderIOInit.enderIO_Conduit[1] = GameRegistry.findRegistry(Item.class).getValue(new ResourceLocation("enderio", "item_endergy_conduit"));
        // Gear
        for (int i = 0; i < EnderIOInit.enderIO_Gear.length; i++) {
            if (EnderIOInit.booleans[i + 2])
                ModRecipes.registryGear(EnderIOInit.enderIO_Gear[i], "ingot" + EnderIOInit.EnderIOIngotOre[i + 2]);
        }
        // TurbineRotor
        for (int i = 0; i < EnderIOInit.enderIO_TurbineRotor.length; i++) {
            if (EnderIOInit.booleans[i]) {
                String[] gears = {"gearVibrant", "gearDark", "gearEndSteel", "gearMelodic", "gearStellar"};
                ModRecipes.registryTurbineRotor(EnderIOInit.enderIO_TurbineRotor[i],
                        "ingot" + EnderIOInit.EnderIOIngotOre[i],
                        gears[i]);
            }
        }
    }

}
