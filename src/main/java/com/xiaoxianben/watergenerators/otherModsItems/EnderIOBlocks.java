package com.xiaoxianben.watergenerators.otherModsItems;

import com.xiaoxianben.watergenerators.blocks.BlocksGenerator;
import com.xiaoxianben.watergenerators.blocks.BlocksMachine;
import com.xiaoxianben.watergenerators.blocks.generator.*;
import com.xiaoxianben.watergenerators.blocks.machine.BlockMachineShell;
import com.xiaoxianben.watergenerators.blocks.machine.BlockMachineVaporization;
import com.xiaoxianben.watergenerators.init.ModRecipes;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Arrays;

import static com.xiaoxianben.watergenerators.jei.modPlugin.vaporizationList;

public class EnderIOBlocks extends otherBlocks {

    public static void initGeneratorTurbine() {
        for (int i = 0; i < 5; i++) {
            if (!EnderIOInit.booleans[i]) {
                continue;
            }
            EnderIOInit.enderIO_Generators[0][i] = new BlockGeneratorTurbine(EnderIOInit.level[i], EnderIOInit.EnderIOName[i]);
        }
    }

    public static void initGeneratorFluid() {
        for (int i = 0; i < 5; i++) {
            if (!EnderIOInit.booleans[i]) {
                continue;
            }
            EnderIOInit.enderIO_generatorFluid[i] = new BlockGeneratorFluid(EnderIOInit.level[i], EnderIOInit.EnderIOName[i]);
        }
    }

    public static void initGeneratorWater() {
        for (int i = 0; i < 5; i++) {
            if (!EnderIOInit.booleans[i]) {
                continue;
            }
            EnderIOInit.enderIO_Generators[1][i] = new BlockGeneratorWater(EnderIOInit.level[i], EnderIOInit.EnderIOName[i]);
        }
    }

    public static void initGeneratorSteam() {
        for (int i = 0; i < 5; i++) {
            if (!EnderIOInit.booleans[i]) {
                continue;
            }
            EnderIOInit.enderIO_generatorSteam[i] = new BlockGeneratorSteam(EnderIOInit.level[i], EnderIOInit.EnderIOName[i]);
        }
    }

    public static void initMachineShell() {
        for (int i = 0; i < 5; i++) {
            if (!EnderIOInit.booleans[i]) {
                continue;
            }
            EnderIOInit.enderIO_machineShell[i] = new BlockMachineShell(EnderIOInit.EnderIOName[i], EnderIOInit.level[i]);
        }
    }

    public static void initMachineVaporization() {
        for (int i = 0; i < 5; i++) {
            if (!EnderIOInit.booleans[i]) {
                continue;
            }
            EnderIOInit.enderIO_machineVaporization[i] = new BlockMachineVaporization(EnderIOInit.EnderIOName[i], EnderIOInit.level[i]);
        }
    }

    public static void initRecipes() {
        for (int i = 0; i < 5; i++) {
            if (!EnderIOInit.booleans[i]) continue;

            // machineShell
            ModRecipes.registryShell(
                    EnderIOInit.enderIO_machineShell[i],
                    i == 0 ? BlocksMachine.machineShells[4] : EnderIOInit.enderIO_machineShell[i - 1],
                    "ingot" + EnderIOInit.EnderIOIngotOre[i]
            );

            // generator
            BlockGeneratorBasic[] oldGenerators = new BlockGeneratorBasic[]{BlocksGenerator.blockGeneratorTurbine[4], BlocksGenerator.blockGeneratorWater[4]};

            for (int j = 0; j < 2; j++) {
                registerGenerator(i, EnderIOInit.enderIO_Generators[j][i], i == 0 ? oldGenerators[j] : EnderIOInit.enderIO_Generators[j][i - 1]);
            }
            registerGenerator(i, EnderIOInit.enderIO_generatorSteam[i], i == 0 ? BlocksGenerator.blockGeneratorSteam[4] : EnderIOInit.enderIO_generatorSteam[i - 1]);
            registerGenerator(i, EnderIOInit.enderIO_generatorFluid[i], i == 0 ? BlocksGenerator.blockGeneratorFluid[4] : EnderIOInit.enderIO_generatorFluid[i - 1]);

            registerMachineVaporization(i, EnderIOInit.enderIO_machineVaporization[i]);
        }
        vaporizationList.addAll(Arrays.asList(EnderIOInit.enderIO_machineVaporization));
    }

    private static ItemStack getConduitStack(int i) {
        int conduitInt = i > 2 ? 1 : 0;
        Item conduit = EnderIOInit.enderIO_Conduit[conduitInt];
        // 如果导管不存在，则跳过
        if (conduit == null) return Items.AIR.getDefaultInstance();
        int date = conduitInt == 0 ? i : i == 3 ? 10 : 11;
        return new ItemStack(conduit, 1, date);
    }

    private static void registerMachineVaporization(int i, BlockMachineVaporization output) {
        ItemStack itemStack = getConduitStack(i);
        ModRecipes.registerMachineVaporization(
                output,
                EnderIOInit.enderIO_machineShell[i],
                itemStack,
                "gear" + EnderIOInit.EnderIOIngotOre[i].replace("Alloy", "")
        );
    }

    private static void registerGenerator(int i, BlockGeneratorBasic output, Block oldGenerator) {
        ItemStack itemStack = getConduitStack(i);
        ModRecipes.registryGenerator(
                output,
                itemStack,
                EnderIOInit.enderIO_TurbineRotor[i],
                "gear" + EnderIOInit.EnderIOIngotOre[i].replace("Alloy", ""),
                EnderIOInit.enderIO_machineShell[i],
                oldGenerator
        );
    }

}
