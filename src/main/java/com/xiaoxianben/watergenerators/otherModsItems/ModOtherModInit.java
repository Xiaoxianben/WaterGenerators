package com.xiaoxianben.watergenerators.otherModsItems;

import com.xiaoxianben.watergenerators.WaterGenerators;
import com.xiaoxianben.watergenerators.api.IModInit;
import com.xiaoxianben.watergenerators.config.ConfigLoader;
import net.minecraftforge.fml.common.Loader;

import java.util.ArrayList;
import java.util.List;

/**
 * 本模组对其他模组的联动主类, 不加载此类会不添加其他联动方块和物品 <p>
 * 可通过将 <tt>implements</tt> {@link IOtherModInit} 的类 {@link ModOtherModInit#OtherModInit} 中, 添加扩展. <p>
 * {@link WaterGenerators#modInit} 的最后一个值一定为 本类。
 */
public class ModOtherModInit implements IModInit {

    private final List<IOtherModInit> OtherModInit = new ArrayList<>();


    public void preInit() {

        boolean[] aBooleans = new boolean[3];

        aBooleans[0] = Loader.isModLoaded("thermalfoundation");
        aBooleans[1] = Loader.isModLoaded("enderio");
        aBooleans[2] = Loader.isModLoaded("mekanism");

        if (aBooleans[0]) {
            TFInit TFInit = new TFInit();
            OtherModInit.add(TFInit);
            ConfigLoader.logger().info("The thermal extension have loaded");

        } else ConfigLoader.logger().info("The thermal extension is not loaded");

        if (aBooleans[1]) {
            EnderIOInit enderIOInit = new EnderIOInit();
            OtherModInit.add(enderIOInit);
            ConfigLoader.logger().info("The enderio extension have loaded");

        } else ConfigLoader.logger().info("The enderio extension is not loaded");

        if (aBooleans[2]) {
            MekInit mekInit = new MekInit();
            OtherModInit.add(mekInit);
            ConfigLoader.logger().info("The mekanism extension have loaded");

        } else ConfigLoader.logger().info("The mekanism extension is not loaded");

        OtherModInit.forEach(othermod -> {
            othermod.initBlocks();
            othermod.initItems();
        });

    }

    @Override
    public void init() {

        OtherModInit.forEach(othermod -> {
            othermod.initOre();
            othermod.initRecipes();
        });

        OtherModInit.clear();

    }

}
