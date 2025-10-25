package com.xiaoxianben.watergenerators.init.modRegister;

import com.xiaoxianben.watergenerators.api.IModRegister;
import com.xiaoxianben.watergenerators.blocks.generator.BlockGeneratorBasic;
import com.xiaoxianben.watergenerators.blocks.machine.BlockMachineBase;
import com.xiaoxianben.watergenerators.blocks.machine.BlockMachineShell;
import com.xiaoxianben.watergenerators.init.EnumModBlock;
import com.xiaoxianben.watergenerators.init.EnumModItems;
import com.xiaoxianben.watergenerators.init.EnumModRegister;
import com.xiaoxianben.watergenerators.init.ModRecipes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.Objects;

public class TFRegister implements IModRegister {


    private final float[] levels = new float[]{6, 7, 8, 9, 10};
    private final String[] levelNames = new String[]{"steel", "invar", "electrum", "signalum", "enderium"};
    private final String[] levelIngotOres = new String[]{"ingotSteel", "ingotInvar", "ingotElectrum", "ingotSignalum", "ingotEnderium"};
    private final String[] gearOres = new String[]{"gearSteel", "gearInvar", "gearElectrum", "gearSignalum", "gearEnderium"};

    private final EnumModRegister selfRegister = EnumModRegister.TF;

    @Override
    public void init() {
        Item conduit = Item.getByNameOrId("thermaldynamics:duct_0");
        ItemStack[] itemStacks = new ItemStack[5];
        for (int i = 0; i < itemStacks.length; i++) {
            itemStacks[i] = new ItemStack(Objects.requireNonNull(conduit), 1, i);
        }
        EnumModItems.CONDUIT.addItems(selfRegister, itemStacks);


        // block
        final int[] i1 = {0};
        Arrays.stream(EnumModBlock.MACHINE_SHELL.blockMap.get(selfRegister))
                .filter(Objects::nonNull)
                .forEach(block -> {
                    int i = i1[0];
                    ModRecipes.instance.addRecipeShell(block,
                            EnumModBlock.MACHINE_SHELL.blockMap.get(i == 0 ? EnumModRegister.MINECRAFT : selfRegister)[i == 0 ? 4 : (i - 1)],
                            levelIngotOres[i]);
                    ++i1[0];
                });
        i1[0] = 0;
        Arrays.stream(EnumModBlock.MACHINE_VAPORIZATION.blockMap.get(selfRegister))
                .filter(Objects::nonNull)
                .forEach(block -> {
                    int i = i1[0];
                    ModRecipes.instance.addRecipeMachineVaporization((BlockMachineBase) block,
                            (BlockMachineShell) EnumModBlock.MACHINE_SHELL.blockMap.get(selfRegister)[i],
                            EnumModItems.CONDUIT.itemMap.get(selfRegister)[i],
                            gearOres[i]);
                    ++i1[0];
                });
        i1[0] = 0;
        Arrays.stream(EnumModBlock.MACHINE_CONCENTRATION.blockMap.get(selfRegister))
                .filter(Objects::nonNull)
                .forEach(block -> {
                    int i = i1[0];
                    ModRecipes.instance.addRecipeMachineConcentration((BlockMachineBase) block,
                            (BlockMachineShell) EnumModBlock.MACHINE_SHELL.blockMap.get(selfRegister)[i],
                            EnumModItems.CONDUIT.itemMap.get(selfRegister)[i],
                            (BlockMachineBase) EnumModBlock.MACHINE_VAPORIZATION.getBlocks(selfRegister)[i]);
                    ++i1[0];
                });
        i1[0] = 0;
        Arrays.stream(EnumModBlock.GENERATOR_turbine.blockMap.get(selfRegister))
                .filter(Objects::nonNull)
                .forEach(block -> {
                    int i = i1[0];
                    recipeGenerator(i,
                            (BlockGeneratorBasic) block,
                            EnumModBlock.GENERATOR_turbine.getBlocks(i == 0 ? EnumModRegister.MINECRAFT : selfRegister)[i == 0 ? 4 : (i - 1)]);
                    ++i1[0];
                });
        i1[0] = 0;
        Arrays.stream(EnumModBlock.GENERATOR_fluid.blockMap.get(selfRegister))
                .filter(Objects::nonNull)
                .forEach(block -> {
                    int i = i1[0];
                    recipeGenerator(i,
                            (BlockGeneratorBasic) block,
                            EnumModBlock.GENERATOR_fluid.getBlocks(i == 0 ? EnumModRegister.MINECRAFT : selfRegister)[i == 0 ? 4 : (i - 1)]);
                    ++i1[0];
                });
        i1[0] = 0;
        Arrays.stream(EnumModBlock.GENERATOR_water.blockMap.get(selfRegister))
                .filter(Objects::nonNull)
                .forEach(block -> {
                    int i = i1[0];
                    recipeGenerator(i,
                            (BlockGeneratorBasic) block,
                            EnumModBlock.GENERATOR_water.getBlocks(i == 0 ? EnumModRegister.MINECRAFT : selfRegister)[i == 0 ? 4 : (i - 1)]);
                    ++i1[0];
                });
        i1[0] = 0;
        Arrays.stream(EnumModBlock.GENERATOR_steam.blockMap.get(selfRegister))
                .filter(Objects::nonNull)
                .forEach(block -> {
                    int i = i1[0];
                    recipeGenerator(i,
                            (BlockGeneratorBasic) block,
                            EnumModBlock.GENERATOR_steam.getBlocks(i == 0 ? EnumModRegister.MINECRAFT : selfRegister)[i == 0 ? 4 : (i - 1)]);
                    ++i1[0];
                });
        i1[0] = 0;
        Arrays.stream(EnumModBlock.GENERATOR_waterCompressed.blockMap.get(selfRegister))
                .filter(Objects::nonNull)
                .forEach(block -> {
                    int i = i1[0];
                    recipeGenerator(i,
                            (BlockGeneratorBasic) block,
                            EnumModBlock.GENERATOR_waterCompressed.getBlocks(i == 0 ? EnumModRegister.MINECRAFT : selfRegister)[i == 0 ? 4 : (i - 1)]);
                    ++i1[0];
                });

        // item
        int i = 0;
        for (ItemStack itemStack : EnumModItems.TURBINE_ROTOR.itemMap.get(selfRegister)) {
            ModRecipes.instance.addRecipeTurbineRotor(itemStack.getItem(), levelIngotOres[i], gearOres[i]);
            ++i;
        }

    }

    @Override
    public void posInit() {

    }

    @Override
    public EnumModRegister getModRegister() {
        return selfRegister;
    }

    @Override
    public float[] getLevels() {
        return levels;
    }

    @Override
    public String[] getLevelsString() {
        return levelNames;
    }

    @Override
    public String getGearOre(int i) {
        return gearOres[i];
    }

}
