package com.xiaoxianben.watergenerators.liquid;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import java.util.HashMap;

public class EnergyLiquid {
    public static HashMap<Block, Float> liquidEnergy = new HashMap<Block, Float>() {{
        put(Blocks.WATER, 1.0f);
    }};

    public static float getEnergyFromLiquid(Block block) {
        return liquidEnergy.get(block);
    }
    public static boolean containsKey(Block block) {
        return liquidEnergy.containsKey(block);
    }

    public static void add(Block block, float energyFloat) {
        liquidEnergy.put(block, energyFloat);
    }
}
