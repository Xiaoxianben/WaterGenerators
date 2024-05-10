package com.xiaoxianben.watergenerators.init;

import com.xiaoxianben.watergenerators.Main;
import com.xiaoxianben.watergenerators.api.IHasInit;
import com.xiaoxianben.watergenerators.blocks.BlockBase;
import com.xiaoxianben.watergenerators.blocks.BlocksFluid;
import com.xiaoxianben.watergenerators.blocks.BlocksGenerator;
import com.xiaoxianben.watergenerators.blocks.BlocksMachine;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class ModBlocks {
    /**
     * init列表
     */
    public static List<IHasInit> initList = new ArrayList<>();

    public static Block GOLD_PLATED_IRON_BLOCK;
    public static Block machineShell_frame;

    public static void preInit() {
        GOLD_PLATED_IRON_BLOCK = new BlockBase("block_goldPlatedIron", Material.IRON, Main.Item_TAB);
        machineShell_frame = new BlockBase("machineShell_frame", Material.IRON, Main.MACHINE_TAB) {
            public boolean canPlaceBlockAt(@Nonnull World worldIn, @Nonnull BlockPos pos) {
                return false;
            }
        };

        BlocksGenerator generator = new BlocksGenerator();
        BlocksMachine machine = new BlocksMachine();
        BlocksFluid blocksFluid = new BlocksFluid();

        initList.add(generator);
        initList.add(machine);
        initList.add(blocksFluid);

        for (IHasInit init : initList) {
            init.init();
        }
    }

    public static void init() {
        ModRecipes.registryBlock(Item.getItemFromBlock(GOLD_PLATED_IRON_BLOCK), ModItems.GOLD_PLATED_IRON_INGOT);

        ModRecipes.registryShell(machineShell_frame, null, null);

        for (IHasInit init : initList) {
            init.initRecipes();
        }
    }
}
