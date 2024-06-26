package com.xiaoxianben.watergenerators.event;

import com.xiaoxianben.watergenerators.WaterGenerators;
import com.xiaoxianben.watergenerators.init.ModBlocks;
import com.xiaoxianben.watergenerators.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


@Mod.EventBusSubscriber
public class RegistryHandler {

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        ModItems.addItems();
        event.getRegistry().registerAll(WaterGenerators.ITEMS.toArray(new Item[0]));
    }

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event) {
        ModBlocks.addBlocks();
        event.getRegistry().registerAll(WaterGenerators.BLOCKS.toArray(new Block[0]));
    }

}
