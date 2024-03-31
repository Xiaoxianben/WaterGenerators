package com.xiaoxianben.watergenerators;

import com.xiaoxianben.watergenerators.GUI.GUIHandler;
import com.xiaoxianben.watergenerators.enery.EnergyLiquid;
import com.xiaoxianben.watergenerators.event.ConfigLoader;
import com.xiaoxianben.watergenerators.event.PacketConsciousness;
import com.xiaoxianben.watergenerators.init.*;
import com.xiaoxianben.watergenerators.otherModsItems.otherInit;
import com.xiaoxianben.watergenerators.proxy.CommonProxy;
import com.xiaoxianben.watergenerators.tabs.IngotBlocksTab;
import com.xiaoxianben.watergenerators.tabs.MachineTab;
import com.xiaoxianben.watergenerators.tabs.MaterialTab;
import com.xiaoxianben.watergenerators.util.ModInformation;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.List;

@Mod(modid = ModInformation.MOD_ID, name = ModInformation.NAME, version = ModInformation.VERSION,
        dependencies = "after:jei;after:enderio;after:thermalfoundation")
public class Main {

    public static List<Item> ITEMS = new ArrayList<>();
    public static List<Block> BLOCKS = new ArrayList<>();

    private SimpleNetworkWrapper network;

    @Mod.Instance
    public static Main instance;

    @SidedProxy(clientSide = ModInformation.CLIENT_PROXY_CLASS, serverSide = ModInformation.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;

    public Main() {
        FluidRegistry.enableUniversalBucket();
    }
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        network = NetworkRegistry.INSTANCE.newSimpleChannel(ModInformation.MOD_ID);
        network.registerMessage(new PacketConsciousness.Handler(), PacketConsciousness.class, 1, Side.CLIENT);
        ConfigLoader.preInitConfigLoader(event);

        otherInit.preInit();

        ModFluid.init();

        ModBlocks.preInit();
        ModItems.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        EnergyLiquid.init();

        ModOre.preInit();

        ModItems.init();
        ModBlocks.init();
        ModRecipes.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, new GUIHandler());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }

    public static SimpleNetworkWrapper getNetwork() {
        return instance.network;
    }

    public static CreativeTabs INGOT_BLOCK_TAB = new IngotBlocksTab();
    public static CreativeTabs MACHINE_TAB = new MachineTab();
    public static CreativeTabs MATERIAL_TAB = new MaterialTab();

}
