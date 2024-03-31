package com.xiaoxianben.watergenerators.event;

import com.xiaoxianben.watergenerators.init.ModBlocks;
import com.xiaoxianben.watergenerators.util.ModInformation;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

@Mod.EventBusSubscriber(modid = ModInformation.MOD_ID)
public class EventMachine {
    @SubscribeEvent
    public static void onBlockStateUpdate(BlockEvent.PlaceEvent event) {
        Block block = event.getState().getBlock();

        if(block == Blocks.GRASS) {

            // 生成一个随机数
            Random random = new Random();
            int chance = random.nextInt(3);

            // 如果随机数小于等于50，则生成一个铁块
            if(chance == 0) {
                event.getWorld().setBlockState(event.getPos(), ModBlocks.GOLD_PLATED_IRON_BLOCK.getDefaultState());
            }
        }
    }
}
