package com.xiaoxianben.watergenerators.otherModsItems;

import com.xiaoxianben.watergenerators.blocks.BlocksGenerator;
import com.xiaoxianben.watergenerators.blocks.BlocksMachine;
import com.xiaoxianben.watergenerators.blocks.generator.*;
import com.xiaoxianben.watergenerators.blocks.machine.BlockMachineShell;
import com.xiaoxianben.watergenerators.blocks.machine.BlockMachineVaporization;
import com.xiaoxianben.watergenerators.init.ModItems;
import com.xiaoxianben.watergenerators.init.ModRecipes;
import com.xiaoxianben.watergenerators.items.ItemMaterial;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TFInit implements IOtherModInit {

    public static Item[] TFTurbine = new Item[5];
    public static BlockGeneratorTurbine[] TF_generatorTurbine = new BlockGeneratorTurbine[5];
    public static BlockGeneratorFluid[] TF_generatorFluid = new BlockGeneratorFluid[5];
    public static BlockGeneratorWater[] TF_generatorWater = new BlockGeneratorWater[5];
    public static BlockGeneratorSteam[] TF_generatorSteam = new BlockGeneratorSteam[5];

    public static BlockMachineShell[] TF_machineShells = new BlockMachineShell[5];
    public static BlockMachineVaporization[] TF_machineVaporization = new BlockMachineVaporization[5];


    public Item TFConduit = new Item();
    public String[] TFName = {"steel", "invar", "electrum", "signalum", "enderium"};
    public int[] TFLevel = {6, 7, 8, 9, 10};
    public String[] TFIngotOres = {"ingotSteel", "ingotInvar", "ingotElectrum", "ingotSignalum", "ingotEnderium"};


    @Override
    public void initBlocks() {
        for (int i = 0; i < 5; i++) {
            TF_generatorTurbine[i] = new BlockGeneratorTurbine(TFLevel[i], TFName[i]);
            TF_generatorFluid[i] = new BlockGeneratorFluid(TFLevel[i], TFName[i]);
            TF_generatorWater[i] = new BlockGeneratorWater(TFLevel[i], TFName[i]);
            TF_generatorSteam[i] = new BlockGeneratorSteam(TFLevel[i], TFName[i]);
            TF_machineShells[i] = new BlockMachineShell(TFLevel[i], TFName[i]);
            TF_machineVaporization[i] = new BlockMachineVaporization(TFLevel[i], TFName[i]);
        }
    }

    @Override
    public void initItems() {
        for (int i = 0; i < 5; i++) {
            TFTurbine[i] = new ItemMaterial("turbine_rotor_" + TFName[i], ModItems.allTurbineRotor);
        }
    }

    @Override
    public void initOre() {

    }

    @Override
    public void initRecipes() {
        TFConduit = Item.getByNameOrId("thermaldynamics:duct_0");
        if (TFConduit == null) {
            TFConduit = Items.AIR;
        }

        for (int i = 0; i < 5; i++) {
            String gearOre = "gear" + TFIngotOres[i].replace("ingot", "");

            ModRecipes.addRecipeTurbineRotor(TFTurbine[i], TFIngotOres[i], gearOre);

            addRecipeGenerator(i, TF_generatorTurbine[i], i == 0 ? BlocksGenerator.blockGeneratorTurbine[4] : TF_generatorTurbine[i - 1], gearOre);
            addRecipeGenerator(i, TF_generatorFluid[i], i == 0 ? BlocksGenerator.blockGeneratorFluid[4] : TF_generatorFluid[i - 1], gearOre);
            addRecipeGenerator(i, TF_generatorWater[i], i == 0 ? BlocksGenerator.blockGeneratorWater[4] : TF_generatorWater[i - 1], gearOre);
            addRecipeGenerator(i, TF_generatorSteam[i], i == 0 ? BlocksGenerator.blockGeneratorSteam[4] : TF_generatorSteam[i - 1], gearOre);

            // machine shell
            Block block = i == 0 ? BlocksMachine.machineShells[4] : TF_machineShells[i - 1];
            ModRecipes.addRecipeShell(TF_machineShells[i], block, TFIngotOres[i]);

            // machine va
            addRecipeMachineVaporization(i, TF_machineVaporization[i], gearOre);
        }
    }

    private ItemStack getConduitStack(int i) {
        return new ItemStack(TFConduit, 1, i);
    }

    private void addRecipeMachineVaporization(int i, BlockMachineVaporization output, String gearOre) {
        ItemStack itemStack = getConduitStack(i);
        ModRecipes.addRecipeMachineVaporization(
                output,
                TF_machineShells[i],
                itemStack,
                gearOre
        );
    }

    private void addRecipeGenerator(int i, BlockGeneratorBasic output, Block oldGenerator, String gearOre) {
        ItemStack itemStack = getConduitStack(i);
        ModRecipes.addRecipeGenerator(
                output,
                itemStack,
                TFTurbine[i],
                TF_machineShells[i],
                oldGenerator,
                gearOre
        );
    }

}
