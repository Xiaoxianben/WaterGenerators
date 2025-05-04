package com.xiaoxianben.watergenerators.init;

import com.xiaoxianben.watergenerators.blocks.generator.*;
import com.xiaoxianben.watergenerators.blocks.machine.BlockMachineConcentration;
import com.xiaoxianben.watergenerators.blocks.machine.BlockMachineShell;
import com.xiaoxianben.watergenerators.blocks.machine.BlockMachineVaporization;
import com.xiaoxianben.watergenerators.init.modRegister.EnumModRegister;
import net.minecraft.block.Block;

import java.util.EnumMap;

public enum EnumModBlock {
    OTHER(null),
    MACHINE_SHELL(BlockMachineShell::new),
    MACHINE_VAPORIZATION(BlockMachineVaporization::new),
    MACHINE_CONCENTRATION(BlockMachineConcentration::new),
    GENERATOR_turbine(BlockGeneratorTurbine::new),
    GENERATOR_fluid(BlockGeneratorFluid::new),
    GENERATOR_water(BlockGeneratorWater::new),
    GENERATOR_waterCompressed(BlockGeneratorWaterCompressed::new),
    GENERATOR_steam(BlockGeneratorSteam::new);


    public final EnumMap<EnumModRegister, Block[]> blockMap = new EnumMap<>(EnumModRegister.class);
    private final MachineCreat<? extends Block> func;

    EnumModBlock(MachineCreat<? extends Block> func) {
        this.func = func;
    }

    public Block[] getBlocks(EnumModRegister register) {
        return blockMap.get(register);
    }

    public void addBlocks(EnumModRegister register, Block[] blocks) {
        blockMap.put(register, blocks);
    }

    public Block creat(float level, String levelName) {
        return func.creat(level, levelName);
    }


    interface MachineCreat<T> {
        T creat(float level, String levelName);
    }

}
