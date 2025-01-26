package com.xiaoxianben.watergenerators.event.client;

import com.xiaoxianben.watergenerators.WaterGenerators;
import com.xiaoxianben.watergenerators.api.IHasModel;
import com.xiaoxianben.watergenerators.init.EnumModBlock;
import com.xiaoxianben.watergenerators.init.EnumModItems;
import com.xiaoxianben.watergenerators.init.modRegister.MinecraftRegister;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Objects;

@Mod.EventBusSubscriber(
        modid = WaterGenerators.MOD_ID,
        value = Side.CLIENT
)
public class ModelRegister {

    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event) {
        ModelLoader.setCustomStateMapper(MinecraftRegister.steam.getBlock(), new StateMapperBase() {
            @Nonnull
            @Override
            protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
                return new ModelResourceLocation(new ResourceLocation(WaterGenerators.MOD_ID, "fluids"), "steam");
            }
        });

        for (EnumModItems modItems : EnumModItems.values()) {
            modItems.itemMap.values()
                    .forEach(items -> Arrays.stream(items)
                            .filter(Objects::nonNull)
                            .map(ItemStack::getItem)
                            .filter(item -> item instanceof IHasModel)
                            .forEach(item -> ((IHasModel) item).registerModels()));
        }
        for (EnumModBlock modBlock : EnumModBlock.values()) {
            modBlock.blockMap.values()
                    .forEach(blocks -> Arrays.stream(blocks)
                            .filter(block -> block instanceof IHasModel)
                            .forEach(block -> ((IHasModel) block).registerModels()));
        }

    }

}
