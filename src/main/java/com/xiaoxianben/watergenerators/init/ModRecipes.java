package com.xiaoxianben.watergenerators.init;

import com.xiaoxianben.watergenerators.blocks.*;
import com.xiaoxianben.watergenerators.items.ItemsMaterial;
import com.xiaoxianben.watergenerators.otherModsItems.TFBlocks;
import com.xiaoxianben.watergenerators.otherModsItems.TFItems;
import com.xiaoxianben.watergenerators.recipe.recipeList;
import com.xiaoxianben.watergenerators.util.ModInformation;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nullable;
import java.util.Objects;

public class ModRecipes {
    public static void init() {
        recipeList.init();
        GameRegistry.addSmelting(ModItems.ductShell_bank, new ItemStack(ModItems.ductShell, 8), 0.5f);

        GameRegistry.addShapedRecipe(ModItems.GOLD_PLATED_IRON_INGOT.getRegistryName(),
                new ResourceLocation(ModInformation.MOD_ID, "ingot"),
                new ItemStack(ModItems.GOLD_PLATED_IRON_INGOT, 8),
                "III",
                "IGI",
                "III",
                'I', "ingotIron",
                'G', "ingotGold");
        GameRegistry.addShapedRecipe(Item.getItemFromBlock(ModBlocks.GOLD_PLATED_IRON_BLOCK).getRegistryName(),
                new ResourceLocation(ModInformation.MOD_ID, "block"),
                new ItemStack(Item.getItemFromBlock(ModBlocks.GOLD_PLATED_IRON_BLOCK), 8),
                "III",
                "IGI",
                "III",
                'I', "blockIron",
                'G', "blockGold");
        GameRegistry.addShapedRecipe(ModItems.information_finder.getRegistryName(), null, ModItems.information_finder.getDefaultInstance(),
                "IBI",
                "IRI",
                "IGI",
                'I', "ingotIron",
                'B', "blockGlassColorless",
                'R', "dustRedstone",
                'G', "ingotGoldPlatedIron");
    }

    public static void registryBlock(Item output, Item ingot) {
        Object[] params = {
                "III",
                "III",
                "III",
                'I', ingot
        };
        GameRegistry.addShapedRecipe(new ResourceLocation(Objects.requireNonNull(output.getRegistryName()).toString() + '1'), new ResourceLocation(ModInformation.MOD_ID, "block"),
                output.getDefaultInstance(), params);
        GameRegistry.addShapelessRecipe(new ResourceLocation(Objects.requireNonNull(ingot.getRegistryName()).toString() + '1'), new ResourceLocation(ModInformation.MOD_ID, "ingot"),
                new ItemStack(ingot, 9), Ingredient.fromItem(output));
    }

    /**
     * @param output   要输出的物品
     * @param block    合成物品的前置材料，可为 null
     * @param oreIngot 合成物品的基础材料，可为 null
     */
    public static void registryShell(Block output, @Nullable Block block, @Nullable String oreIngot) {
        Object[] params = {
                "OLO",
                "LIL",
                "OLO",
                'O', "ingotIron",
                'I', block == null ? "ingotIron" : block,
                'L', oreIngot == null ? Items.AIR : oreIngot
        };

        GameRegistry.addShapedRecipe(output.getRegistryName(), new ResourceLocation(ModInformation.MOD_ID, "shell"),
                Item.getItemFromBlock(output).getDefaultInstance(), params);
    }

