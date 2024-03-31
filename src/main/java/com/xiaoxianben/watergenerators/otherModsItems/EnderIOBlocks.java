package com.xiaoxianben.watergenerators.otherModsItems;

import com.xiaoxianben.watergenerators.blocks.*;
import com.xiaoxianben.watergenerators.init.ModRecipes;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Arrays;

import static com.xiaoxianben.watergenerators.JEI.modPlugin.vaporizationList;

public class EnderIOBlocks extends otherBlocks {

    @Override
    public void init() {
        for (int i = 0; i < 5; i++) {
            if (!EnderIOInit.booleans[i]) {
                continue;
            }
            String name = EnderIOInit.EnderIOName[i];
            float level = EnderIOInit.level[i];

            // generator
            BlockGeneratorBasic[] generatorBasics = registryGenerator(name, level, name);
            EnderIOInit.enderIO_Generators[0][i] = generatorBasics[0];
            EnderIOInit.enderIO_Generators[1][i] = generatorBasics[1];
            EnderIOInit.enderIO_generatorSteam[i] = (BlockGeneratorSteam) generatorBasics[2];

            // machine shell
            EnderIOInit.enderIO_machineShell[i] = new BlockMachineShell(EnderIOInit.EnderIOName[i], EnderIOInit.level[i]);

            // machine va
            EnderIOInit.enderIO_machineVaporization[i] = new BlockMachineVaporization(name, level);
        }
    }

    @Override
    public void initRecipes() {
        for (int i = 0; i < 5; i++) {
            if (!EnderIOInit.booleans[i]) {
                continue;
            }
            // machineShell
            ModRecipes.registryShell(
                    EnderIOInit.enderIO_machineShell[i],
                    i == 0 ? BlocksMachine.machineShells[4] : EnderIOInit.enderIO_machineShell[i - 1],
                    "ingot" + EnderIOInit.EnderIOIngotOre[i]
            );

            // generator
            BlockGeneratorBasic[] oldGenerators = new BlockGeneratorBasic[]{BlocksGenerator.TURBINE_GENERATOR_LEVEL5, BlocksGenerator.WATER_GENERATOR_LEVEL5};

            for (int j = 0; j < 2; j++) {
                this.registerGenerator(i, EnderIOInit.enderIO_Generators[j][i], i == 0 ? oldGenerators[j] : EnderIOInit.enderIO_Generators[j][i - 1]);
            }
            this.registerGenerator(i, EnderIOInit.enderIO_generatorSteam[i], i == 0 ? BlocksGenerator.blockGeneratorSteam[4] : EnderIOInit.enderIO_generatorSteam[i - 1]);

            this.registerMachineVaporization(i, EnderIOInit.enderIO_machineVaporization[i]);
        }

        vaporizationList.addAll(Arrays.asList(EnderIOInit.enderIO_machineVaporization));

    }

    private ItemStack getConduitStack(int i) {
        int conduitInt = i > 2 ? 1 : 0;
        Item conduit = EnderIOInit.enderIO_Conduit[conduitInt];
        // 如果导管不存在，则跳过
        if (conduit == null) return Items.AIR.getDefaultInstance();
        int date = conduitInt == 0 ? i : i == 3 ? 10 : 11;
        return new ItemStack(conduit, 1, date);
    }

    private void registerMachineVaporization(int i, BlockMachineVaporization output) {
        ItemStack itemStack = getConduitStack(i);
        ModRecipes.registryMachineVaporization(
                output,
                EnderIOInit.enderIO_machineShell[i],
                itemStack.getItem(),
                "gear" + EnderIOInit.EnderIOIngotOre[i].replace("Alloy", "")
        );
    }

    private void registerGenerator(int i, BlockGeneratorBasic output, Block oldGenerator) {
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
