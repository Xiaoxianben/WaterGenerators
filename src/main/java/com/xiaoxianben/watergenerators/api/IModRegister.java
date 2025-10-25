package com.xiaoxianben.watergenerators.api;

import com.xiaoxianben.watergenerators.blocks.generator.BlockGeneratorBasic;
import com.xiaoxianben.watergenerators.blocks.machine.BlockMachineShell;
import com.xiaoxianben.watergenerators.init.EnumModBlock;
import com.xiaoxianben.watergenerators.init.EnumModItems;
import com.xiaoxianben.watergenerators.init.EnumModRegister;
import com.xiaoxianben.watergenerators.init.ModRecipes;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public interface IModRegister {
    default void preInit() {
        registerDefaultBlock();
        registerDefaultItem();
    }
    void init();
    void posInit();

    EnumModRegister getModRegister();
    float[] getLevels();
    String[] getLevelsString();
    String getGearOre(int i);

    default void recipeGenerator(int i1, BlockGeneratorBasic block, Block oldBlock) {
        ModRecipes.instance.addRecipeGenerator(block,
                EnumModItems.CONDUIT.itemMap.get(getModRegister())[i1],
                EnumModItems.TURBINE_ROTOR.itemMap.get(getModRegister())[i1].getItem(),
                (BlockMachineShell) EnumModBlock.MACHINE_SHELL.blockMap.get(getModRegister())[i1],
                oldBlock,
                getGearOre(i1));
    }

    default void registerDefaultBlock() {
        for (int i = 1; i < EnumModBlock.values().length; i++) {
            EnumModBlock modBlock = EnumModBlock.values()[i];
            if (!modBlock.isDefaultBlock || !modBlock.modRegister.isEnable()) {
                continue;
            }

            float[] levels = getLevels();
            String[] levelsString = getLevelsString();
            Block[] blocks = new Block[levels.length];

            for (int i2 = 0; i2 < levels.length; i2++) {
                blocks[i2] = modBlock.create(levels[i2], levelsString[i2]);
            }

            modBlock.addBlocks(getModRegister(), blocks);
        }
    }

    default void registerDefaultItem() {
        for (int i = 1; i < EnumModItems.values().length; i++) {
            EnumModItems modItems = EnumModItems.values()[i];
            if (modItems == EnumModItems.COIL || modItems == EnumModItems.GEAR || modItems == EnumModItems.CONDUIT) continue;

            String[] levelsString = getLevelsString();
            Item[] items = new Item[levelsString.length];

            for (int i2 = 0; i2 < levelsString.length; i2++) {
                items[i2] = modItems.create(levelsString[i2]);
            }

            modItems.addItems(getModRegister(), items);
        }
    }

    default void recipeBlock(EnumModBlock modBlock, Consumer<RecipeBlockInput> consumer) {
        AtomicInteger i = new AtomicInteger();
        Arrays.stream(modBlock.blockMap.get(getModRegister()))
                .filter(Objects::nonNull)
                .forEach(block -> {
                    consumer.accept(new RecipeBlockInput(block, i.get()));
                    i.getAndIncrement();
                });
    }
    default void recipeItem(EnumModItems modItems, Consumer<RecipeItemInput> consumer) {
        AtomicInteger i = new AtomicInteger();
        Arrays.stream(modItems.itemMap.get(getModRegister()))
                .filter(Objects::nonNull)
                .forEach(item -> {
                    consumer.accept(new RecipeItemInput(item, i.get()));
                    i.getAndIncrement();
                });
    }


    class RecipeBlockInput {
        public final Block block;
        public final int i;
        RecipeBlockInput(Block block, int i) {
            this.block = block;
            this.i = i;
        }
    }

    class RecipeItemInput {
        public final ItemStack item;
        public final int i;
        RecipeItemInput(ItemStack item, int i) {
            this.item = item;
            this.i = i;
        }
    }
}
