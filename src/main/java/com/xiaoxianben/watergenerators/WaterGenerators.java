package com.xiaoxianben.watergenerators;

import com.xiaoxianben.watergenerators.api.IModInit;
import com.xiaoxianben.watergenerators.config.ConfigLoader;
import com.xiaoxianben.watergenerators.enery.EnergyLiquid;
import com.xiaoxianben.watergenerators.gui.GUIHandler;
import com.xiaoxianben.watergenerators.init.*;
import com.xiaoxianben.watergenerators.otherModsItems.ModOtherModInit;
import com.xiaoxianben.watergenerators.proxy.CommonProxy;
import com.xiaoxianben.watergenerators.tabs.ItemTab;
import com.xiaoxianben.watergenerators.tabs.MachineTab;
import com.xiaoxianben.watergenerators.tabs.MaterialTab;
import com.xiaoxianben.watergenerators.tileEntity.generator.*;
import com.xiaoxianben.watergenerators.util.ModInformation;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nullable;
import java.util.LinkedHashSet;
import java.util.Objects;

@Mod(modid = ModInformation.MOD_ID,
        name = ModInformation.NAME,
        version = ModInformation.VERSION,
        dependencies = "after:jei;after:enderio;after:thermalfoundation")
public class WaterGenerators {


    @Nullable
    public static LinkedHashSet<Item> ITEMS = new LinkedHashSet<>();
    @Nullable
    public static LinkedHashSet<Block> BLOCKS = new LinkedHashSet<>();
    @Mod.Instance
    public static WaterGenerators instance;
    @SidedProxy(clientSide = ModInformation.CLIENT_PROXY_CLASS, serverSide = ModInformation.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;
    /**
     * 创造标签：物品
     */
    public static CreativeTabs Item_TAB = new ItemTab();
    /**
     * 创造标签：机器
     */
    public static CreativeTabs MACHINE_TAB = new MachineTab();
    /**
     * 创造标签：材料
     */
    public static CreativeTabs MATERIAL_TAB = new MaterialTab();
    @Nullable
    public LinkedHashSet<IModInit> modInit = new LinkedHashSet<>();

//    private SimpleNetworkWrapper network;

    public WaterGenerators() {
        FluidRegistry.enableUniversalBucket();
        modInit.add(new ModOre());
        modInit.add(new ModFluid());
        modInit.add(new ModBlocks());
        modInit.add(new ModItems());
        modInit.add(new ModRecipes());
        modInit.add(new ModOtherModInit());
    }

//    public static SimpleNetworkWrapper getNetwork() {
//        return instance.network;
//    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
//        network = NetworkRegistry.INSTANCE.newSimpleChannel(ModInformation.MOD_ID);
//        network.registerMessage(new PacketConsciousness.Handler(), PacketConsciousness.class, 1, Side.CLIENT);
        ConfigLoader.preInitConfigLoader(event);

        new ModOtherModInit();

        Objects.requireNonNull(modInit).forEach(IModInit::preInit);

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        this.registerTileEntity();

        Objects.requireNonNull(modInit).forEach(IModInit::init);

        NetworkRegistry.INSTANCE.registerGuiHandler(WaterGenerators.instance, new GUIHandler());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        EnergyLiquid.init();
        ITEMS = null;
        BLOCKS = null;
        modInit = null;
    }

    public void registerTileEntity() {
        GameRegistry.registerTileEntity(TEGeneratorTurbine.class, new ResourceLocation(ModInformation.MOD_ID, "TETurbineGenerator"));
        GameRegistry.registerTileEntity(TEGeneratorFluid.class, new ResourceLocation(ModInformation.MOD_ID, "TEFluidGenerator"));
        GameRegistry.registerTileEntity(TEGeneratorWater.class, new ResourceLocation(ModInformation.MOD_ID, "TEWaterGenerator"));
        GameRegistry.registerTileEntity(TEGeneratorSteam.class, new ResourceLocation(ModInformation.MOD_ID, "TESteamGenerator"));
        GameRegistry.registerTileEntity(TEGeneratorCreate.class, new ResourceLocation(ModInformation.MOD_ID, "TECreateGenerator"));
    }
}
