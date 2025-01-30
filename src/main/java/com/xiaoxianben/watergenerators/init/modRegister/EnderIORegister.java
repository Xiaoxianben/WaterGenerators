package com.xiaoxianben.watergenerators.init.modRegister;

import com.xiaoxianben.watergenerators.blocks.generator.BlockGeneratorBasic;
import com.xiaoxianben.watergenerators.blocks.machine.BlockMachineBase;
import com.xiaoxianben.watergenerators.blocks.machine.BlockMachineShell;
import com.xiaoxianben.watergenerators.init.EnumModBlock;
import com.xiaoxianben.watergenerators.init.EnumModItems;
import com.xiaoxianben.watergenerators.init.ModRecipes;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;
import java.util.Objects;

public class EnderIORegister implements IModRegister {


    private final float[] levels = new float[]{6.5f, 8, 9.5f, 11, 12.5f};
    private final String[] levelNames = new String[]{"vibrantalloy", "darksteel", "endsteel", "melodicalloy", "stellaralloy"};
    private final String[] levelIngotOres = new String[]{"ingotVibrantAlloy", "ingotDarkSteel", "ingotEndSteel", "ingotMelodicAlloy", "ingotStellarAlloy"};
    private final String[] gearOres = new String[]{"gearVibrant", "gearDark", "gearEnd", "gearMelodicAlloy", "gearStellarAlloy"};

    private final EnumModRegister selfRegister = EnumModRegister.EnderIO;

    private final int decreaseValue = Loader.isModLoaded("enderioendergy") ? 0 : 2;


    @Override
    public void preInit() {
        for (int i = 1; i < EnumModBlock.values().length; i++) {
            EnumModBlock modBlock = EnumModBlock.values()[i];
            Block[] blocks = new Block[levels.length];

            for (int i2 = 0; i2 < levels.length - decreaseValue; i2++) {
                blocks[i2] = modBlock.creat(levels[i2], levelNames[i2]);
            }

            modBlock.addBlocks(selfRegister, blocks);
        }

        for (int i = 1; i < EnumModItems.values().length; i++) {
            EnumModItems modItems = EnumModItems.values()[i];
            if (modItems == EnumModItems.COIL || modItems == EnumModItems.CONDUIT) continue;

            ItemStack[] items = new ItemStack[levels.length];
            for (int i2 = (modItems == EnumModItems.GEAR ? 2 : 0); i2 < levels.length - decreaseValue; i2++) {
                items[i2] = modItems.creat(levelNames[i2]).getDefaultInstance();
            }

            modItems.addItems(selfRegister, items);
        }

    }

    @Override
    public void init() {
        for (int i = 2; i < EnumModItems.GEAR.itemMap.get(selfRegister).length - decreaseValue; i++) {
            OreDictionary.registerOre(gearOres[i], EnumModItems.GEAR.itemMap.get(selfRegister)[i]);
        }
        ItemStack[] itemStacks = new ItemStack[5];
        for (int i = 0; i < itemStacks.length - decreaseValue; i++) {
            Item conduit = Item.getByNameOrId(i > 2 ? "enderio:item_endergy_conduit" : "enderio:item_power_conduit");
            itemStacks[i] = new ItemStack(Objects.requireNonNull(conduit), 1, i < 3 ? i : (i == 3 ? 10 : 11));
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

        // item
        int i = 0;
        for (ItemStack itemStack : EnumModItems.GEAR.itemMap.get(selfRegister)) {
            if (itemStack == null) continue;
            ModRecipes.instance.addRecipeGear(itemStack.getItem(), levelIngotOres[i]);
            ++i;
        }
        i = 0;
        for (ItemStack itemStack : EnumModItems.TURBINE_ROTOR.itemMap.get(selfRegister)) {
            if (itemStack == null) continue;
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
    public String getGearOre(int i) {
        return gearOres[i];
    }

}
