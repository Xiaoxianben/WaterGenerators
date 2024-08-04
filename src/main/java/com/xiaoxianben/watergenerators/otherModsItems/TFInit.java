package com.xiaoxianben.watergenerators.otherModsItems;

import com.xiaoxianben.watergenerators.blocks.BlocksGenerator;
import com.xiaoxianben.watergenerators.blocks.BlocksMachine;
import com.xiaoxianben.watergenerators.blocks.generator.*;
import com.xiaoxianben.watergenerators.blocks.machine.BlockMachineShell;
import com.xiaoxianben.watergenerators.blocks.machine.BlockMachineVaporization;
import com.xiaoxianben.watergenerators.init.ModRecipes;
import com.xiaoxianben.watergenerators.items.material.ItemTurbineRotor;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class TFInit implements IOtherModInit {


    public static Item[] turbineRotors = new Item[5];

    public static BlockGeneratorTurbine[] generatorTurbine = new BlockGeneratorTurbine[5];
    public static BlockGeneratorFluid[] generatorFluid = new BlockGeneratorFluid[5];
    public static BlockGeneratorWater[] generatorWater = new BlockGeneratorWater[5];
    public static BlockGeneratorSteam[] generatorSteam = new BlockGeneratorSteam[5];

    public static BlockMachineShell[] machineShells = new BlockMachineShell[5];
    public static BlockMachineVaporization[] machineVaporization = new BlockMachineVaporization[5];


    public int[] levels = {6, 7, 8, 9, 10};
    public String[] names = {"steel", "invar", "electrum", "signalum", "enderium"};
    public String[] ingotOres = {"ingotSteel", "ingotInvar", "ingotElectrum", "ingotSignalum", "ingotEnderium"};
    public String[] gearOres = {"Steel", "Invar", "Electrum", "Signalum", "Enderium"};


    @Override
    public void initBlocks() {
        for (int i = 0; i < 5; i++) {
            int level = levels[i];
            String name = names[i];

            generatorTurbine[i] = new BlockGeneratorTurbine(level, name);
            generatorFluid[i] = new BlockGeneratorFluid(level, name);
            generatorWater[i] = new BlockGeneratorWater(level, name);
            generatorSteam[i] = new BlockGeneratorSteam(level, name);

            machineShells[i] = new BlockMachineShell(level, name);
            machineVaporization[i] = new BlockMachineVaporization(level, name);
        }
    }

    @Override
    public void initItems() {
        for (int i = 0; i < 5; i++) {
            turbineRotors[i] = new ItemTurbineRotor(names[i]);
        }
    }

    @Override
    public void initOre() {
        for (int i = 0; i < names.length; i++) {
            OreDictionary.registerOre("turbineRotor" + gearOres[i], turbineRotors[i]);
        }
    }

    @Override
    public void initRecipes() {
        for (int i = 0; i < 5; i++) {
            String gearOre = "gear" + gearOres[i];
            String ingotOre = ingotOres[i];

            ModRecipes.addRecipeTurbineRotor(turbineRotors[i], ingotOre, gearOre);

            addRecipeGenerator(i, generatorTurbine[i], i == 0 ? BlocksGenerator.blockGeneratorTurbine[4] : generatorTurbine[i - 1], gearOre);
            addRecipeGenerator(i, generatorFluid[i], i == 0 ? BlocksGenerator.blockGeneratorFluid[4] : generatorFluid[i - 1], gearOre);
            addRecipeGenerator(i, generatorWater[i], i == 0 ? BlocksGenerator.blockGeneratorWater[4] : generatorWater[i - 1], gearOre);
            addRecipeGenerator(i, generatorSteam[i], i == 0 ? BlocksGenerator.blockGeneratorSteam[4] : generatorSteam[i - 1], gearOre);

            // machine shell
            Block block = i == 0 ? BlocksMachine.machineShells[4] : machineShells[i - 1];
            ModRecipes.addRecipeShell(machineShells[i], block, ingotOre);

            // machine va
            addRecipeMachineVaporization(i, machineVaporization[i], gearOre);
        }
    }

    private ItemStack getConduitStack(int i) {
        Item conduit = Item.getByNameOrId("thermaldynamics:duct_0");
        return new ItemStack(conduit == null ? Items.AIR : conduit, 1, i);
    }

    private void addRecipeMachineVaporization(int i, BlockMachineVaporization output, String gearOre) {
        ItemStack itemStack = getConduitStack(i);
        ModRecipes.addRecipeMachineVaporization(
                output,
                machineShells[i],
                itemStack,
                gearOre
        );
    }

    private void addRecipeGenerator(int i, BlockGeneratorBasic output, Block oldGenerator, String gearOre) {
        ItemStack itemStack = getConduitStack(i);
        ModRecipes.addRecipeGenerator(
                output,
                itemStack,
                turbineRotors[i],
                machineShells[i],
                oldGenerator,
                gearOre
        );
    }

}
