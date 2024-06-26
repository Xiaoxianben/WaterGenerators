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
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Locale;

public class EnderIOInit implements IOtherModInit {
    /**
     * EndSteel, MelodicAlloy, StellarAlloy
     */
    public static Item[] enderIO_Gear = new Item[3];
    /**
     * 0:0,1,2;<p></p>
     * 1:10,11
     */
    public static Item[] enderIO_Conduit = new Item[2];
    public static Item[] enderIO_TurbineRotor = new Item[5];
    public static BlockGeneratorTurbine[] enderIO_GeneratorTurbine = new BlockGeneratorTurbine[5];
    public static BlockGeneratorFluid[] enderIO_generatorFluid = new BlockGeneratorFluid[5];
    public static BlockGeneratorWater[] enderIO_GeneratorWater = new BlockGeneratorWater[5];
    public static BlockGeneratorSteam[] enderIO_generatorSteam = new BlockGeneratorSteam[5];
    public static BlockMachineShell[] enderIO_machineShell = new BlockMachineShell[5];
    public static BlockMachineVaporization[] enderIO_machineVaporization = new BlockMachineVaporization[5];

    /**
     * has[
     * VibrantAlloy,
     * DarkSteel,
     * EndSteel,
     * MelodicAlloy,
     * StellarAlloy
     * ]
     */
    public boolean[] booleans = new boolean[5];
    public float[] level = {6.5f, 7.5f, 8.5f, 9.5f, 10.5f};
    public String[] EnderIOName = new String[]{"vibrantalloy", "darksteel", "endsteel", "melodicalloy", "stellaralloy"};
    public String[] EnderIOIngotOre = new String[]{"VibrantAlloy", "DarkSteel", "EndSteel", "MelodicAlloy", "StellarAlloy"};

    public EnderIOInit() {
        for (int i = 0; i < EnderIOIngotOre.length; i++) {
            booleans[i] = i < 3 ? Loader.isModLoaded("enderio") : Loader.isModLoaded("enderioendergy");
            EnderIOName[i] = EnderIOIngotOre[i].toLowerCase(Locale.ROOT);
        }
    }

    @Override
    public void initBlocks() {
        for (int i = 0; i < this.booleans.length; i++) {
            if (!this.booleans[i]) break;
            float level = this.level[i];
            String name = this.EnderIOName[i];

            enderIO_GeneratorTurbine[i] = new BlockGeneratorTurbine(level, name);
            enderIO_generatorFluid[i] = new BlockGeneratorFluid(level, name);
            enderIO_GeneratorWater[i] = new BlockGeneratorWater(level, name);
            enderIO_generatorSteam[i] = new BlockGeneratorSteam(level, name);

            enderIO_machineShell[i] = new BlockMachineShell(level, name);
            enderIO_machineVaporization[i] = new BlockMachineVaporization(level, name);
        }
    }

    @Override
    public void initItems() {
        for (int i = 0; i < this.booleans.length; i++) {
            if (!this.booleans[i]) break;
            if (i > 1) {
                enderIO_Gear[i - 2] = new ItemMaterial("gear_" + EnderIOName[i].replace("alloy", "").replace("steel", ""), ModItems.allGear);
            }
            enderIO_TurbineRotor[i] = new ItemMaterial("turbine_rotor_" + EnderIOName[i], ModItems.allTurbineRotor);
        }
    }

    @Override
    public void initOre() {
        for (int i = 0; i < EnderIOInit.enderIO_Gear.length; i++) {
            if (booleans[i + 2]) {
                OreDictionary.registerOre("gear" + EnderIOIngotOre[i + 2].replace("Alloy", "").replace("Steel", ""), EnderIOInit.enderIO_Gear[i]);
            }
        }

    }

    @Override
    public void initRecipes() {
        if (booleans[1]) {
            EnderIOInit.enderIO_Conduit[0] = GameRegistry.findRegistry(Item.class).getValue(new ResourceLocation("enderio", "item_power_conduit"));
        }
        if (booleans[3]) {
            EnderIOInit.enderIO_Conduit[1] = GameRegistry.findRegistry(Item.class).getValue(new ResourceLocation("enderio", "item_endergy_conduit"));
        }

        for (int i = 0; i < booleans.length; i++) {
            if (!booleans[i]) break;

            String gear = "gear" + EnderIOIngotOre[i].replace("Alloy", "").replace("Steel", "");
            String ingotOre = "ingot" + EnderIOIngotOre[i];

            if (i < 3) {
                ModRecipes.addRecipeGear(enderIO_Gear[i], "ingot" + EnderIOIngotOre[i + 2]);
            }

            ModRecipes.addRecipeTurbineRotor(enderIO_TurbineRotor[i], ingotOre, gear);

            ModRecipes.addRecipeShell(enderIO_machineShell[i], i == 0 ? BlocksMachine.machineShells[4] : enderIO_machineShell[i - 1], ingotOre);

            addRecipeMachineVaporization(i, enderIO_machineVaporization[i], gear);

            addRecipeGenerator(i, enderIO_GeneratorTurbine[i], i == 0 ? BlocksGenerator.blockGeneratorTurbine[4] : enderIO_GeneratorTurbine[i - 1], gear);
            addRecipeGenerator(i, enderIO_generatorFluid[i], i == 0 ? BlocksGenerator.blockGeneratorFluid[4] : enderIO_generatorFluid[i - 1], gear);
            addRecipeGenerator(i, enderIO_GeneratorWater[i], i == 0 ? BlocksGenerator.blockGeneratorWater[4] : enderIO_GeneratorWater[i - 1], gear);
            addRecipeGenerator(i, enderIO_generatorSteam[i], i == 0 ? BlocksGenerator.blockGeneratorSteam[4] : enderIO_generatorSteam[i - 1], gear);
        }

    }

    private ItemStack getConduitStack(int i) {
        int conduitInt = i > 2 ? 1 : 0;
        Item conduit = EnderIOInit.enderIO_Conduit[conduitInt];
        // 如果导管不存在，则跳过
        if (conduit == null) return Items.AIR.getDefaultInstance();
        int date = conduitInt == 0 ? i : i == 3 ? 10 : 11;
        return new ItemStack(conduit, 1, date);
    }

    private void addRecipeMachineVaporization(int i, BlockMachineVaporization output, String gearOre) {
        ItemStack itemStack = getConduitStack(i);
        ModRecipes.addRecipeMachineVaporization(
                output,
                EnderIOInit.enderIO_machineShell[i],
                itemStack,
                gearOre
        );
    }

    private void addRecipeGenerator(int i, BlockGeneratorBasic output, Block oldGenerator, String gearOre) {
        ItemStack itemStack = getConduitStack(i);
        ModRecipes.addRecipeGenerator(
                output,
                itemStack,
                enderIO_TurbineRotor[i],
                enderIO_machineShell[i],
                oldGenerator,
                gearOre
        );
    }

}
