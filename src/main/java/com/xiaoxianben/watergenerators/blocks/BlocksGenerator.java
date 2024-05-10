package com.xiaoxianben.watergenerators.blocks;

import com.xiaoxianben.watergenerators.api.IHasInit;
import com.xiaoxianben.watergenerators.blocks.generator.*;
import com.xiaoxianben.watergenerators.fluid.Fluids;
import com.xiaoxianben.watergenerators.init.ModRecipes;
import com.xiaoxianben.watergenerators.items.ItemsMaterial;
import com.xiaoxianben.watergenerators.otherModsItems.otherInit;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BlocksGenerator implements IHasInit {

    // GENERATOR
    public static BlockGeneratorBasic createGenerator;

    public static BlockGeneratorTurbine[] blockGeneratorTurbine = new BlockGeneratorTurbine[5];
    public static BlockGeneratorFluid[] blockGeneratorFluid = new BlockGeneratorFluid[5];
    public static BlockGeneratorWater[] blockGeneratorWater = new BlockGeneratorWater[5];
    public static BlockGeneratorSteam[] blockGeneratorSteam = new BlockGeneratorSteam[5];


    public static String[] oreIngots = {"ingotIron", "ingotGoldPlatedIron", "gemDiamond", "obsidian", "gemEmerald"};
    //    public static String[] blockName = {"level1", "level2", "level3", "level4", "level5"};
    public static String[] gearName = {"gearIron", "gearGoldPlated", "gearDiamond", "gearObsidian", "gearEmerald"};
    public static int[] level = {1, 2, 3, 4, 5};

    public void init() {
        createGenerator = new BlockGeneratorCreate();

        for (int i = 0; i < 5; i++) {
            blockGeneratorTurbine[i] = registerTurbine(level[i]);
        }
        otherInit.initGeneratorTurbine();

        for (int i = 0; i < 5; i++) {
            blockGeneratorFluid[i] = registerFluid(level[i]);
        }
        otherInit.initGeneratorFluid();

        for (int i = 0; i < 5; i++) {
            blockGeneratorWater[i] = registerWater(level[i]);
        }
        otherInit.initGeneratorWater();

        for (int i = 0; i < 5; i++) {
            blockGeneratorSteam[i] = registerSteam(level[i]);
        }
        otherInit.initGeneratorSteam();
    }

    public void initRecipes() {
        for (int i = 0; i < 5; i++) {
            registerGenerator(i, ItemsMaterial.coilIron, blockGeneratorTurbine);
            registerGenerator(i, Items.BUCKET, blockGeneratorFluid);
            registerOtherFluidGenerator(i, Fluids.steamBucket, blockGeneratorSteam);
            registerOtherFluidGenerator(i, Items.WATER_BUCKET.getDefaultInstance(), blockGeneratorWater);
        }
    }

    private void registerGenerator(int i, Item oldItem, BlockGeneratorBasic... blockGenerator) {
        ItemStack oldItemStack;
        if (i == 0) {
            oldItemStack = oldItem.getDefaultInstance();
        } else {
            oldItemStack = Item.getItemFromBlock(blockGenerator[i - 1]).getDefaultInstance();
        }
        ModRecipes.registryGenerator(
                blockGenerator[i],
                ItemsMaterial.conduits[i].getDefaultInstance(),
                ItemsMaterial.turbines[i],
                gearName[i],
                BlocksMachine.machineShells[i],
                oldItemStack
        );
    }

    private void registerOtherFluidGenerator(int i, ItemStack oldItem, BlockGeneratorFluid... blockGenerator) {
        if (i == 0) {
            ModRecipes.recipeGenerator(
                    blockGenerator[0],
                    oldItem
            );
        } else {
            registerGenerator(i, null, blockGenerator);
        }
    }

    public BlockGeneratorTurbine registerTurbine(int level) {
        return new BlockGeneratorTurbine(level, "level" + level);
    }

    public BlockGeneratorWater registerWater(int level) {
        return new BlockGeneratorWater(level, "level" + level);
    }

    public BlockGeneratorSteam registerSteam(int level) {
        return new BlockGeneratorSteam(level, "level" + level);
    }

    public BlockGeneratorFluid registerFluid(int level) {
        return new BlockGeneratorFluid(level, "level" + level);
    }
}
