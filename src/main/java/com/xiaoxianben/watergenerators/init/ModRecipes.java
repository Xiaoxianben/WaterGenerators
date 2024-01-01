package com.xiaoxianben.watergenerators.init;

import com.xiaoxianben.watergenerators.blocks.BlockTEBasic;
import com.xiaoxianben.watergenerators.blocks.BlocksGenerator;
import com.xiaoxianben.watergenerators.items.ItemsMaterial;
import com.xiaoxianben.watergenerators.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Objects;

public class ModRecipes {
    public static void init() {
        GameRegistry.addSmelting(ModItems.ductShell_bank, new ItemStack(ModItems.ductShell, 8), 0.5f);

        GameRegistry.addShapedRecipe(ModItems.GOLD_PLATED_IRON_INGOT.getRegistryName(),
                new ResourceLocation(Reference.MOD_ID, "ingot"),
                new ItemStack(ModItems.GOLD_PLATED_IRON_INGOT, 8),
                "III",
                "IGI",
                "III",
                'I', "ingotIron",
                'G', "ingotGold");
        GameRegistry.addShapedRecipe(Item.getItemFromBlock(ModBlocks.GOLD_PLATED_IRON_BLOCK).getRegistryName(),
                new ResourceLocation(Reference.MOD_ID, "block"),
                new ItemStack(Item.getItemFromBlock(ModBlocks.GOLD_PLATED_IRON_BLOCK), 8),
                "III",
                "IGI",
                "III",
                'I', "blockIron",
                'G', "blockGold");
    }

