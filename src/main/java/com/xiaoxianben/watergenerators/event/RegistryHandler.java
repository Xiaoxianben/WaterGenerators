package com.xiaoxianben.watergenerators.event;

import com.xiaoxianben.watergenerators.blocks.BlockBase;
import com.xiaoxianben.watergenerators.init.EnumModBlock;
import com.xiaoxianben.watergenerators.init.EnumModItems;
import com.xiaoxianben.watergenerators.items.ItemBlockPrivate;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;
import java.util.Objects;


@Mod.EventBusSubscriber
public class RegistryHandler {

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        for (EnumModItems modItems : EnumModItems.values()) {
            modItems.itemMap.values()
                    .forEach(items -> Arrays.stream(items)
                            .filter(Objects::nonNull)
                            .map(ItemStack::getItem)
                            .forEach(event.getRegistry()::register));
        }
        for (EnumModBlock modBlock : EnumModBlock.values()) {
            modBlock.blockMap.values()
                    .forEach(blocks -> Arrays.stream(blocks)
                            .filter(block -> block instanceof BlockBase)
                            .forEach(block -> event.getRegistry().register(new ItemBlockPrivate(block))));
        }
    }

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event) {
        for (EnumModBlock modBlock : EnumModBlock.values()) {
            modBlock.blockMap.values()
                    .forEach(blocks -> Arrays.stream(blocks)
                            .filter(Objects::nonNull)
                            .forEach(event.getRegistry()::register));
        }
    }

}
