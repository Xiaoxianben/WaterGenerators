package com.xiaoxianben.watergenerators.init;

import com.xiaoxianben.watergenerators.WaterGenerators;
import com.xiaoxianben.watergenerators.api.IHasInit;
import com.xiaoxianben.watergenerators.api.IModInit;
import com.xiaoxianben.watergenerators.blocks.BlockBase;
import com.xiaoxianben.watergenerators.blocks.BlocksFluid;
import com.xiaoxianben.watergenerators.blocks.BlocksGenerator;
import com.xiaoxianben.watergenerators.blocks.BlocksMachine;
import com.xiaoxianben.watergenerators.items.ItemBlockPrivate;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ModBlocks implements IModInit {

    @Nullable
    public static LinkedHashSet<Block> allMachineShell = new LinkedHashSet<>();
    @Nullable
    public static LinkedHashSet<Block> allMachineVaporization = new LinkedHashSet<>();

    @Nullable
    public static LinkedHashSet<Block> allGeneratorTurbine = new LinkedHashSet<>();
    @Nullable
    public static LinkedHashSet<Block> allGeneratorFluid = new LinkedHashSet<>();
    @Nullable
    public static LinkedHashSet<Block> allGeneratorSteam = new LinkedHashSet<>();
    @Nullable
    public static LinkedHashSet<Block> allGeneratorWater = new LinkedHashSet<>();


    public static Block GOLD_PLATED_IRON_BLOCK;
    public static Block machineShell_frame;


    /**
     * init列表
     */
    public List<IHasInit> initList = new ArrayList<>();

    private static void addBlocks(LinkedHashSet<Block> blocks) {
        Objects.requireNonNull(WaterGenerators.BLOCKS).addAll(blocks);
        Objects.requireNonNull(WaterGenerators.ITEMS).addAll(blocks.stream()
                .map(ItemBlockPrivate::new)
                .collect(Collectors.toList()));
    }

    public static void addBlocks() {
        addBlocks(allMachineShell);
        addBlocks(allMachineVaporization);

        addBlocks(allGeneratorTurbine);
        addBlocks(allGeneratorFluid);
        addBlocks(allGeneratorSteam);
        addBlocks(allGeneratorWater);
    }

    public void preInit() {
        Objects.requireNonNull(WaterGenerators.BLOCKS);
        GOLD_PLATED_IRON_BLOCK = new BlockBase("block_goldPlatedIron", Material.IRON, WaterGenerators.Item_TAB, null, (byte) 1);
        machineShell_frame = new BlockBase("machineshell_frame", Material.IRON, WaterGenerators.MACHINE_TAB, null, (byte) 1) {
            public boolean canPlaceBlockAt(@Nonnull World worldIn, @Nonnull BlockPos pos) {
                return false;
            }
        };


        initList.add(new BlocksGenerator());
        initList.add(new BlocksMachine());
        initList.add(new BlocksFluid());


        for (IHasInit init : initList) {
            init.init();
        }

    }

    @Override
    public void init() {
        allMachineShell = null;
        allMachineVaporization = null;
        allGeneratorTurbine = null;
        allGeneratorFluid = null;
        allGeneratorSteam = null;
        allGeneratorWater = null;

        ModRecipes.addRecipeBlock(Item.getItemFromBlock(GOLD_PLATED_IRON_BLOCK), ModItems.GOLD_PLATED_IRON_INGOT);

        ModRecipes.addRecipeShell(machineShell_frame, null, null);

        for (IHasInit init : initList) {
            init.initRecipes();
        }
    }
}
