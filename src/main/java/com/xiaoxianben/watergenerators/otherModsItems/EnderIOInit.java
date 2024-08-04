package com.xiaoxianben.watergenerators.otherModsItems;

import com.xiaoxianben.watergenerators.blocks.BlocksGenerator;
import com.xiaoxianben.watergenerators.blocks.BlocksMachine;
import com.xiaoxianben.watergenerators.blocks.generator.*;
import com.xiaoxianben.watergenerators.blocks.machine.BlockMachineShell;
import com.xiaoxianben.watergenerators.blocks.machine.BlockMachineVaporization;
import com.xiaoxianben.watergenerators.init.ModItems;
import com.xiaoxianben.watergenerators.init.ModRecipes;
import com.xiaoxianben.watergenerators.items.material.ItemMaterial;
import com.xiaoxianben.watergenerators.items.material.ItemTurbineRotor;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

public class EnderIOInit implements IOtherModInit {
    /**
     * EndSteel, MelodicAlloy, StellarAlloy
     */
    public static Item[] gears = new Item[3];
    public static Item[] turbineRotors = new Item[5];
    public static BlockGeneratorTurbine[] generatorTurbines = new BlockGeneratorTurbine[5];
    public static BlockGeneratorFluid[] generatorFluids = new BlockGeneratorFluid[5];
    public static BlockGeneratorWater[] generatorWaters = new BlockGeneratorWater[5];
    public static BlockGeneratorSteam[] generatorSteams = new BlockGeneratorSteam[5];
    public static BlockMachineShell[] machineShells = new BlockMachineShell[5];
    public static BlockMachineVaporization[] machineVaporizations = new BlockMachineVaporization[5];

    public boolean[] booleans = new boolean[5];
    public float[] level = {6.5f, 7.5f, 8.5f, 9.5f, 10.5f};
    public String[] names = new String[]{"vibrantalloy", "darksteel", "endsteel", "melodicalloy", "stellaralloy"};
    public String[] ingotOres = new String[]{"VibrantAlloy", "DarkSteel", "EndSteel", "MelodicAlloy", "StellarAlloy"};
    public String[] gearOres = new String[]{"VibrantAlloy", "Dark", "End", "Melodic", "Stellar"};

    public EnderIOInit() {
        for (int i = 0; i < ingotOres.length; i++) {
            booleans[i] = i < 3 ? Loader.isModLoaded("enderio") : Loader.isModLoaded("enderioendergy");
        }
    }

    @Override
    public void initBlocks() {
        for (int i = 0; i < this.booleans.length; i++) {
            if (!this.booleans[i]) break;
            float level = this.level[i];
            String name = this.names[i];

            generatorTurbines[i] = new BlockGeneratorTurbine(level, name);
            generatorFluids[i] = new BlockGeneratorFluid(level, name);
            generatorWaters[i] = new BlockGeneratorWater(level, name);
            generatorSteams[i] = new BlockGeneratorSteam(level, name);

            machineShells[i] = new BlockMachineShell(level, name);
            machineVaporizations[i] = new BlockMachineVaporization(level, name);
        }
    }

    @Override
    public void initItems() {
        for (int i = 0; i < this.booleans.length; i++) {
            if (!this.booleans[i]) break;
            if (i > 1) {
                gears[i - 2] = new ItemMaterial("gear_" + names[i].replace("alloy", "").replace("steel", ""), ModItems.allGear);
            }
            turbineRotors[i] = new ItemTurbineRotor(names[i]);
        }
    }

    @Override
    public void initOre() {
        for (int i = 0; i < names.length; i++) {
            if (booleans[i + 2]) {
                OreDictionary.registerOre("gears" + ingotOres[i + 2].replace("Alloy", "").replace("Steel", ""), gears[i]);
            }
        }
        for (int i = 0; i < names.length; i++) {
            OreDictionary.registerOre("turbineRotor" + gearOres[i], turbineRotors[i]);
        }
    }

    @Override
    public void initRecipes() {

        for (int i = 0; i < booleans.length; i++) {
            if (!booleans[i]) break;

            String gear = "gears" + gearOres[i];
            String ingotOre = "ingot" + ingotOres[i];

            if (i > 2) {
                ModRecipes.addRecipeGear(EnderIOInit.gears[i - 3], ingotOre);
            }

            ModRecipes.addRecipeTurbineRotor(turbineRotors[i], ingotOre, gear);

            ModRecipes.addRecipeShell(machineShells[i], i == 0 ? BlocksMachine.machineShells[4] : machineShells[i - 1], ingotOre);

            addRecipeMachineVaporization(i, machineVaporizations[i], gear);

            addRecipeGenerator(i, generatorTurbines[i], i == 0 ? BlocksGenerator.blockGeneratorTurbine[4] : generatorTurbines[i - 1], gear);
            addRecipeGenerator(i, generatorFluids[i], i == 0 ? BlocksGenerator.blockGeneratorFluid[4] : generatorFluids[i - 1], gear);
            addRecipeGenerator(i, generatorWaters[i], i == 0 ? BlocksGenerator.blockGeneratorWater[4] : generatorWaters[i - 1], gear);
            addRecipeGenerator(i, generatorSteams[i], i == 0 ? BlocksGenerator.blockGeneratorSteam[4] : generatorSteams[i - 1], gear);
        }

    }

    private ItemStack getConduitStack(int i) {
        IForgeRegistry<Item> itemIForgeRegistry = GameRegistry.findRegistry(Item.class);
        Item conduit = i > 2 ?
                itemIForgeRegistry.getValue(new ResourceLocation("enderio", "item_endergy_conduit")) :
                itemIForgeRegistry.getValue(new ResourceLocation("enderio", "item_power_conduit"));
        // 如果导管不存在，则跳过
        if (conduit == null) return Items.AIR.getDefaultInstance();
        int date = i < 2 ? i : (i == 3 ? 10 : 11);
        return new ItemStack(conduit, 1, date);
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
