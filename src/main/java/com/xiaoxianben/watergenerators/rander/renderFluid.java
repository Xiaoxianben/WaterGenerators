package com.xiaoxianben.watergenerators.rander;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class renderFluid {

    public static TextureMap fluidTextures = null;

    @SubscribeEvent
    public static void onStitch(TextureStitchEvent.Pre event) {
        fluidTextures = event.getMap();
    }
}
