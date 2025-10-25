package com.xiaoxianben.watergenerators.init.modRegister;

import com.xiaoxianben.watergenerators.WaterGenerators;
import com.xiaoxianben.watergenerators.api.IModRegister;
import com.xiaoxianben.watergenerators.blocks.BlockBase;
import com.xiaoxianben.watergenerators.blocks.generator.BlockGeneratorBasic;
import com.xiaoxianben.watergenerators.blocks.generator.BlockGeneratorCreate;
import com.xiaoxianben.watergenerators.blocks.machine.BlockMachineBase;
import com.xiaoxianben.watergenerators.blocks.machine.BlockMachineShell;
import com.xiaoxianben.watergenerators.init.EnumModBlock;
import com.xiaoxianben.watergenerators.init.EnumModItems;
import com.xiaoxianben.watergenerators.init.EnumModRegister;
import com.xiaoxianben.watergenerators.init.ModRecipes;
import com.xiaoxianben.watergenerators.items.ItemBase;
import com.xiaoxianben.watergenerators.items.component.ItemComponent;
import com.xiaoxianben.watergenerators.tileEntity.TEEnergyBasic;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class MinecraftRegister implements IModRegister {
    public static Block GOLD_PLATED_IRON_BLOCK;
    public static Block machineShell_frame;
    public static BlockGeneratorCreate generatorCreate;
    public static Fluid steam;
    public static Fluid waterCompressed;

    public static Item GOLD_PLATED_IRON_INGOT;
    public static Item ductShell_bank;
    public static Item ductShell;
    public static Item information_finder;

    public static ItemComponent component_null;
    public static ItemComponent component_extract;
    public static ItemComponent component_powerGeneration;
    public static ItemComponent component_efficiency;

    private final float[] levels = new float[]{1, 2, 3, 4, 5};
    private final String[] levelIngotOres = new String[]{"ingotIron", "ingotGoldPlatedIron", "gemDiamond", "obsidian", "gemEmerald"};
    private final String[] gearOres = new String[]{"gearIron", "gearGoldPlatedIron", "gearDiamond", "gearObsidian", "gearEmerald"};
    private final EnumModRegister selfRegister = EnumModRegister.MINECRAFT;


    @Override
    public void preInit() {
        // fluid
        steam = new Fluid("steam", new ResourceLocation("watergenerators:fluids/steam_still"), new ResourceLocation("watergenerators:fluids/steam_flow"));
        steam.setTemperature(1000).setGaseous(true).setLuminosity(15).setDensity(-10);
        FluidRegistry.addBucketForFluid(steam);

        waterCompressed = new Fluid("watercompressed", new ResourceLocation("watergenerators:fluids/watercompressed_still"), new ResourceLocation("watergenerators:fluids/watercompressed_flow"));
        waterCompressed.setDensity(3000).setViscosity(6000);
        FluidRegistry.addBucketForFluid(waterCompressed);

        MaterialLiquid MaterialSteam = (new MaterialLiquid(MapColor.SNOW));
        BlockFluidClassic blockSteam = (BlockFluidClassic) new BlockFluidClassic(steam, MaterialSteam).setRegistryName(WaterGenerators.MOD_ID, "steam").setUnlocalizedName(WaterGenerators.MOD_ID + ".steam");
        BlockFluidClassic blockWaterCompressed = (BlockFluidClassic) new BlockFluidClassic(waterCompressed, Material.WATER).setRegistryName(WaterGenerators.MOD_ID, "watercompressed").setUnlocalizedName(WaterGenerators.MOD_ID + ".watercompressed");

        // block
        GOLD_PLATED_IRON_BLOCK = new BlockBase("block_goldPlatedIron", Material.IRON, WaterGenerators.MATERIAL_TAB, null);
        machineShell_frame = new MachineShellFrame();
        generatorCreate = new BlockGeneratorCreate();


        EnumModBlock.OTHER.addBlocks(selfRegister, new Block[]{GOLD_PLATED_IRON_BLOCK, machineShell_frame, generatorCreate, blockSteam, blockWaterCompressed});

        registerDefaultBlock();

        // item
        GOLD_PLATED_IRON_INGOT = new ItemBase("ingot_goldPlatedIron", WaterGenerators.MATERIAL_TAB);
        ductShell_bank = new ItemBase("ductShell_1", WaterGenerators.MATERIAL_TAB);
        ductShell = new ItemBase("ductShell_0", WaterGenerators.MATERIAL_TAB);
        information_finder = new ItemBase("information_finder") {
            @ParametersAreNonnullByDefault
            @SideOnly(Side.CLIENT)
            public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
                tooltip.add("用于查看机器的NBT，仅限创造模式");
            }

            @ParametersAreNonnullByDefault
            @Nonnull
            public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
                TileEntity TEBlock = world.getTileEntity(pos);

                if (!world.isRemote && TEBlock instanceof TEEnergyBasic) {
                    player.sendMessage(new TextComponentString(String.valueOf(TEBlock.writeToNBT(new NBTTagCompound()))));
                }

                return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
            }
        }.setMaxStackSize(1);

        component_null = new ItemComponent("null");
        component_extract = new ItemComponent("fluidExtract");
        component_powerGeneration = new ItemComponent("powerGeneration");
        component_efficiency = new ItemComponent("efficiency");


        EnumModItems.OTHER.addItems(selfRegister,
                new Item[]{GOLD_PLATED_IRON_INGOT, ductShell_bank, ductShell, information_finder,
                        component_null, component_extract, component_powerGeneration, component_efficiency});

        registerDefaultItem();

    }

    @Override
    public void init() {
        ItemStack steamBucket = FluidUtil.getFilledBucket(new FluidStack(steam, Fluid.BUCKET_VOLUME));
        ItemStack waterCompressedBucket = FluidUtil.getFilledBucket(new FluidStack(waterCompressed, Fluid.BUCKET_VOLUME));

        ModRecipes.instance.inputItemStack = new ItemStack[]{
                EnumModItems.COIL.itemMap.get(EnumModRegister.MINECRAFT)[0],
                Items.BUCKET.getDefaultInstance()
        };
        ModRecipes.instance.mapFluidMaterial.put("water", Items.WATER_BUCKET.getDefaultInstance());
        ModRecipes.instance.mapFluidMaterial.put("steam", steamBucket);
        ModRecipes.instance.mapFluidMaterial.put("watercompressed", waterCompressedBucket);

        // ore
        for (int i = 0; i < EnumModItems.GEAR.itemMap.get(selfRegister).length; i++) {
            OreDictionary.registerOre(gearOres[i], EnumModItems.GEAR.itemMap.get(selfRegister)[i]);
        }

        // block
        ModRecipes.instance.addRecipeBlock(Item.getItemFromBlock(GOLD_PLATED_IRON_BLOCK), GOLD_PLATED_IRON_INGOT);
        ModRecipes.instance.addRecipeShell(machineShell_frame, null, null);
        GameRegistry.addShapedRecipe(MinecraftRegister.GOLD_PLATED_IRON_INGOT.getRegistryName(),
                new ResourceLocation(WaterGenerators.MOD_ID, "ingot"),
                new ItemStack(MinecraftRegister.GOLD_PLATED_IRON_INGOT, 8),
                "III",
                "IGI",
                "III",
                'I', "ingotIron",
                'G', "ingotGold"
        );
        GameRegistry.addShapedRecipe(Item.getItemFromBlock(MinecraftRegister.GOLD_PLATED_IRON_BLOCK).getRegistryName(),
                new ResourceLocation(WaterGenerators.MOD_ID, "block"),
                new ItemStack(Item.getItemFromBlock(MinecraftRegister.GOLD_PLATED_IRON_BLOCK), 8),
                "III",
                "IGI",
                "III",
                'I', "blockIron",
                'G', "blockGold"
        );

        recipeBlock(EnumModBlock.MACHINE_SHELL, recipeBlockInput -> {
            final Block block = recipeBlockInput.block;
            final int i = recipeBlockInput.i;
            ModRecipes.instance.addRecipeShell(block,
                    i == 0 ? machineShell_frame : EnumModBlock.MACHINE_SHELL.blockMap.get(selfRegister)[i - 1],
                    levelIngotOres[i]);
        });
        recipeBlock(EnumModBlock.MACHINE_VAPORIZATION, recipeBlockInput -> {
            final Block block = recipeBlockInput.block;
            final int i = recipeBlockInput.i;
            ModRecipes.instance.addRecipeMachineVaporization((BlockMachineBase) block,
                    (BlockMachineShell) EnumModBlock.MACHINE_SHELL.blockMap.get(selfRegister)[i],
                    EnumModItems.CONDUIT.itemMap.get(selfRegister)[i],
                    gearOres[i]);
        });
        recipeBlock(EnumModBlock.MACHINE_CONCENTRATION, recipeBlockInput -> {
            final Block block = recipeBlockInput.block;
            final int i = recipeBlockInput.i;
            ModRecipes.instance.addRecipeMachineConcentration((BlockMachineBase) block,
                    (BlockMachineShell) EnumModBlock.MACHINE_SHELL.blockMap.get(selfRegister)[i],
                    EnumModItems.CONDUIT.itemMap.get(selfRegister)[i],
                    (BlockMachineBase) EnumModBlock.MACHINE_VAPORIZATION.getBlocks(selfRegister)[i]);
        });
        recipeBlock(EnumModBlock.GENERATOR_turbine, recipeBlockInput -> {
            final Block block = recipeBlockInput.block;
            final int i = recipeBlockInput.i;
            recipeGenerator(i,
                    (BlockGeneratorBasic) block,
                    i == 0 ? null : EnumModBlock.GENERATOR_turbine.getBlocks(selfRegister)[i - 1]);
        });
        recipeBlock(EnumModBlock.GENERATOR_fluid, recipeBlockInput -> {
            final Block block = recipeBlockInput.block;
            final int i = recipeBlockInput.i;
            recipeGenerator(i,
                    (BlockGeneratorBasic) block,
                    i == 0 ? null : EnumModBlock.GENERATOR_fluid.getBlocks(selfRegister)[i - 1]);
        });
        recipeBlock(EnumModBlock.GENERATOR_water, recipeBlockInput -> {
            final Block block = recipeBlockInput.block;
            final int i = recipeBlockInput.i;
            recipeGenerator(i,
                    (BlockGeneratorBasic) block,
                    i == 0 ? null : EnumModBlock.GENERATOR_water.getBlocks(selfRegister)[i - 1]);
        });
        recipeBlock(EnumModBlock.GENERATOR_steam, recipeBlockInput -> {
            final Block block = recipeBlockInput.block;
            final int i = recipeBlockInput.i;
            recipeGenerator(i,
                    (BlockGeneratorBasic) block,
                    i == 0 ? null : EnumModBlock.GENERATOR_steam.getBlocks(selfRegister)[i - 1]);
        });
        recipeBlock(EnumModBlock.GENERATOR_waterCompressed, recipeBlockInput -> {
            final Block block = recipeBlockInput.block;
            final int i = recipeBlockInput.i;
            recipeGenerator(i,
                    (BlockGeneratorBasic) block,
                    i == 0 ? null : EnumModBlock.GENERATOR_waterCompressed.getBlocks(selfRegister)[i - 1]);
        });


        // item
        GameRegistry.addSmelting(ductShell_bank, new ItemStack(ductShell, 8), 0.5f);

        ModRecipes.instance.addRecipeComponent(component_null, Items.AIR, null);
        ModRecipes.instance.addRecipeComponent(component_extract, component_null, Items.BUCKET);
        ModRecipes.instance.addRecipeComponent(component_powerGeneration, component_null, EnumModItems.TURBINE_ROTOR.itemMap.get(selfRegister)[4].getItem());

        recipeItem(EnumModItems.GEAR, recipeItemInput -> {
            ItemStack itemStack = recipeItemInput.item;
            int i = recipeItemInput.i;
            ModRecipes.instance.addRecipeGear(itemStack.getItem(), levelIngotOres[i]);
        });
        recipeItem(EnumModItems.TURBINE_ROTOR, recipeItemInput -> {
            ItemStack itemStack = recipeItemInput.item;
            int i = recipeItemInput.i;
            ModRecipes.instance.addRecipeTurbineRotor(itemStack.getItem(), levelIngotOres[i], gearOres[i]);
        });
        recipeItem(EnumModItems.COIL, recipeItemInput -> {
            ItemStack itemStack = recipeItemInput.item;
            int i = recipeItemInput.i;
            ModRecipes.instance.addRecipeCoil(itemStack.getItem(), i == 0 ? null : EnumModItems.COIL.itemMap.get(selfRegister)[i - 1].getItem(), levelIngotOres[i]);
        });
        recipeItem(EnumModItems.CONDUIT, recipeItemInput -> {
            ItemStack itemStack = recipeItemInput.item;
            int i = recipeItemInput.i;
            Item coil = EnumModItems.COIL.itemMap.get(selfRegister)[i].getItem();

            if (i == 0) {
                ModRecipes.instance.addRecipeConduit(itemStack.getItem(), coil, coil);
            } else {
                ModRecipes.instance.addRecipeConduit(itemStack.getItem(),
                        coil, EnumModItems.CONDUIT.itemMap.get(selfRegister)[i - 1].getItem());
            }
        });

    }

    @Override
    public void posInit() {

    }

    @Override
    public EnumModRegister getModRegister() {
        return selfRegister;
    }

    @Override
    public float[] getLevels() {
        return levels;
    }

    @Override
    public String[] getLevelsString() {
        return new String[]{"level1", "level2", "level3", "level4", "level5"};
    }

    @Override
    public String getGearOre(int i) {
        return gearOres[i];
    }

    @Override
    public void registerDefaultItem() {
        for (int i = 1; i < EnumModItems.values().length; i++) {
            EnumModItems modItems = EnumModItems.values()[i];

            String[] levelsString = getLevelsString();
            Item[] items = new Item[levelsString.length];

            for (int i2 = 0; i2 < levelsString.length; i2++) {
                items[i2] = modItems.create(levelsString[i2]);
            }

            modItems.addItems(getModRegister(), items);
        }
    }

    private static class MachineShellFrame extends BlockBase {
        public MachineShellFrame() {
            super("machineshell_frame", Material.IRON, WaterGenerators.MATERIAL_TAB, null);
        }

        public boolean canPlaceBlockAt(@Nonnull World worldIn, @Nonnull BlockPos pos) {
            return false;
        }
    }
}
