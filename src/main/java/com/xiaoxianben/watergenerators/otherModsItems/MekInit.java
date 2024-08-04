package com.xiaoxianben.watergenerators.otherModsItems;

import com.xiaoxianben.watergenerators.blocks.BlocksGenerator;
import com.xiaoxianben.watergenerators.blocks.BlocksMachine;
import com.xiaoxianben.watergenerators.blocks.generator.*;
import com.xiaoxianben.watergenerators.blocks.machine.BlockMachineShell;
import com.xiaoxianben.watergenerators.blocks.machine.BlockMachineVaporization;
import com.xiaoxianben.watergenerators.init.ModRecipes;
import com.xiaoxianben.watergenerators.items.material.ItemTurbineRotor;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

public class MekInit implements IOtherModInit {


    public static Item[] turbineRotors = new Item[4];

    public static BlockGeneratorTurbine[] generatorTurbine = new BlockGeneratorTurbine[4];
    public static BlockGeneratorFluid[] generatorFluid = new BlockGeneratorFluid[4];
    public static BlockGeneratorWater[] generatorWater = new BlockGeneratorWater[4];
    public static BlockGeneratorSteam[] generatorSteam = new BlockGeneratorSteam[4];

    public static BlockMachineShell[] machineShell = new BlockMachineShell[4];
    public static BlockMachineVaporization[] machineVaporization = new BlockMachineVaporization[4];


    public static float[] levels = {6, 8f, 10f, 12};
    public String[] names = new String[]{"basic", "advanced", "elite", "ultimate"};
    public String[] gearOres = new String[]{"Basic", "Advanced", "Elite", "Ultimate"};
    public String[] ingotOres = new String[]{"ingotSteel", "ingotOsmium", "ingotRefinedGlowstone", "ingotRefinedObsidian"};

    @Override
    public void initBlocks() {
        for (int i = 0; i < names.length; i++) {
            String name = this.names[i];
            float level = levels[i];

            generatorTurbine[i] = new BlockGeneratorTurbine(level, name);
            generatorFluid[i] = new BlockGeneratorFluid(level, name);
            generatorWater[i] = new BlockGeneratorWater(level, name);
            generatorSteam[i] = new BlockGeneratorSteam(level, name);

            machineShell[i] = new BlockMachineShell(level, name);
            machineVaporization[i] = new BlockMachineVaporization(level, name);
        }
    }

    @Override
    public void initItems() {
        for (int i = 0; i < names.length; i++) {
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
        for (int i = 0; i < this.names.length; i++) {
            ModRecipes.addRecipeShell(machineShell[i], i == 0 ? BlocksMachine.machineShells[4] : machineShell[i - 1], ingotOres[i]);
            addRecipeMachineVaporization(i, machineVaporization[i], machineShell[i]);

            ModRecipes.addRecipeTurbineRotor(turbineRotors[i], ingotOres[i], "circuit" + gearOres[i]);

            addRecipeGenerator(i, generatorTurbine, BlocksGenerator.blockGeneratorTurbine[4]);
            addRecipeGenerator(i, generatorFluid, BlocksGenerator.blockGeneratorFluid[4]);
            addRecipeGenerator(i, generatorWater, BlocksGenerator.blockGeneratorWater[4]);
            addRecipeGenerator(i, generatorSteam, BlocksGenerator.blockGeneratorSteam[4]);
        }
    }

    private ItemStack getConduitStack(int i) {
        Item conduit = Item.getByNameOrId("mekanism:transmitter");
        // 如果导管不存在，则跳过
        if (conduit == null) return Items.AIR.getDefaultInstance();
        ItemStack itemStack = new ItemStack(conduit);
        itemStack.setTagCompound(new NBTTagCompound());
        itemStack.getTagCompound().setInteger("tier", i);
        return itemStack;
    }

    private void addRecipeGenerator(int i, BlockGeneratorBasic[] generator, BlockGeneratorBasic oldGenerator) {
        ModRecipes.addRecipeGenerator(
                generator[i],
                getConduitStack(i),
                turbineRotors[i],
                machineShell[i],
                i > 0 ? generator[i - 1] : oldGenerator,
                "circuit" + gearOres[i]
        );
    }

    private void addRecipeMachineVaporization(int i, BlockMachineVaporization output, BlockMachineShell machineShell) {
        ItemStack itemStack = getConduitStack(i);
        ModRecipes.addRecipeMachineVaporization(
                output,
                machineShell,
                itemStack,
                "circuit" + gearOres[i]
        );
    }

}
