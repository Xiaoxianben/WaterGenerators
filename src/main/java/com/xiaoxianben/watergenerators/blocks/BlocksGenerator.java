package com.xiaoxianben.watergenerators.blocks;

import com.xiaoxianben.watergenerators.API.IHasInit;
import com.xiaoxianben.watergenerators.event.ConfigLoader;
import com.xiaoxianben.watergenerators.init.ModRecipes;
import com.xiaoxianben.watergenerators.items.ItemsMaterial;
import com.xiaoxianben.watergenerators.tileEntity.TEGeneratorCreate;
import com.xiaoxianben.watergenerators.tileEntity.TEGeneratorSteam;
import com.xiaoxianben.watergenerators.tileEntity.TEGeneratorTurbine;
import com.xiaoxianben.watergenerators.tileEntity.TEGeneratorWater;
import com.xiaoxianben.watergenerators.util.ModInformation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlocksGenerator implements IHasInit {

    public static int energyBasic = ConfigLoader.energyBasic;

    // GENERATOR
    public static BlockGeneratorBasic createGenerator;

    public static BlockGeneratorBasic TURBINE_GENERATOR_LEVEL1; // 铁
    public static BlockGeneratorBasic TURBINE_GENERATOR_LEVEL2; // 镀金
    public static BlockGeneratorBasic TURBINE_GENERATOR_LEVEL3; // 钻石
    public static BlockGeneratorBasic TURBINE_GENERATOR_LEVEL4; // 黑曜石
    public static BlockGeneratorBasic TURBINE_GENERATOR_LEVEL5; // 绿宝石

    public static BlockGeneratorBasic WATER_GENERATOR_LEVEL1;
    public static BlockGeneratorBasic WATER_GENERATOR_LEVEL2;
    public static BlockGeneratorBasic WATER_GENERATOR_LEVEL3;
    public static BlockGeneratorBasic WATER_GENERATOR_LEVEL4;
    public static BlockGeneratorBasic WATER_GENERATOR_LEVEL5;

    public static BlockGeneratorTurbine[] blockGeneratorTurbine = new BlockGeneratorTurbine[5];
    public static BlockGeneratorWater[] blockGeneratorWater = new BlockGeneratorWater[5];
    public static BlockGeneratorSteam[] blockGeneratorSteam = new BlockGeneratorSteam[5];


    public static String[] oreIngots = {"ingotIron", "ingotGoldPlatedIron", "gemDiamond", "obsidian", "gemEmerald"};
    public static String[] blockName = {"level1", "level2", "level3", "level4", "level5"};
    public static String[] gearName = {"gearIron", "gearGoldPlated", "gearDiamond", "gearObsidian", "gearEmerald"};
    public static int[] level = {1, 2, 3, 4, 5};

    public void init() {
        createGenerator = new BlockGeneratorCreate("create_generator", Integer.MAX_VALUE);

        for (int i = 0; i < 5; i++) {
            blockGeneratorTurbine[i] = registerTurbine(blockName[i], level[i]);
        }
        for (int i = 0; i < 5; i++) {
            blockGeneratorWater[i] = registerWater(blockName[i], level[i]);
        }
        for (int i = 0; i < 5; i++) {
            blockGeneratorSteam[i] = registerSteam(blockName[i], level[i]);
        }

        TURBINE_GENERATOR_LEVEL1 = blockGeneratorTurbine[0]; // 铁
        TURBINE_GENERATOR_LEVEL2 = blockGeneratorTurbine[1]; // 镀金
        TURBINE_GENERATOR_LEVEL3 = blockGeneratorTurbine[2]; // 钻石
        TURBINE_GENERATOR_LEVEL4 = blockGeneratorTurbine[3]; // 黑曜石
        TURBINE_GENERATOR_LEVEL5 = blockGeneratorTurbine[4]; // 绿宝石

        WATER_GENERATOR_LEVEL1 = blockGeneratorWater[0]; // 铁
        WATER_GENERATOR_LEVEL2 = blockGeneratorWater[1]; // 镀金
        WATER_GENERATOR_LEVEL3 = blockGeneratorWater[2]; // 钻石
        WATER_GENERATOR_LEVEL4 = blockGeneratorWater[3]; // 黑曜石
        WATER_GENERATOR_LEVEL5 = blockGeneratorWater[4]; // 绿宝石

    }

    public void initRecipes() {
        ModRecipes.registryGenerator_old(TURBINE_GENERATOR_LEVEL1, (byte) 1, (byte) 1);
        ModRecipes.registryGenerator_old(TURBINE_GENERATOR_LEVEL2, (byte) 2, (byte) 1);
        ModRecipes.registryGenerator_old(TURBINE_GENERATOR_LEVEL3, (byte) 3, (byte) 1);
        ModRecipes.registryGenerator_old(TURBINE_GENERATOR_LEVEL4, (byte) 4, (byte) 1);
        ModRecipes.registryGenerator_old(TURBINE_GENERATOR_LEVEL5, (byte) 5, (byte) 1);

        ModRecipes.registryGenerator_old(WATER_GENERATOR_LEVEL1, (byte) 1, (byte) 2);
        ModRecipes.registryGenerator_old(WATER_GENERATOR_LEVEL2, (byte) 2, (byte) 2);
        ModRecipes.registryGenerator_old(WATER_GENERATOR_LEVEL3, (byte) 3, (byte) 2);
        ModRecipes.registryGenerator_old(WATER_GENERATOR_LEVEL4, (byte) 4, (byte) 2);
        ModRecipes.registryGenerator_old(WATER_GENERATOR_LEVEL5, (byte) 5, (byte) 2);

        for (int i = 0; i < 5; i++) {
            registerGenerator(i);
        }

        GameRegistry.registerTileEntity(TEGeneratorTurbine.class, new ResourceLocation(ModInformation.MOD_ID, "TETurbineGenerator"));
        GameRegistry.registerTileEntity(TEGeneratorWater.class, new ResourceLocation(ModInformation.MOD_ID, "TEWaterGenerator"));
        GameRegistry.registerTileEntity(TEGeneratorCreate.class, new ResourceLocation(ModInformation.MOD_ID, "TECreateGenerator"));
        GameRegistry.registerTileEntity(TEGeneratorSteam.class, new ResourceLocation(ModInformation.MOD_ID, "TESteamGenerator"));

    }

    private void registerGenerator(int i) {
        ItemStack oldItem;
        if (i == 0) {
            UniversalBucket bucket = ForgeModContainer.getInstance().universalBucket;
            oldItem = new ItemStack(bucket);
            FluidStack fluidContents = new FluidStack(FluidRegistry.getFluid("steam"), bucket.getCapacity());

            NBTTagCompound tag = new NBTTagCompound();
            fluidContents.writeToNBT(tag);

            oldItem.setTagCompound(tag);
        } else {
            oldItem = Item.getItemFromBlock(blockGeneratorSteam[i - 1]).getDefaultInstance();
        }
        ModRecipes.registryGenerator_main(
                blockGeneratorSteam[i],
                ItemsMaterial.conduits[i].getDefaultInstance(),
                ItemsMaterial.turbines[i],
                gearName[i],
                BlocksMachine.machineShells[i],
                oldItem
        );
    }

    public BlockGeneratorTurbine registerTurbine(String name, int level) {
        return (BlockGeneratorTurbine) new BlockGeneratorTurbine(name, (long) (energyBasic * Math.pow(4, level)), level).setLevelName(String.valueOf(level));
    }

    public BlockGeneratorWater registerWater(String name, int level) {
        return (BlockGeneratorWater) new BlockGeneratorWater(name, (long) (energyBasic * Math.pow(4, level) * 1.5), level).setLevelName(String.valueOf(level));
    }

    public BlockGeneratorSteam registerSteam(String name, int level) {
        long steamBasePowerGeneration = (long) (energyBasic / 2 * Math.pow(4, level));
        return (BlockGeneratorSteam) new BlockGeneratorSteam(name, steamBasePowerGeneration <= 0 ? 1 : steamBasePowerGeneration, level).setLevelName(String.valueOf(level));
    }
}
