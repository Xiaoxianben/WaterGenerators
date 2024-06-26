package com.xiaoxianben.watergenerators.blocks;

import com.xiaoxianben.watergenerators.api.IHasInit;
import com.xiaoxianben.watergenerators.blocks.machine.BlockMachineShell;
import com.xiaoxianben.watergenerators.blocks.machine.BlockMachineVaporization;
import com.xiaoxianben.watergenerators.init.ModBlocks;
import com.xiaoxianben.watergenerators.init.ModRecipes;
import com.xiaoxianben.watergenerators.items.ItemsMaterial;
import com.xiaoxianben.watergenerators.tileEntity.machine.TEMachineVaporization;
import com.xiaoxianben.watergenerators.util.ModInformation;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlocksMachine implements IHasInit {


    public static BlockMachineVaporization[] machineVaporizations = new BlockMachineVaporization[5];
    public static BlockMachineShell[] machineShells = new BlockMachineShell[5];

    public String[] oreIngots = {"ingotIron", "ingotGoldPlatedIron", "gemDiamond", "obsidian", "gemEmerald"};

    @Override
    public void init() {
        // machine
        for (int i = 0; i < 5; i++) {
            int level = i + 1;
            machineShells[i] = new BlockMachineShell(level, "level" + level);
            machineVaporizations[i] = new BlockMachineVaporization(level, "level" + level);
        }
    }

    @Override
    public void initRecipes() {
        GameRegistry.registerTileEntity(TEMachineVaporization.class, new ResourceLocation(ModInformation.MOD_ID, "TEMachineVaporization"));

        for (int i = 0; i < machineShells.length; i++) {
            Block block = i == 0 ? ModBlocks.machineShell_frame : machineShells[i - 1];
            ModRecipes.addRecipeShell(machineShells[i], block, oreIngots[i]);

            ModRecipes.addRecipeMachineVaporization(
                    machineVaporizations[i],
                    machineShells[i],
                    ItemsMaterial.conduits[i],
                    oreIngots[i]);
        }
    }
}
