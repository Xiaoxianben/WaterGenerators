package com.xiaoxianben.watergenerators.otherModsItems;

import com.xiaoxianben.watergenerators.blocks.*;
import com.xiaoxianben.watergenerators.init.ModRecipes;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import java.util.Arrays;

import static com.xiaoxianben.watergenerators.JEI.modPlugin.vaporizationList;

public class TFBlocks extends otherBlocks {
    public static BlockGeneratorBasic TURBINE_GENERATOR_Steel; // 钢
    public static BlockGeneratorBasic TURBINE_GENERATOR_Invar; // 殷钢
    public static BlockGeneratorBasic TURBINE_GENERATOR_Electrum; // 琥珀金
    public static BlockGeneratorBasic TURBINE_GENERATOR_Signalum; // 信素
    public static BlockGeneratorBasic TURBINE_GENERATOR_Enderium; // 末影
    public static BlockGeneratorBasic WATER_GENERATOR_Steel;
    public static BlockGeneratorBasic WATER_GENERATOR_Invar;
    public static BlockGeneratorBasic WATER_GENERATOR_Electrum;
    public static BlockGeneratorBasic WATER_GENERATOR_Signalum;
    public static BlockGeneratorBasic WATER_GENERATOR_Enderium;

    public static BlockGeneratorBasic[][] TF_generatorTW = new BlockGeneratorBasic[2][5];

    public static BlockMachineShell[] machineShells = new BlockMachineShell[5];
    public static BlockMachineVaporization[] machineVaporization = new BlockMachineVaporization[5];
    public static BlockGeneratorSteam[] TF_generatorSteam = new BlockGeneratorSteam[5];

    public static String[] TFName = {"steel", "invar", "electrum", "signalum", "enderium"};
    public static int[] level = {6, 7, 8, 9, 10};
    public static String[] oreIngots = {"ingotSteel", "ingotInvar", "ingotElectrum", "ingotSignalum", "ingotEnderium"};

    @Override
    public void init() {
        for (int i = 0; i < 5; i++) {
            int l = level[i];
            String name = TFName[i];

            // generator
            BlockGeneratorBasic[] generatorBasics = registryGenerator(name, l, name);
            TF_generatorTW[0][i] = generatorBasics[0];
            TF_generatorTW[1][i] = generatorBasics[1];
            TF_generatorSteam[i] = (BlockGeneratorSteam) generatorBasics[2];

            // machine shell
            machineShells[i] = new BlockMachineShell(name, l);

            // machine va
            machineVaporization[i] = new BlockMachineVaporization(name, l);
        }

        // 支持旧代码
        TFBlocks.TURBINE_GENERATOR_Steel = TF_generatorTW[0][0]; // 钢
        TFBlocks.TURBINE_GENERATOR_Invar = TF_generatorTW[0][1]; // 殷钢
        TFBlocks.TURBINE_GENERATOR_Electrum = TF_generatorTW[0][2]; // 琥珀金
        TFBlocks.TURBINE_GENERATOR_Signalum = TF_generatorTW[0][3]; // 信素
        TFBlocks.TURBINE_GENERATOR_Enderium = TF_generatorTW[0][4]; // 末影

        TFBlocks.WATER_GENERATOR_Steel = TF_generatorTW[1][0]; // 钢
        TFBlocks.WATER_GENERATOR_Invar = TF_generatorTW[1][1]; // 殷钢
        TFBlocks.WATER_GENERATOR_Electrum = TF_generatorTW[1][2]; // 琥珀金
        TFBlocks.WATER_GENERATOR_Signalum = TF_generatorTW[1][3]; // 信素
        TFBlocks.WATER_GENERATOR_Enderium = TF_generatorTW[1][4]; // 末影

    }

    @Override
    public void initRecipes() {
        ModRecipes.registryGenerator_old(TFBlocks.TURBINE_GENERATOR_Steel, (byte) 6, (byte) 1);
        ModRecipes.registryGenerator_old(TFBlocks.TURBINE_GENERATOR_Invar, (byte) 7, (byte) 1);
        ModRecipes.registryGenerator_old(TFBlocks.TURBINE_GENERATOR_Electrum, (byte) 8, (byte) 1);
        ModRecipes.registryGenerator_old(TFBlocks.TURBINE_GENERATOR_Signalum, (byte) 9, (byte) 1);
        ModRecipes.registryGenerator_old(TFBlocks.TURBINE_GENERATOR_Enderium, (byte) 10, (byte) 1);

        ModRecipes.registryGenerator_old(TFBlocks.WATER_GENERATOR_Steel, (byte) 6, (byte) 2);
        ModRecipes.registryGenerator_old(TFBlocks.WATER_GENERATOR_Invar, (byte) 7, (byte) 2);
        ModRecipes.registryGenerator_old(TFBlocks.WATER_GENERATOR_Electrum, (byte) 8, (byte) 2);
        ModRecipes.registryGenerator_old(TFBlocks.WATER_GENERATOR_Signalum, (byte) 9, (byte) 2);
        ModRecipes.registryGenerator_old(TFBlocks.WATER_GENERATOR_Enderium, (byte) 10, (byte) 2);

        for (int i = 0; i < 5; i++) {
            this.registerGenerator(i, TF_generatorSteam[i], i == 0 ? BlocksGenerator.blockGeneratorSteam[4] : TF_generatorSteam[i - 1]);

            // machine shell
            Block block = i == 0 ? BlocksMachine.machineShells[4] : machineShells[i - 1];
            ModRecipes.registryShell(machineShells[i], block, oreIngots[i]);

            // machine va
            this.registerMachineVaporization(i, machineVaporization[i]);
        }
        vaporizationList.addAll(Arrays.asList(machineVaporization));
    }

    private ItemStack getConduitStack(int i) {
        return new ItemStack(TFItems.TFConduit, 1, i);
    }

    private void registerMachineVaporization(int i, BlockMachineVaporization output) {
        ItemStack itemStack = getConduitStack(i);
        ModRecipes.registryMachineVaporization(
                output,
                TFBlocks.machineShells[i],
                itemStack.getItem(),
                "gear" + EnderIOInit.EnderIOIngotOre[i].replace("Alloy", "")
        );
    }

    private void registerGenerator(int i, BlockGeneratorBasic output, Block oldGenerator) {
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
