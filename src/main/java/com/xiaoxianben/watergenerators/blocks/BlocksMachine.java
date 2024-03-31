package com.xiaoxianben.watergenerators.blocks;

import com.xiaoxianben.watergenerators.API.IHasInit;
import com.xiaoxianben.watergenerators.init.ModBlocks;
import com.xiaoxianben.watergenerators.init.ModRecipes;
import com.xiaoxianben.watergenerators.items.ItemsMaterial;
import com.xiaoxianben.watergenerators.tileEntity.TEMachineVaporization;
import com.xiaoxianben.watergenerators.util.ModInformation;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Arrays;

import static com.xiaoxianben.watergenerators.JEI.modPlugin.vaporizationList;

public class BlocksMachine implements IHasInit {

    public static BlockMachineVaporization[] blockMachineVaporizations = new BlockMachineVaporization[5];
    public static BlockMachineShell[] machineShells = new BlockMachineShell[5];

    @Override
    public void init() {
        // machine
        for (int i = 0; i < 5; i++) {
            int level = i + 1;
            machineShells[i] = new BlockMachineShell("level" + level, level);
        }
        // va
        for (int i = 0; i < 5; i++) {
            int level = i + 1;
            blockMachineVaporizations[i] = new BlockMachineVaporization("level" + level, level);
        }
    }

    @Override
    public void initRecipes() {
        GameRegistry.registerTileEntity(TEMachineVaporization.class, new ResourceLocation(ModInformation.MOD_ID, "TEMachineVaporization"));

        for (int i = 0; i < machineShells.length; i++) {
            Block block = i == 0 ? ModBlocks.machineShell_frame : machineShells[i - 1];
            ModRecipes.registryShell(machineShells[i], block, BlocksGenerator.oreIngots[i]);
        }

        for (int i = 0; i < blockMachineVaporizations.length; i++) {
            ModRecipes.registryMachineVaporization(
                    blockMachineVaporizations[i],
                    machineShells[i],
                    ItemsMaterial.conduits[i],
                    BlocksGenerator.oreIngots[i]);
        }
        vaporizationList.addAll(Arrays.asList(blockMachineVaporizations));
    }
}
