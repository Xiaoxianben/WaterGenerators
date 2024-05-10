package com.xiaoxianben.watergenerators.otherModsItems;

import com.xiaoxianben.watergenerators.blocks.generator.BlockGeneratorBasic;
import com.xiaoxianben.watergenerators.blocks.generator.BlockGeneratorFluid;
import com.xiaoxianben.watergenerators.blocks.generator.BlockGeneratorSteam;
import com.xiaoxianben.watergenerators.blocks.machine.BlockMachineShell;
import com.xiaoxianben.watergenerators.blocks.machine.BlockMachineVaporization;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Loader;

public class EnderIOInit implements IOtherModInit {
    /**
     * has[
     * VibrantAlloy,
     * DarkSteel,
     * EndSteel,
     * MelodicAlloy,
     * StellarAlloy
     * ]
     */
    public static boolean[] booleans = new boolean[5];
    public static float[] level = {6.5f, 7.5f, 8.5f, 9.5f, 10.5f};
    /**
     * vibrantalloy,
     * darksteel,
     * endsteel,
     * melodicalloy,
     * stellaralloy,
     * 脉冲合金,
     * 玄钢,
     * 末影钢,
     * 旋律合金,
     * 恒星合金
     */
    public static String[] EnderIOName = new String[]{"vibrantalloy", "darksteel", "endsteel", "melodicalloy", "stellaralloy", "脉冲合金", "玄钢", "末影钢", "旋律合金", "恒星合金"};
    /**
     * VibrantAlloy,
     * DarkSteel,
     * EndSteel,
     * MelodicAlloy,
     * StellarAlloy
     */
    public static String[] EnderIOIngotOre = new String[]{"VibrantAlloy", "DarkSteel", "EndSteel", "MelodicAlloy", "StellarAlloy"};
    /**
     * EndSteel,
     * MelodicAlloy,
     * StellarAlloy
     */
    public static Item[] enderIO_Gear = new Item[3];
    /**
     * 0:0,1,2;
     * 1:10,11
     */
    public static Item[] enderIO_Conduit = new Item[2];
    public static Item[] enderIO_TurbineRotor = new Item[5];
    /**
     * @implNote enderIO_Generators = new BlockGeneratorBasic[2][5];
     * {BlockGeneratorTurbine[5], BlockGeneratorWater[5]}
     */
    public static BlockGeneratorBasic[][] enderIO_Generators = new BlockGeneratorBasic[2][5];
    public static BlockGeneratorSteam[] enderIO_generatorSteam = new BlockGeneratorSteam[5];
    public static BlockGeneratorFluid[] enderIO_generatorFluid = new BlockGeneratorFluid[5];
    public static BlockMachineShell[] enderIO_machineShell = new BlockMachineShell[5];
    public static BlockMachineVaporization[] enderIO_machineVaporization = new BlockMachineVaporization[5];

    public void init() {
        for (int i = 0; i < EnderIOIngotOre.length; i++) {
            booleans[i] = i < 3 ? Loader.isModLoaded("enderio") : Loader.isModLoaded("enderioendergy");
//            EnderIOInit.booleans[i] = true;
        }
    }

    @Override
    public void initGeneratorWater() {
        EnderIOBlocks.initGeneratorWater();
    }

    @Override
    public void initGeneratorTurbine() {
        EnderIOBlocks.initGeneratorTurbine();
    }

    @Override
    public void initGeneratorFluid() {
        EnderIOBlocks.initGeneratorFluid();
    }

    @Override
    public void initGeneratorSteam() {
        EnderIOBlocks.initGeneratorSteam();
    }

    @Override
    public void initMachineShell() {
        EnderIOBlocks.initMachineShell();
    }

    @Override
    public void initMachineVaporization() {
        EnderIOBlocks.initMachineVaporization();
    }

    @Override
    public void initGear() {
        EnderIOItems.initGear();
    }

    @Override
    public void initTurbineRotor() {
        EnderIOItems.initTurbineRotor();
    }

    @Override
    public void initOre() {
        EnderIOOre.init();
    }

    @Override
    public void initRecipes() {
        EnderIOItems.initRecipes();
        EnderIOBlocks.initRecipes();
    }
}