    public static void registryBlock(Item output, Item ingot) {
        Object[] params = {
                "III",
                "III",
                "III",
                'I', ingot
        };
        GameRegistry.addShapedRecipe(new ResourceLocation(Objects.requireNonNull(output.getRegistryName()).toString() + '1'), new ResourceLocation(Reference.MOD_ID, "block"),
                output.getDefaultInstance(), params);
        GameRegistry.addShapelessRecipe(new ResourceLocation(Objects.requireNonNull(ingot.getRegistryName()).toString() + '1'), new ResourceLocation(Reference.MOD_ID, "ingot"),
                new ItemStack(ingot, 9), Ingredient.fromItem(output));
    }
    public static void registryShell(Item output, Item item, String oreIngot) {
        Object[] params = {
            "OLO",
            "LIL",
            "OLO",
            'O', "ingotIron",
            'L', Objects.equals(oreIngot, "") ? Items.AIR : oreIngot,
            'I', item == Items.AIR ? "ingotIron" : item
        };

        GameRegistry.addShapedRecipe(output.getRegistryName(), new ResourceLocation(Reference.MOD_ID, "shell"),
                output.getDefaultInstance(), params);
    }
    public static void registryGear(Item output, String materialOreName) {
        Object[] params = {
            " O ",
            "OGO",
            " O ",
            'O', materialOreName,
            'G', "ingotIron"
        };
        GameRegistry.addShapedRecipe(output.getRegistryName(), new ResourceLocation(Reference.MOD_ID, "gear"),
                output.getDefaultInstance(), params);
    }
    public static void registryTurbineRotor(Item output, String inputOre, String gearOre) {
        Object[] params = {
            "OOO",
            "OGO",
            "OOO",
            'O', inputOre,
            'G', gearOre
        };
        GameRegistry.addShapedRecipe(output.getRegistryName(), new ResourceLocation(Reference.MOD_ID, "turbineRotor"), output.getDefaultInstance(), params);
    }
    public static void registryCoil(Item output, String materialOreName, Item materialItem) {
        Object[] params = {
            "OOO",
            "RCR",
            "OOO",
            'O', materialOreName,
            'C', materialItem,
            'R', "dustRedstone"
        };
        GameRegistry.addShapedRecipe(output.getRegistryName(), new ResourceLocation(Reference.MOD_ID, "coil"),
                output.getDefaultInstance(), params);
    }
    public static void registryConduit(Item output, Item item) {
        Object[] params = {
            "DDD",
            "CCC",
            "DDD",
            'C', item,
            'D', "itemConduitBinder"
        };
        GameRegistry.addShapedRecipe(output.getRegistryName(), new ResourceLocation(Reference.MOD_ID, "duct_coil"),
                output.getDefaultInstance(), params);
    }
    public static void registryGenerator(BlockTEBasic output, Item conduit, Item turbineRotor,String gearOreName, Block oldGenerator) {
        Object[] params = {
            "FZF",
            "DCD",
            "FGF",
            'F', ModBlocks.machineShell,
            'D', conduit,
            'Z', turbineRotor,
            'G', gearOreName,
            'C', oldGenerator
        };
        GameRegistry.addShapedRecipe(output.getRegistryName(), new ResourceLocation(Reference.MOD_ID, "generator"),
                Item.getItemFromBlock(output).getDefaultInstance(), params);
    }
    public static void registryGenerator_old(BlockTEBasic output, byte level, byte generatorTab) {
        Object[] material = getGeneratorMaterial(level, generatorTab);
        if (material[0] == null) return ;
        String gearOreName = (String) material[0];
        Item conduit = (Item) material[1];
        Item turbineRotor = (Item) material[2];
        Item oldGenerator = material[3] instanceof Block? Item.getItemFromBlock((Block) material[3]) : (Item) material[3];
        Object[] params = {
            "FZF",
            "DCD",
            "FGF",
            'F', ModBlocks.machineShell,
            'D', conduit,
            'Z', turbineRotor,
            'G', gearOreName,
            'C', oldGenerator
        };
        GameRegistry.addShapedRecipe(output.getRegistryName(), new ResourceLocation(Reference.MOD_ID, "generator"),
                Item.getItemFromBlock(output).getDefaultInstance(), params);
    }
    public static Object[] getGeneratorMaterial(byte level, byte generatorTab) {
        Object[] params = new Object[4];
        switch (level) {
            case 1:
                params[0] = "gearIron";
                params[1] = ItemsMaterial.conduitIron;
                params[2] = ItemsMaterial.turbineRotorIron;
                params[3] = generatorTab == 1? ItemsMaterial.coilIron : Items.WATER_BUCKET;
                break;
            case 2:
                params[0] = "gearGoldPlated";
                params[1] = ItemsMaterial.conduitGoldPlated;
                params[2] = ItemsMaterial.turbineRotorGoldPlated;
                params[3] = generatorTab == 1? BlocksGenerator.TURBINE_GENERATOR_LEVEL1 : BlocksGenerator.WATER_GENERATOR_LEVEL1;
                break;
            case 3:
                params[0] = "gearDiamond";
                params[1] = ItemsMaterial.conduitDiamond;
                params[2] = ItemsMaterial.turbineRotorDiamond;
                params[3] = generatorTab == 1? BlocksGenerator.TURBINE_GENERATOR_LEVEL2 : BlocksGenerator.WATER_GENERATOR_LEVEL2;
                break;
            case 4:
                params[0] = "gearObsidian";
                params[1] = ItemsMaterial.conduitObsidian;
                params[2] = ItemsMaterial.turbineRotorObsidian;
                params[3] = generatorTab == 1? BlocksGenerator.TURBINE_GENERATOR_LEVEL3 : BlocksGenerator.WATER_GENERATOR_LEVEL3;
                break;
            case 5:
                params[0] = "gearEmerald";
                params[1] = ItemsMaterial.conduitEmerald;
                params[2] = ItemsMaterial.turbineRotorEmerald;
                params[3] = generatorTab == 1? BlocksGenerator.TURBINE_GENERATOR_LEVEL4 : BlocksGenerator.WATER_GENERATOR_LEVEL4;
                break;
            case 6:
                params[0] = "gearSteel";
                params[1] = ItemsMaterial.conduitSteel;
                params[2] = ItemsMaterial.turbineRotorSteel;
                params[3] = generatorTab == 1? BlocksGenerator.TURBINE_GENERATOR_LEVEL5 : BlocksGenerator.WATER_GENERATOR_LEVEL5;
                break;
            case 7:
                params[0] = "gearInvar";
                params[1] = ItemsMaterial.conduitInvar;
                params[2] = ItemsMaterial.turbineRotorInvar;
                params[3] = generatorTab == 1? BlocksGenerator.TURBINE_GENERATOR_Steel : BlocksGenerator.WATER_GENERATOR_Steel;
                break;
            case 8:
                params[0] = "gearElectrum";
                params[1] = ItemsMaterial.conduitElectrum;
                params[2] = ItemsMaterial.turbineRotorElectrum;
                params[3] = generatorTab == 1? BlocksGenerator.TURBINE_GENERATOR_Invar : BlocksGenerator.WATER_GENERATOR_Invar;
                break;
            case 9:
                params[0] = "gearSignalum";
                params[1] = ItemsMaterial.conduitSignalum;
                params[2] = ItemsMaterial.turbineRotorSignalum;
                params[3] = generatorTab == 1? BlocksGenerator.TURBINE_GENERATOR_Electrum : BlocksGenerator.WATER_GENERATOR_Electrum;
                break;
            case 10:
                params[0] = "gearEnderium";
                params[1] = ItemsMaterial.conduitEnderium;
                params[2] = ItemsMaterial.turbineRotorEnderium;
                params[3] = generatorTab == 1? BlocksGenerator.TURBINE_GENERATOR_Signalum : BlocksGenerator.WATER_GENERATOR_Signalum;
                break;
        }
        return params;
    }
}
