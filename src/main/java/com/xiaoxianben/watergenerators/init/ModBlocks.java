package com.xiaoxianben.watergenerators.init;

import com.xiaoxianben.watergenerators.API.IHasInit;
import com.xiaoxianben.watergenerators.Main;
import com.xiaoxianben.watergenerators.blocks.BlockBase;
import com.xiaoxianben.watergenerators.blocks.BlocksGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks {
    public static List<IHasInit> initList = new ArrayList<>();

    public static Block GOLD_PLATED_IRON_BLOCK;
    public static Block machineShell_frame;
    public static Block machineShell;

    public static void preInit() {
        GOLD_PLATED_IRON_BLOCK = new BlockBase("block_goldPlatedIron", Material.IRON, Main.INGOT_BLOCK_TAB);
        machineShell_frame = new BlockBase("block_machineShell_frame", Material.IRON, Main.MACHINE_TAB);
        machineShell = new BlockBase("block_machineShell", Material.IRON, Main.MACHINE_TAB);
        BlocksGenerator generator = new BlocksGenerator();
        initList.add(generator);
        for (IHasInit init : initList) {
            init.init();
        }
    }
    public static void init() {
        ModRecipes.registryBlock(Item.getItemFromBlock(GOLD_PLATED_IRON_BLOCK), ModItems.GOLD_PLATED_IRON_INGOT);

        ModRecipes.registryShell(Item.getItemFromBlock(machineShell_frame), Items.AIR, "");
        ModRecipes.registryShell(Item.getItemFromBlock(machineShell), Item.getItemFromBlock(machineShell_frame), "ingotGoldPlatedIron");

        for (IHasInit init : initList) {
            init.initRegistry();
        }
    }
}
