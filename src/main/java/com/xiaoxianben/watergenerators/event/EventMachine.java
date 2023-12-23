package com.xiaoxianben.watergenerators.event;

import com.xiaoxianben.watergenerators.util.Reference;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class EventMachine {
    @SubscribeEvent
    public static void onBlockStateUpdate(BlockEvent.PlaceEvent event) {
        /*
        Block block = event.getState().getBlock();

        if (ModBlocks.BLOCKSMACHINE.contains(block)) {
            World world = event.getWorld();
            BlockPos pos = event.getPos();

            ModBlocks.BLOCKSMACHINE.get(ModBlocks.BLOCKSMACHINE.indexOf(block)).updateTick(
                    world, pos, block.getDefaultState(), new Random());
        }
        */
    }
}
