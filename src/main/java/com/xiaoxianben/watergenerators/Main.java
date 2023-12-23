package com.xiaoxianben.watergenerators;

import com.xiaoxianben.watergenerators.GUI.GUIHandler;
import com.xiaoxianben.watergenerators.event.PacketConsciousness;
import com.xiaoxianben.watergenerators.init.ModBlocks;
import com.xiaoxianben.watergenerators.init.ModItems;
import com.xiaoxianben.watergenerators.init.ModOre;
import com.xiaoxianben.watergenerators.init.ModRecipes;
import com.xiaoxianben.watergenerators.proxy.CommonProxy;
import com.xiaoxianben.watergenerators.tabs.IngotBlocksTab;
import com.xiaoxianben.watergenerators.tabs.MachineTab;
import com.xiaoxianben.watergenerators.tabs.MaterialTab;
import com.xiaoxianben.watergenerators.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.List;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION)
public class Main {

    public static final List<Item> ITEMS = new ArrayList<>();
    public static final List<Block> BLOCKS = new ArrayList<>();

    private SimpleNetworkWrapper network;

    @Mod.Instance
    public static Main instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        network = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);
        network.registerMessage(new PacketConsciousness.Handler(), PacketConsciousness.class, 1, Side.CLIENT);
        ModItems.preInit();
        ModBlocks.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
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
