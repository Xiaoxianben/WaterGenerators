package com.xiaoxianben.watergenerators.otherModsItems;

import com.xiaoxianben.watergenerators.API.IHasInit;
import com.xiaoxianben.watergenerators.blocks.BlockGeneratorBasic;
import com.xiaoxianben.watergenerators.blocks.BlockGeneratorSteam;
import com.xiaoxianben.watergenerators.blocks.BlockGeneratorTurbine;
import com.xiaoxianben.watergenerators.blocks.BlockGeneratorWater;
import com.xiaoxianben.watergenerators.event.ConfigLoader;
import net.minecraft.client.resources.I18n;

public class otherBlocks implements IHasInit {

    protected static int energyBasic = ConfigLoader.energyBasic;

    @Override
    public void init() {
    }

    @Override
    public void initRecipes() {
    }

    /**
     * @return {BlockGeneratorTurbine, BlockGeneratorWater, steam}
     * */
    public BlockGeneratorBasic[] registryGenerator(String name, float level, String levelName) {
        levelName = I18n.format("level."+levelName+".name");
        return new BlockGeneratorBasic[]{
                new BlockGeneratorTurbine(name, (long) (energyBasic * Math.pow(4, level)), level).setLevelName(levelName),
                new BlockGeneratorWater(name, (long) (energyBasic * Math.pow(4, level) * 1.5), level).setLevelName(levelName),
                new BlockGeneratorSteam(name, Math.max((long) (energyBasic * Math.pow(4, level) / 2), 1L), level).setLevelName(levelName)
        };
    }

}
