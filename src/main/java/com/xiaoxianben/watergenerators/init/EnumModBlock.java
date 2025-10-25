package com.xiaoxianben.watergenerators.init;

import com.xiaoxianben.watergenerators.blocks.generator.*;
import com.xiaoxianben.watergenerators.blocks.machine.BlockMachineConcentration;
import com.xiaoxianben.watergenerators.blocks.machine.BlockMachineShell;
import com.xiaoxianben.watergenerators.blocks.machine.BlockMachineVaporization;
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
    private final MachineCreate<? extends Block> func;
    public final EnumModRegister modRegister;
    public final boolean isDefaultBlock;

    EnumModBlock(MachineCreate<? extends Block> func) {
        this(EnumModRegister.MINECRAFT, true, func);
    }
    EnumModBlock(EnumModRegister modRegister, boolean isDefaultBlock, MachineCreate<? extends Block> func) {
        this.func = func;
        this.modRegister = modRegister;
        this.isDefaultBlock = isDefaultBlock;
    }

    public Block[] getBlocks(EnumModRegister register) {
        return blockMap.get(register);
    }

    public void addBlocks(EnumModRegister register, Block[] blocks) {
        blockMap.put(register, blocks);
    }

    public Block create(float level, String levelName) {
        return func.creat(level, levelName);
    }


    interface MachineCreate<T> {
        T creat(float level, String levelName);
    }

}