    public static void registryGear(Item output, String materialOreName) {
        Object[] params = {
                " O ",
                "OGO",
                " O ",
                'O', materialOreName,
                'G', "ingotIron"
        };
        GameRegistry.addShapedRecipe(output.getRegistryName(), new ResourceLocation(ModInformation.MOD_ID, "gear"),
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
        GameRegistry.addShapedRecipe(output.getRegistryName(), new ResourceLocation(ModInformation.MOD_ID, "turbineRotor"), output.getDefaultInstance(), params);
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
        GameRegistry.addShapedRecipe(output.getRegistryName(), new ResourceLocation(ModInformation.MOD_ID, "coil"),
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
        GameRegistry.addShapedRecipe(output.getRegistryName(), new ResourceLocation(ModInformation.MOD_ID, "duct_coil"),
                output.getDefaultInstance(), params);
    }

    public static void registryGenerator(BlockGeneratorBasic output, ItemStack conduit, Item turbineRotor, String gearOreName, BlockMachineShell blockMachineShell, Block oldGenerator) {
        registryGenerator_main(output, conduit, turbineRotor, gearOreName, blockMachineShell, Item.getItemFromBlock(oldGenerator));
    }

    /**
     *
     */
    public static void registryGenerator_main(BlockGeneratorBasic output, ItemStack conduit, Item turbineRotor, String gearOreName, BlockMachineShell blockMachineShell, Item oldGenerator) {
        Object[] params = {
                "FZF",
                "DCD",
                "FGF",
                'F', blockMachineShell,
                'D', conduit,
                'Z', turbineRotor,
                'G', gearOreName,
                'C', oldGenerator
        };

        String[] nameStr = Objects.requireNonNull(output.getRegistryName()).getResourcePath().split("_");

        // 输出的方块是否为 水发电机
        if (Objects.equals(nameStr[0], "water")) {
            Item turbineG = Objects.requireNonNull(Item.getByNameOrId(ModInformation.MOD_ID + ":turbine_generator_" + nameStr[2]));
            Item waterG = Item.getItemFromBlock(output);

            GameRegistry.addShapelessRecipe(
                    new ResourceLocation(ModInformation.MOD_ID, waterG.getRegistryName().getResourcePath() + "_2"),
                    new ResourceLocation(ModInformation.MOD_ID, "generator2"),

                    waterG.getDefaultInstance(),

                    Ingredient.fromItem(turbineG),
                    Ingredient.fromItem(Items.WATER_BUCKET));

            GameRegistry.addShapelessRecipe(
                    new ResourceLocation(ModInformation.MOD_ID, turbineG.getRegistryName().getResourcePath() + "_2"),
                    new ResourceLocation(ModInformation.MOD_ID, "generator2"),

                    turbineG.getDefaultInstance(),

                    Ingredient.fromItem(waterG),
                    Ingredient.fromItem(ItemsMaterial.coilIron));

        }

        GameRegistry.addShapedRecipe(
                output.getRegistryName(),
                new ResourceLocation(ModInformation.MOD_ID, "generator"),
                Item.getItemFromBlock(output).getDefaultInstance(),
                params);
    }

    public static void registryGenerator_old(BlockGeneratorBasic output, byte level, byte generatorTab) {
        Object[] material = getGeneratorMaterial(level, generatorTab);
        if (material[0] == null || material[1] == Items.AIR) return;
        String gearOreName = (String) material[0];

        ItemStack conduit = ((Item) material[1]).getDefaultInstance();
        if (level > 5) {
            conduit = new ItemStack(conduit.getItem(), 1, level - 6);
        }

        Item turbineRotor = (Item) material[2];
        BlockMachineShell blockMachineShell = (BlockMachineShell) material[4];
        Item oldGenerator = material[3] instanceof Block ? Item.getItemFromBlock((Block) material[3]) : (Item) material[3];

        registryGenerator_main(output, conduit, turbineRotor, gearOreName, blockMachineShell, oldGenerator);
    }

    public static Object[] getGeneratorMaterial(byte level, byte generatorTab) {
        Object[] params = new Object[5];
        switch (level) {
            case 1:
                params[0] = "gearIron";
                params[1] = ItemsMaterial.conduitIron;
                params[2] = ItemsMaterial.turbineRotorIron;
                params[3] = generatorTab == 1 ? ItemsMaterial.coilIron : Items.WATER_BUCKET;
                params[4] = BlocksMachine.machineShells[0];
                break;
            case 2:
                params[0] = "gearGoldPlated";
                params[1] = ItemsMaterial.conduitGoldPlated;
                params[2] = ItemsMaterial.turbineRotorGoldPlated;
                params[3] = generatorTab == 1 ? BlocksGenerator.TURBINE_GENERATOR_LEVEL1 : BlocksGenerator.WATER_GENERATOR_LEVEL1;
                params[4] = BlocksMachine.machineShells[1];
                break;
            case 3:
                params[0] = "gearDiamond";
                params[1] = ItemsMaterial.conduitDiamond;
                params[2] = ItemsMaterial.turbineRotorDiamond;
                params[3] = generatorTab == 1 ? BlocksGenerator.TURBINE_GENERATOR_LEVEL2 : BlocksGenerator.WATER_GENERATOR_LEVEL2;
                params[4] = BlocksMachine.machineShells[2];
                break;
            case 4:
                params[0] = "gearObsidian";
                params[1] = ItemsMaterial.conduitObsidian;
                params[2] = ItemsMaterial.turbineRotorObsidian;
                params[3] = generatorTab == 1 ? BlocksGenerator.TURBINE_GENERATOR_LEVEL3 : BlocksGenerator.WATER_GENERATOR_LEVEL3;
                params[4] = BlocksMachine.machineShells[3];
                break;
            case 5:
                params[0] = "gearEmerald";
                params[1] = ItemsMaterial.conduitEmerald;
                params[2] = ItemsMaterial.turbineRotorEmerald;
                params[3] = generatorTab == 1 ? BlocksGenerator.TURBINE_GENERATOR_LEVEL4 : BlocksGenerator.WATER_GENERATOR_LEVEL4;
                params[4] = BlocksMachine.machineShells[4];
                break;
            case 6:
                params[0] = "gearSteel";
                params[1] = TFItems.TFConduit;
                params[2] = TFItems.turbineRotorSteel;
                params[3] = generatorTab == 1 ? BlocksGenerator.TURBINE_GENERATOR_LEVEL5 : BlocksGenerator.WATER_GENERATOR_LEVEL5;
                params[4] = TFBlocks.machineShells[0];
                break;
            case 7:
                params[0] = "gearInvar";
                params[1] = TFItems.TFConduit;
                params[2] = TFItems.turbineRotorInvar;
                params[3] = generatorTab == 1 ? TFBlocks.TURBINE_GENERATOR_Steel : TFBlocks.WATER_GENERATOR_Steel;
                params[4] = TFBlocks.machineShells[1];
                break;
            case 8:
                params[0] = "gearElectrum";
                params[1] = TFItems.TFConduit;
                params[2] = TFItems.turbineRotorElectrum;
                params[3] = generatorTab == 1 ? TFBlocks.TURBINE_GENERATOR_Invar : TFBlocks.WATER_GENERATOR_Invar;
                params[4] = TFBlocks.machineShells[2];
                break;
            case 9:
                params[0] = "gearSignalum";
                params[1] = TFItems.TFConduit;
                params[2] = TFItems.turbineRotorSignalum;
                params[3] = generatorTab == 1 ? TFBlocks.TURBINE_GENERATOR_Electrum : TFBlocks.WATER_GENERATOR_Electrum;
                params[4] = TFBlocks.machineShells[3];
                break;
            case 10:
                params[0] = "gearEnderium";
                params[1] = TFItems.TFConduit;
                params[2] = TFItems.turbineRotorEnderium;
                params[3] = generatorTab == 1 ? TFBlocks.TURBINE_GENERATOR_Signalum : TFBlocks.WATER_GENERATOR_Signalum;
                params[4] = TFBlocks.machineShells[4];
                break;
        }
        return params;
    }

    public static void registryMachineVaporization(BlockMachine output, BlockMachineShell machineShell, Item conduit, String gearOre) {
        Object[] params = {
                "FCF",
                "DGD",
                "FCF",
                'F', machineShell,
                'D', Items.BUCKET,
                'C', conduit,
                'G', gearOre
        };

        GameRegistry.addShapedRecipe(
                output.getRegistryName(),
                new ResourceLocation(ModInformation.MOD_ID, "machine"),
                Item.getItemFromBlock(output).getDefaultInstance(),
                params);
    }
}
