package com.xiaoxianben.watergenerators.init.modRegister;

import com.xiaoxianben.watergenerators.blocks.generator.BlockGeneratorBasic;
import com.xiaoxianben.watergenerators.blocks.machine.BlockMachineShell;
import com.xiaoxianben.watergenerators.init.EnumModBlock;
import com.xiaoxianben.watergenerators.init.EnumModItems;
import com.xiaoxianben.watergenerators.init.ModRecipes;
import net.minecraft.block.Block;

public interface IModRegister {
    void preInit();
    void init();
    void posInit();

    EnumModRegister getModRegister();
    String getGearOre(int i);

    default void recipeGenerator(int i1, BlockGeneratorBasic block, Block oldBlock) {
        ModRecipes.instance.addRecipeGenerator(block,
                EnumModItems.CONDUIT.itemMap.get(getModRegister())[i1],
                EnumModItems.TURBINE_ROTOR.itemMap.get(getModRegister())[i1].getItem(),
                (BlockMachineShell) EnumModBlock.MACHINE_SHELL.blockMap.get(getModRegister())[i1],
                oldBlock,
                getGearOre(i1));
    }

}
