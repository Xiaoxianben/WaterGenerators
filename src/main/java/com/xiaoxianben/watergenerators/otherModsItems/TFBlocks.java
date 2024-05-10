package com.xiaoxianben.watergenerators.otherModsItems;

import com.xiaoxianben.watergenerators.blocks.BlocksGenerator;
import com.xiaoxianben.watergenerators.blocks.BlocksMachine;
import com.xiaoxianben.watergenerators.blocks.generator.*;
import com.xiaoxianben.watergenerators.blocks.machine.BlockMachineShell;
import com.xiaoxianben.watergenerators.blocks.machine.BlockMachineVaporization;
import com.xiaoxianben.watergenerators.init.ModRecipes;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import java.util.Arrays;

import static com.xiaoxianben.watergenerators.jei.modPlugin.vaporizationList;

public class TFBlocks extends otherBlocks {

    public static BlockGeneratorBasic[][] TF_generatorTW = new BlockGeneratorBasic[2][5];
    public static BlockGeneratorSteam[] TF_generatorSteam = new BlockGeneratorSteam[5];
    public static BlockGeneratorFluid[] TF_generatorFluid = new BlockGeneratorFluid[5];

    public static BlockMachineShell[] machineShells = new BlockMachineShell[5];
    public static BlockMachineVaporization[] machineVaporization = new BlockMachineVaporization[5];

    public static String[] TFName = {"steel", "invar", "electrum", "signalum", "enderium"};
    public static int[] level = {6, 7, 8, 9, 10};
    public static String[] oreIngots = {"ingotSteel", "ingotInvar", "ingotElectrum", "ingotSignalum", "ingotEnderium"};

    public static void initGeneratorTurbine() {
        for (int i = 0; i < 5; i++) {
            TF_generatorTW[0][i] = new BlockGeneratorTurbine(level[i], TFName[i]);
        }
    }

    public static void initGeneratorFluid() {
        for (int i = 0; i < 5; i++) {
            TF_generatorFluid[i] = new BlockGeneratorFluid(level[i], TFName[i]);
        }
    }

    public static void initGeneratorWater() {
        for (int i = 0; i < 5; i++) {
            TF_generatorTW[1][i] = new BlockGeneratorWater(level[i], TFName[i]);
        }
    }

    public static void initGeneratorSteam() {
        for (int i = 0; i < 5; i++) {
            TF_generatorSteam[i] = new BlockGeneratorSteam(level[i], TFName[i]);
        }
    }

    public static void initMachineShell() {
        for (int i = 0; i < 5; i++) {
            machineShells[i] = new BlockMachineShell(TFName[i], level[i]);
        }
    }

    public static void initMachineVaporization() {
        for (int i = 0; i < 5; i++) {
            machineVaporization[i] = new BlockMachineVaporization(TFName[i], level[i]);
        }
    }

    public static void initRecipes() {
        for (int i = 0; i < 5; i++) {
            registerGenerator(i, TF_generatorTW[0][i], i == 0 ? BlocksGenerator.blockGeneratorTurbine[4] : TF_generatorTW[0][i - 1]);
            registerGenerator(i, TF_generatorFluid[i], i == 0 ? BlocksGenerator.blockGeneratorFluid[4] : TF_generatorFluid[i - 1]);
            registerGenerator(i, TF_generatorTW[1][i], i == 0 ? BlocksGenerator.blockGeneratorWater[4] : TF_generatorTW[1][i - 1]);
            registerGenerator(i, TF_generatorSteam[i], i == 0 ? BlocksGenerator.blockGeneratorSteam[4] : TF_generatorSteam[i - 1]);

            // machine shell
            Block block = i == 0 ? BlocksMachine.machineShells[4] : machineShells[i - 1];
            ModRecipes.registryShell(machineShells[i], block, oreIngots[i]);

            // machine va
            registerMachineVaporization(i, machineVaporization[i]);
        }
        vaporizationList.addAll(Arrays.asList(machineVaporization));
    }

    private static ItemStack getConduitStack(int i) {
        return new ItemStack(TFItems.TFConduit, 1, i);
    }

    private static void registerMachineVaporization(int i, BlockMachineVaporization output) {
        ItemStack itemStack = getConduitStack(i);
        ModRecipes.registerMachineVaporization(
                output,
                TFBlocks.machineShells[i],
                itemStack,
                "gear" + TFBlocks.oreIngots[i].replace("ingot", "")
        );
    }

    private static void registerGenerator(int i, BlockGeneratorBasic output, Block oldGenerator) {
        ItemStack itemStack = getConduitStack(i);
        ModRecipes.registryGenerator(
                output,
                itemStack,
                TFItems.TFTurbine[i],
                "gear" + TFBlocks.oreIngots[i].replace("ingot", ""),
                TFBlocks.machineShells[i],
                oldGenerator
        );
    }

}