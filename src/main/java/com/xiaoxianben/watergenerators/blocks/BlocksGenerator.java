package com.xiaoxianben.watergenerators.blocks;

import com.xiaoxianben.watergenerators.API.IHasInit;
import com.xiaoxianben.watergenerators.init.ModRecipes;
import com.xiaoxianben.watergenerators.tileEntity.TECreateGenerator;
import com.xiaoxianben.watergenerators.tileEntity.TETurbineGenerator;
import com.xiaoxianben.watergenerators.tileEntity.TEWaterGenerator;
import com.xiaoxianben.watergenerators.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlocksGenerator implements IHasInit {

    public static int energyBasic = 10;

    // GENERATOR
    public static BlockTEBasic createGenerator;

    public static BlockTEBasic TURBINE_GENERATOR_LEVEL1; // 铁
    public static BlockTEBasic TURBINE_GENERATOR_LEVEL2; // 镀金
    public static BlockTEBasic TURBINE_GENERATOR_LEVEL3; // 钻石
    public static BlockTEBasic TURBINE_GENERATOR_LEVEL4; // 黑曜石
    public static BlockTEBasic TURBINE_GENERATOR_LEVEL5; // 绿宝石
    public static BlockTEBasic TURBINE_GENERATOR_DarkSteel; // 玄钢
    public static BlockTEBasic TURBINE_GENERATOR_EnergeticAlloy; // 充能合金锭
    public static BlockTEBasic TURBINE_GENERATOR_VibrantAlloy; // 脉冲合金锭
    public static BlockTEBasic TURBINE_GENERATOR_EndSteel; // 末影钢
    public static BlockTEBasic TURBINE_GENERATOR_Steel; // 钢
    public static BlockTEBasic TURBINE_GENERATOR_Invar; // 殷钢
    public static BlockTEBasic TURBINE_GENERATOR_Electrum; // 琥珀金
    public static BlockTEBasic TURBINE_GENERATOR_Signalum; // 信素
    public static BlockTEBasic TURBINE_GENERATOR_Enderium; // 末影

    public static BlockTEBasic WATER_GENERATOR_LEVEL1;
    public static BlockTEBasic WATER_GENERATOR_LEVEL2;
    public static BlockTEBasic WATER_GENERATOR_LEVEL3;
    public static BlockTEBasic WATER_GENERATOR_LEVEL4;
    public static BlockTEBasic WATER_GENERATOR_LEVEL5;
    public static BlockTEBasic WATER_GENERATOR_Steel;
    public static BlockTEBasic WATER_GENERATOR_Invar;
    public static BlockTEBasic WATER_GENERATOR_Electrum;
    public static BlockTEBasic WATER_GENERATOR_Signalum;
    public static BlockTEBasic WATER_GENERATOR_Enderium;


    public void init() {
        createGenerator = new BlockCreateGenerator("create_generator", (int) (Math.pow(2, 31) - 1));

        TURBINE_GENERATOR_LEVEL1 = turbineGeneratorLevel0("turbine_generator_level1", energyBasic, "1"); // 铁
        TURBINE_GENERATOR_LEVEL2 = turbineGeneratorLevel0("turbine_generator_level2", energyBasic*2, "2"); // 镀金
        TURBINE_GENERATOR_LEVEL3 = turbineGeneratorLevel0("turbine_generator_level3", energyBasic*8, "3"); // 钻石
        TURBINE_GENERATOR_LEVEL4 = turbineGeneratorLevel0("turbine_generator_level4", energyBasic*32, "4"); // 黑曜石
        TURBINE_GENERATOR_LEVEL5 = turbineGeneratorLevel0("turbine_generator_level5", energyBasic*128, "5"); // 绿宝石
        if (Loader.isModLoaded("thermalfoundation")) {
            TURBINE_GENERATOR_Steel = turbineGeneratorLevel1("turbine_generator_steel", energyBasic*128*4, "钢"); // 钢
            TURBINE_GENERATOR_Invar = turbineGeneratorLevel1("turbine_generator_invar", energyBasic*128*16, "殷钢"); // 殷钢
            TURBINE_GENERATOR_Electrum = turbineGeneratorLevel1("turbine_generator_electrum", energyBasic*128*64, "琥珀金"); // 琥珀金
            TURBINE_GENERATOR_Signalum = turbineGeneratorLevel1("turbine_generator_signalum", energyBasic*128*64*4, "信素"); // 信素
            TURBINE_GENERATOR_Enderium = turbineGeneratorLevel1("turbine_generator_enderium", energyBasic*128*64*16, "末影"); // 末影
        }

        WATER_GENERATOR_LEVEL1 = waterGeneratorLevel0("water_generator_level1", energyBasic*2, "1"); // 铁
        WATER_GENERATOR_LEVEL2 = waterGeneratorLevel0("water_generator_level2", energyBasic*4, "2"); // 镀金
        WATER_GENERATOR_LEVEL3 = waterGeneratorLevel0("water_generator_level3", energyBasic*16, "3"); // 钻石
        WATER_GENERATOR_LEVEL4 = waterGeneratorLevel0("water_generator_level4", energyBasic*64, "4"); // 黑曜石
        WATER_GENERATOR_LEVEL5 = waterGeneratorLevel0("water_generator_level5", energyBasic*64*4, "5"); // 绿宝石
        if (Loader.isModLoaded("thermalfoundation")) {
            WATER_GENERATOR_Steel = waterGeneratorLevel1("water_generator_steel", energyBasic*64*16, "钢"); // 钢
            WATER_GENERATOR_Invar = waterGeneratorLevel1("water_generator_invar", energyBasic*64*64, "殷钢"); // 殷钢
            WATER_GENERATOR_Electrum = waterGeneratorLevel1("water_generator_electrum", energyBasic*64*64*4, "琥珀金"); // 琥珀金
            WATER_GENERATOR_Signalum = waterGeneratorLevel1("water_generator_signalum", energyBasic*64*64*16, "信素"); // 信素
            WATER_GENERATOR_Enderium = waterGeneratorLevel1("water_generator_enderium", energyBasic*64*64*64, "末影"); // 末影
        }

    }
    public void initRegistry() {
        ModRecipes.registryGenerator(TURBINE_GENERATOR_LEVEL1, (byte) 1, (byte) 1);
        ModRecipes.registryGenerator(TURBINE_GENERATOR_LEVEL2, (byte) 2, (byte) 1);
        ModRecipes.registryGenerator(TURBINE_GENERATOR_LEVEL3, (byte) 3, (byte) 1);
        ModRecipes.registryGenerator(TURBINE_GENERATOR_LEVEL4, (byte) 4, (byte) 1);
        ModRecipes.registryGenerator(TURBINE_GENERATOR_LEVEL5, (byte) 5, (byte) 1);
        if (Loader.isModLoaded("thermalfoundation")) {
            ModRecipes.registryGenerator(TURBINE_GENERATOR_Steel, (byte) 6, (byte) 1);
            ModRecipes.registryGenerator(TURBINE_GENERATOR_Invar, (byte) 7, (byte) 1);
            ModRecipes.registryGenerator(TURBINE_GENERATOR_Electrum, (byte) 8, (byte) 1);
            ModRecipes.registryGenerator(TURBINE_GENERATOR_Signalum, (byte) 9, (byte) 1);
            ModRecipes.registryGenerator(TURBINE_GENERATOR_Enderium, (byte) 10, (byte) 1);
        }
        ModRecipes.registryGenerator(WATER_GENERATOR_LEVEL1, (byte) 1, (byte) 2);
        ModRecipes.registryGenerator(WATER_GENERATOR_LEVEL2, (byte) 2, (byte) 2);
        ModRecipes.registryGenerator(WATER_GENERATOR_LEVEL3, (byte) 3, (byte) 2);
        ModRecipes.registryGenerator(WATER_GENERATOR_LEVEL4, (byte) 4, (byte) 2);
        ModRecipes.registryGenerator(WATER_GENERATOR_LEVEL5, (byte) 5, (byte) 2);
        if (Loader.isModLoaded("thermalfoundation")) {
            ModRecipes.registryGenerator(WATER_GENERATOR_Steel, (byte) 6, (byte) 2);
            ModRecipes.registryGenerator(WATER_GENERATOR_Invar, (byte) 7, (byte) 2);
            ModRecipes.registryGenerator(WATER_GENERATOR_Electrum, (byte) 8, (byte) 2);
            ModRecipes.registryGenerator(WATER_GENERATOR_Signalum, (byte) 9, (byte) 2);
            ModRecipes.registryGenerator(WATER_GENERATOR_Enderium, (byte) 10, (byte) 2);
        }
        GameRegistry.registerTileEntity(TETurbineGenerator.class, new ResourceLocation(Reference.MOD_ID, "tileTurbineGenerator"));
        GameRegistry.registerTileEntity(TEWaterGenerator.class, new ResourceLocation(Reference.MOD_ID, "tileWaterGenerator"));
        GameRegistry.registerTileEntity(TECreateGenerator.class, new ResourceLocation(Reference.MOD_ID, "TileCreateGenerator"));

    }

    public BlockTEBasic turbineGeneratorLevel0(String name, int basePower, String level) {
        return new BlockTurbineGenerator(name, basePower, 0).setLevel(level);
    }
    public BlockTEBasic turbineGeneratorLevel1(String name, int basePower, String level) {
        return new BlockTurbineGenerator(name, basePower, 1).setLevel(level);
    }
    public BlockTEBasic waterGeneratorLevel0(String name, int basePower, String level) {
        return new BlockWaterGenerator(name, basePower, 0).setLevel(level);
    }
    public BlockTEBasic waterGeneratorLevel1(String name, int basePower, String level) {
        return new BlockWaterGenerator(name, basePower, 1).setLevel(level);
    }
}
