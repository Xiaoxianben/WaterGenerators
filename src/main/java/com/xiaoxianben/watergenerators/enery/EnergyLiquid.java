package com.xiaoxianben.watergenerators.enery;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.HashMap;

public class EnergyLiquid {
    public static HashMap<Fluid, Float> liquidEnergy = new HashMap<>();

    public static void init() {
        // 初始化其他液体
        liquidEnergy.put(FluidRegistry.getFluid("water"), 1.0f);
    }

    public static float getEnergyFromLiquid(Fluid fluid) {
        return liquidEnergy.get(fluid);
    }

    public static boolean containsKey(Fluid fluid) {
        return liquidEnergy.containsKey(fluid);
    }

    public static void add(Fluid fluid, float energyFloat) {
        if (fluid != null && energyFloat > 0.0f)
            liquidEnergy.put(fluid, energyFloat);
    }
}
