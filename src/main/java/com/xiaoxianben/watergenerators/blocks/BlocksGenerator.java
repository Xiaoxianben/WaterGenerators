package com.xiaoxianben.watergenerators.blocks;

import com.xiaoxianben.watergenerators.api.IHasInit;
import com.xiaoxianben.watergenerators.blocks.generator.*;
import com.xiaoxianben.watergenerators.fluid.Fluids;
import com.xiaoxianben.watergenerators.init.ModRecipes;
import com.xiaoxianben.watergenerators.items.ItemsMaterial;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BlocksGenerator implements IHasInit {


    public static BlockGeneratorTurbine[] blockGeneratorTurbine = new BlockGeneratorTurbine[5];
    public static BlockGeneratorFluid[] blockGeneratorFluid = new BlockGeneratorFluid[5];
    public static BlockGeneratorWater[] blockGeneratorWater = new BlockGeneratorWater[5];
    public static BlockGeneratorSteam[] blockGeneratorSteam = new BlockGeneratorSteam[5];

    public static BlockGeneratorBasic createGenerator;

    public String[] gearName = {"gearIron", "gearGoldPlatedIron", "gearDiamond", "gearObsidian", "gearEmerald"};

    public void init() {
        createGenerator = new BlockGeneratorCreate();

        for (int i = 0; i < 5; i++) {
            int level = i + 1;
            blockGeneratorTurbine[i] = new BlockGeneratorTurbine(level, "level" + level);
            blockGeneratorFluid[i] = new BlockGeneratorFluid(level, "level" + level);
            blockGeneratorWater[i] = new BlockGeneratorWater(level, "level" + level);
            blockGeneratorSteam[i] = new BlockGeneratorSteam(level, "level" + level);
        }
    }

    public void initRecipes() {
        for (int i = 0; i < 5; i++) {
            registerGenerator(i, ItemsMaterial.coils[i], blockGeneratorTurbine);
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
        ModRecipes.addRecipeGenerator(
                blockGenerator[i],
                ItemsMaterial.conduits[i].getDefaultInstance(),
                ItemsMaterial.turbines[i],
                BlocksMachine.machineShells[i],
                oldItemStack,
                gearName[i]
        );
    }

    private void registerOtherFluidGenerator(int i, ItemStack oldItem, BlockGeneratorFluid... blockGenerator) {
        if (i == 0) {
            ModRecipes.addRecipeGenerator(
                    blockGenerator[0],
                    oldItem
            );
        } else {
            registerGenerator(i, null, blockGenerator);
        }
    }

}
