package com.xiaoxianben.watergenerators.otherModsItems;

import com.xiaoxianben.watergenerators.event.ConfigLoader;
import net.minecraftforge.fml.common.Loader;

import java.util.ArrayList;
import java.util.List;

/**
 * 本模组对其他模组的联动主类, 不加载此类会不添加其他联动方块和物品
 */
public class otherInit {

    private static final boolean[] aBooleans = new boolean[2];
    private static final List<IOtherModInit> IOtherModInit = new ArrayList<>();

    public static void preInit() {

        aBooleans[0] = Loader.isModLoaded("thermalfoundation");
        aBooleans[1] = Loader.isModLoaded("enderio");

        if (aBooleans[0]) {
            TFInit TFInit = new TFInit();
            IOtherModInit.add(TFInit);
            ConfigLoader.logger().info("The thermal extension have loaded");

        } else
            ConfigLoader.logger().info("The thermal extension is not loaded");

        if (aBooleans[1]) {
            EnderIOInit enderIOInit = new EnderIOInit();
            IOtherModInit.add(enderIOInit);
            enderIOInit.init();
            ConfigLoader.logger().info("The enderio extension have loaded");

        } else
            ConfigLoader.logger().info("The enderio extension is not loaded");
    }

    public static void initGeneratorTurbine() {
        for (IOtherModInit otherModInit : IOtherModInit) {
            otherModInit.initGeneratorTurbine();
        }
    }

    public static void initGeneratorFluid() {
        for (IOtherModInit otherModInit : IOtherModInit) {
            otherModInit.initGeneratorFluid();
        }
    }

    public static void initGeneratorWater() {
        for (IOtherModInit otherModInit : IOtherModInit) {
            otherModInit.initGeneratorWater();
        }
    }

    public static void initGeneratorSteam() {
        for (IOtherModInit otherModInit : IOtherModInit) {
            otherModInit.initGeneratorSteam();
        }
    }

    public static void initMachineShell() {
        for (IOtherModInit otherModInit : IOtherModInit) {
            otherModInit.initMachineShell();
        }
    }

    public static void initMachineVaporization() {
        for (IOtherModInit otherModInit : IOtherModInit) {
            otherModInit.initMachineVaporization();
        }
    }

    public static void initGear() {
        for (IOtherModInit otherModInit : IOtherModInit) {
            otherModInit.initGear();
        }
    }

    public static void initTurbineRotor() {
        for (IOtherModInit otherModInit : IOtherModInit) {
            otherModInit.initTurbineRotor();
        }
    }

    public static void initOre() {
        for (IOtherModInit otherModInit : IOtherModInit) {
            otherModInit.initOre();
        }
    }

    public static void initRecipes() {
        for (IOtherModInit otherModInit : IOtherModInit) {
            otherModInit.initRecipes();
        }
    }
}
