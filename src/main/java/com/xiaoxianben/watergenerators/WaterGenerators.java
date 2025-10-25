package com.xiaoxianben.watergenerators;

import com.xiaoxianben.watergenerators.config.ConfigLoader;
import com.xiaoxianben.watergenerators.gui.GUIHandler;
import com.xiaoxianben.watergenerators.init.ModOre;
import com.xiaoxianben.watergenerators.init.ModRecipes;
import com.xiaoxianben.watergenerators.init.EnumModRegister;
import com.xiaoxianben.watergenerators.proxy.CommonProxy;
import com.xiaoxianben.watergenerators.tabs.ItemTab;
import com.xiaoxianben.watergenerators.tabs.MachineTab;
import com.xiaoxianben.watergenerators.tabs.MaterialTab;
import com.xiaoxianben.watergenerators.tileEntity.generator.*;
import com.xiaoxianben.watergenerators.tileEntity.generator.fluid.TEGeneratorFluid;
import com.xiaoxianben.watergenerators.tileEntity.generator.fluid.TEGeneratorSteam;
import com.xiaoxianben.watergenerators.tileEntity.generator.fluid.TEGeneratorWater;
import com.xiaoxianben.watergenerators.tileEntity.generator.fluid.TEGeneratorWaterCompressed;
import com.xiaoxianben.watergenerators.tileEntity.machine.TEMachineConcentration;
import com.xiaoxianben.watergenerators.tileEntity.machine.TEMachineVaporization;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Arrays;

@Mod(modid = WaterGenerators.MOD_ID,
        name = WaterGenerators.NAME,
        version = "1.6.1",
        dependencies = "after:jei;after:enderio;after:enderioendergy;after:thermalfoundation;after:thermaldynamics;after:mekanism",
        useMetadata = true)
public class WaterGenerators {


    public static final String MOD_ID = "watergenerators";
    public static final String NAME = "Water Generators";


    @Mod.Instance
    public static WaterGenerators instance;

    @SidedProxy(clientSide = "com.xiaoxianben.watergenerators.proxy.ClientProxy",
            serverSide = "com.xiaoxianben.watergenerators.proxy.CommonProxy")
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


//    private SimpleNetworkWrapper network;

    public WaterGenerators() {
        FluidRegistry.enableUniversalBucket();
        ModRecipes.instance = new ModRecipes();
    }

//    public static SimpleNetworkWrapper getNetwork() {
//        return instance.network;
//    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
//        network = NetworkRegistry.INSTANCE.newSimpleChannel(ModInformation.MOD_ID);
//        network.registerMessage(new PacketConsciousness.Handler(), PacketConsciousness.class, 1, Side.CLIENT);
        ConfigLoader.preInitConfigLoader(event);

        Arrays.stream(EnumModRegister.values()).forEach(EnumModRegister::enable);

        for (EnumModRegister register : EnumModRegister.values()) {
            register.preInit();
        }

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        this.registerTileEntity();


        new ModOre().init();
        for (EnumModRegister register : EnumModRegister.values()) {
            register.init();
        }
        ModRecipes.instance.init();


        NetworkRegistry.INSTANCE.registerGuiHandler(WaterGenerators.instance, new GUIHandler());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        for (EnumModRegister register : EnumModRegister.values()) {
            register.posInit();
        }
        ModRecipes.instance = null;
    }

    public void registerTileEntity() {
        GameRegistry.registerTileEntity(TEGeneratorTurbine.class, new ResourceLocation(MOD_ID, "TETurbineGenerator"));
        GameRegistry.registerTileEntity(TEGeneratorFluid.class, new ResourceLocation(MOD_ID, "TEFluidGenerator"));
        GameRegistry.registerTileEntity(TEGeneratorWater.class, new ResourceLocation(MOD_ID, "TEWaterGenerator"));
        GameRegistry.registerTileEntity(TEGeneratorWaterCompressed.class, new ResourceLocation(MOD_ID, "TEWaterCompressedGenerator"));
        GameRegistry.registerTileEntity(TEGeneratorSteam.class, new ResourceLocation(MOD_ID, "TESteamGenerator"));
        GameRegistry.registerTileEntity(TEGeneratorCreate.class, new ResourceLocation(MOD_ID, "TECreateGenerator"));

        GameRegistry.registerTileEntity(TEMachineVaporization.class, new ResourceLocation(MOD_ID, "TEMachineVaporization"));
        GameRegistry.registerTileEntity(TEMachineConcentration.class, new ResourceLocation(MOD_ID, "TEMachineConcentration"));
    }
}
