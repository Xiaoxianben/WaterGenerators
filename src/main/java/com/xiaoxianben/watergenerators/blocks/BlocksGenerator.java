package com.xiaoxianben.watergenerators.blocks;

import com.xiaoxianben.watergenerators.API.IHasInit;
import com.xiaoxianben.watergenerators.init.ModRecipes;
import com.xiaoxianben.watergenerators.tileEntity.TECreateGenerator;
import com.xiaoxianben.watergenerators.tileEntity.TETurbineGenerator;
import com.xiaoxianben.watergenerators.tileEntity.TEWaterGenerator;
import com.xiaoxianben.watergenerators.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlocksGenerator implements IHasInit {

    public static int energyBasic = 10;

    // GENERATOR
    public static Block createGenerator;

    public static Block TURBINE_GENERATOR_LEVEL1; // 铁
    public static Block TURBINE_GENERATOR_LEVEL2; // 镀金
    public static Block TURBINE_GENERATOR_LEVEL3; // 钻石
    public static Block TURBINE_GENERATOR_LEVEL4; // 黑曜石
    public static Block TURBINE_GENERATOR_LEVEL5; // 绿宝石
    public static Block TURBINE_GENERATOR_LEVEL6; // 钢
    public static Block TURBINE_GENERATOR_LEVEL7; // 殷钢
    public static Block TURBINE_GENERATOR_LEVEL8; // 琥珀金
    public static Block TURBINE_GENERATOR_LEVEL9; // 信素
    public static Block TURBINE_GENERATOR_LEVEL10; // 末影

    public static Block WATER_GENERATOR_LEVEL1;
    public static Block WATER_GENERATOR_LEVEL2;
    public static Block WATER_GENERATOR_LEVEL3;
    public static Block WATER_GENERATOR_LEVEL4;
    public static Block WATER_GENERATOR_LEVEL5;
    public static Block WATER_GENERATOR_LEVEL6;
    public static Block WATER_GENERATOR_LEVEL7;
    public static Block WATER_GENERATOR_LEVEL8;
    public static Block WATER_GENERATOR_LEVEL9;
    public static Block WATER_GENERATOR_LEVEL10;


    public void init() {
        createGenerator = new BlockCreateGenerator("create_generator999", (int) (Math.pow(2, 31) - 1));

        TURBINE_GENERATOR_LEVEL1 = new BlockTurbineGenerator("turbine_generator_level1", energyBasic); // 铁
        TURBINE_GENERATOR_LEVEL2 = new BlockTurbineGenerator("turbine_generator_level2", energyBasic*2); // 镀金
        TURBINE_GENERATOR_LEVEL3 = new BlockTurbineGenerator("turbine_generator_level3", energyBasic*8); // 钻石
        TURBINE_GENERATOR_LEVEL4 = new BlockTurbineGenerator("turbine_generator_level4", energyBasic*32); // 黑曜石
        TURBINE_GENERATOR_LEVEL5 = new BlockTurbineGenerator("turbine_generator_level5", energyBasic*128); // 绿宝石
        if (Loader.isModLoaded("thermalfoundation")) {
            TURBINE_GENERATOR_LEVEL6 = new BlockTurbineGenerator("turbine_generator_level6", energyBasic*128*4); // 钢
            TURBINE_GENERATOR_LEVEL7 = new BlockTurbineGenerator("turbine_generator_level7", energyBasic*128*16); // 殷钢
            TURBINE_GENERATOR_LEVEL8 = new BlockTurbineGenerator("turbine_generator_level8", energyBasic*128*64); // 琥珀金
            TURBINE_GENERATOR_LEVEL9 = new BlockTurbineGenerator("turbine_generator_level9", energyBasic*128*64*4); // 信素
            TURBINE_GENERATOR_LEVEL10 = new BlockTurbineGenerator("turbine_generator_level10", energyBasic*128*64*16); // 末影
        }

        WATER_GENERATOR_LEVEL1 = new BlockWaterGenerator("water_generator_level1", energyBasic*2); // 铁
        WATER_GENERATOR_LEVEL2 = new BlockWaterGenerator("water_generator_level2", energyBasic*4); // 镀金
        WATER_GENERATOR_LEVEL3 = new BlockWaterGenerator("water_generator_level3", energyBasic*16); // 钻石
        WATER_GENERATOR_LEVEL4 = new BlockWaterGenerator("water_generator_level4", energyBasic*64); // 黑曜石
        WATER_GENERATOR_LEVEL5 = new BlockWaterGenerator("water_generator_level5", energyBasic*64*4); // 绿宝石
        if (Loader.isModLoaded("thermalfoundation")) {
            WATER_GENERATOR_LEVEL6 = new BlockWaterGenerator("water_generator_level6", energyBasic*64*16); // 钢
            WATER_GENERATOR_LEVEL7 = new BlockWaterGenerator("water_generator_level7", energyBasic*64*64); // 殷钢
            WATER_GENERATOR_LEVEL8 = new BlockWaterGenerator("water_generator_level8", energyBasic*64*64*4); // 琥珀金
            WATER_GENERATOR_LEVEL9 = new BlockWaterGenerator("water_generator_level9", energyBasic*64*64*16); // 信素
            WATER_GENERATOR_LEVEL10 = new BlockWaterGenerator("water_generator_level10", energyBasic*64*64*64); // 末影
        }

    }
    public void initRegistry() {
        ModRecipes.registryGenerator(TURBINE_GENERATOR_LEVEL1, (byte) 1, (byte) 1);
        ModRecipes.registryGenerator(TURBINE_GENERATOR_LEVEL2, (byte) 2, (byte) 1);
        ModRecipes.registryGenerator(TURBINE_GENERATOR_LEVEL3, (byte) 3, (byte) 1);
        ModRecipes.registryGenerator(TURBINE_GENERATOR_LEVEL4, (byte) 4, (byte) 1);
        ModRecipes.registryGenerator(TURBINE_GENERATOR_LEVEL5, (byte) 5, (byte) 1);
        if (Loader.isModLoaded("thermalfoundation")) {
            ModRecipes.registryGenerator(TURBINE_GENERATOR_LEVEL6, (byte) 6, (byte) 1);
            ModRecipes.registryGenerator(TURBINE_GENERATOR_LEVEL7, (byte) 7, (byte) 1);
            ModRecipes.registryGenerator(TURBINE_GENERATOR_LEVEL8, (byte) 8, (byte) 1);
            ModRecipes.registryGenerator(TURBINE_GENERATOR_LEVEL9, (byte) 9, (byte) 1);
            ModRecipes.registryGenerator(TURBINE_GENERATOR_LEVEL10, (byte) 10, (byte) 1);
        }
        ModRecipes.registryGenerator(WATER_GENERATOR_LEVEL1, (byte) 1, (byte) 2);
        ModRecipes.registryGenerator(WATER_GENERATOR_LEVEL2, (byte) 2, (byte) 2);
        ModRecipes.registryGenerator(WATER_GENERATOR_LEVEL3, (byte) 3, (byte) 2);
        ModRecipes.registryGenerator(WATER_GENERATOR_LEVEL4, (byte) 4, (byte) 2);
        ModRecipes.registryGenerator(WATER_GENERATOR_LEVEL5, (byte) 5, (byte) 2);
        if (Loader.isModLoaded("thermalfoundation")) {
            ModRecipes.registryGenerator(WATER_GENERATOR_LEVEL6, (byte) 6, (byte) 2);
            ModRecipes.registryGenerator(WATER_GENERATOR_LEVEL7, (byte) 7, (byte) 2);
            ModRecipes.registryGenerator(WATER_GENERATOR_LEVEL8, (byte) 8, (byte) 2);
            ModRecipes.registryGenerator(WATER_GENERATOR_LEVEL9, (byte) 9, (byte) 2);
            ModRecipes.registryGenerator(WATER_GENERATOR_LEVEL10, (byte) 10, (byte) 2);
        }
        GameRegistry.registerTileEntity(TETurbineGenerator.class, new ResourceLocation(Reference.MOD_ID, "tileTurbineGenerator"));
        GameRegistry.registerTileEntity(TEWaterGenerator.class, new ResourceLocation(Reference.MOD_ID, "tileWaterGenerator"));
        GameRegistry.registerTileEntity(TECreateGenerator.class, new ResourceLocation(Reference.MOD_ID, "TileCreateGenerator"));

    }
}
