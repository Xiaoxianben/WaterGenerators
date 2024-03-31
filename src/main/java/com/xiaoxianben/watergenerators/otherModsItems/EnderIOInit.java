package com.xiaoxianben.watergenerators.otherModsItems;

import com.xiaoxianben.watergenerators.API.IOtherModInit;
import com.xiaoxianben.watergenerators.blocks.BlockGeneratorBasic;
import com.xiaoxianben.watergenerators.blocks.BlockGeneratorSteam;
import com.xiaoxianben.watergenerators.blocks.BlockMachineShell;
import com.xiaoxianben.watergenerators.blocks.BlockMachineVaporization;
import com.xiaoxianben.watergenerators.init.ModBlocks;
import com.xiaoxianben.watergenerators.init.ModItems;
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
    public static String[] EnderIOName;
    /**
     * VibrantAlloy,
     * DarkSteel,
     * EndSteel,
     * MelodicAlloy,
     * StellarAlloy
     */
    public static String[] EnderIOIngotOre;
    /**
     * @implNote enderIO_Generators = new BlockGeneratorBasic[2][5];
     * {BlockGeneratorTurbine[5], BlockGeneratorWater[5]}
     * */
    public static BlockGeneratorBasic[][] enderIO_Generators;
    /**
     * EndSteel,
     * MelodicAlloy,
     * StellarAlloy
     */
    public static Item[] enderIO_Gear;
    /**
     * 0:0,1,2;
     * 1:10,11
     */
    public static Item[] enderIO_Conduit;
    public static Item[] enderIO_TurbineRotor;
    public static BlockMachineShell[] enderIO_machineShell;
    public static BlockMachineVaporization[] enderIO_machineVaporization = new BlockMachineVaporization[5];
    public static BlockGeneratorSteam[] enderIO_generatorSteam = new BlockGeneratorSteam[5];

    public void init() {
        EnderIOName = new String[]{"vibrantalloy", "darksteel", "endsteel", "melodicalloy", "stellaralloy", "脉冲合金", "玄钢", "末影钢", "旋律合金", "恒星合金"};
        EnderIOIngotOre = new String[]{"VibrantAlloy", "DarkSteel", "EndSteel", "MelodicAlloy", "StellarAlloy"};
        enderIO_Generators = new BlockGeneratorBasic[2][5];
        enderIO_Gear = new Item[3];
        enderIO_Conduit = new Item[2];
        enderIO_TurbineRotor = new Item[5];
        enderIO_machineShell = new BlockMachineShell[5];

        for (int i = 0; i < EnderIOIngotOre.length; i++) {
            booleans[i] = i<3? Loader.isModLoaded("enderio") : Loader.isModLoaded("enderioendergy");
//            EnderIOInit.booleans[i] = true;
        }

    }

    @Override
    public void initBlock() {
        ModBlocks.initList.add(new EnderIOBlocks());
    }

    @Override
    public void initItem() {
        ModItems.initList.add(new EnderIOItems());
    }

    @Override
    public void initOre() {
        EnderIOOre.init();
    }
}
