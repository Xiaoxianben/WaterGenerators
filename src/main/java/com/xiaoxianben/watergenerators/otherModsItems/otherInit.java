package com.xiaoxianben.watergenerators.otherModsItems;

import com.xiaoxianben.watergenerators.API.IOtherModInit;
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
            ConfigLoader.logger().info("The thermal extension generator have loaded");
        } else
            ConfigLoader.logger().info("The thermal extension generator is not loaded");

        if (aBooleans[1]) {
            EnderIOInit enderIOInit = new EnderIOInit();
            IOtherModInit.add(enderIOInit);
            enderIOInit.init();
            ConfigLoader.logger().info("The enderio extension have loaded");
        } else
            ConfigLoader.logger().info("The enderio extension generator is not loaded");
    }

    public static void initBlock() {
        for (IOtherModInit otherModInit : IOtherModInit) {
            otherModInit.initBlock();
        }
    }

    public static void initItem() {
        for (IOtherModInit otherModInit : IOtherModInit) {
            otherModInit.initItem();
        }
    }

    public static void initOre() {
        for (IOtherModInit otherModInit : IOtherModInit) {
            otherModInit.initOre();
        }
    }
}
