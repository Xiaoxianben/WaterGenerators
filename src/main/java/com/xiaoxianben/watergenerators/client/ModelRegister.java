package com.xiaoxianben.watergenerators.client;

import com.xiaoxianben.watergenerators.WaterGenerators;
import com.xiaoxianben.watergenerators.api.IHasModel;
import com.xiaoxianben.watergenerators.fluid.Fluids;
import com.xiaoxianben.watergenerators.util.ModInformation;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public class ModelRegister {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event) {
        ModelLoader.setCustomStateMapper(Fluids.steam.getBlock(), new StateMapperBase() {
            @Nonnull
            @Override
            protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
                return new ModelResourceLocation(new ResourceLocation(ModInformation.MOD_ID, "fluid"), "steam");
            }
        });
        for (Item item : WaterGenerators.ITEMS) {
            if (item instanceof IHasModel) {
                ((IHasModel) item).registerModels();
            }
        }

        for (Block block : WaterGenerators.BLOCKS) {
            if (block instanceof IHasModel) {
                ((IHasModel) block).registerModels();
            }
        }

    }

}
