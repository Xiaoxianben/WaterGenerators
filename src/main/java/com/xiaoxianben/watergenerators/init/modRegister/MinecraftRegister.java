package com.xiaoxianben.watergenerators.init.modRegister;

import com.xiaoxianben.watergenerators.WaterGenerators;
import com.xiaoxianben.watergenerators.blocks.BlockBase;
import com.xiaoxianben.watergenerators.blocks.generator.BlockGeneratorBasic;
import com.xiaoxianben.watergenerators.blocks.generator.BlockGeneratorCreate;
import com.xiaoxianben.watergenerators.blocks.machine.BlockMachineBase;
import com.xiaoxianben.watergenerators.blocks.machine.BlockMachineShell;
import com.xiaoxianben.watergenerators.init.EnumModBlock;
import com.xiaoxianben.watergenerators.init.EnumModItems;
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
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MinecraftRegister implements IModRegister {
    public static Block GOLD_PLATED_IRON_BLOCK;
    public static Block machineShell_frame;
    public static BlockGeneratorCreate generatorCreate;
    public static Fluid steam;
    public static ItemStack steamBucket;

    public static Item GOLD_PLATED_IRON_INGOT;
    public static Item ductShell_bank;
    public static Item ductShell;
    public static Item information_finder;

    public static ItemComponent component_null;
    public static ItemComponent component_extract;
    public static ItemComponent component_powerGeneration;

    private final int[] levels = new int[]{1, 2, 3, 4, 5};
    private final String[] levelIngotOres = new String[]{"ingotIron", "ingotGoldPlatedIron", "gemDiamond", "obsidian", "gemEmerald"};
    private final String[] gearOres = new String[]{"gearIron", "gearGoldPlatedIron", "gearDiamond", "gearObsidian", "gearEmerald"};
    private final EnumModRegister selfRegister = EnumModRegister.MINECRAFT;
    public BlockFluidClassic blockSteam;

    @Override
    public void preInit() {
        // fluid
        steam = new Fluid("steam", new ResourceLocation("watergenerators:fluids/steam_still"), new ResourceLocation("watergenerators:fluids/steam_flow"));
        steam.setTemperature(1000).setGaseous(true).setLuminosity(0).setDensity(-10);
        FluidRegistry.addBucketForFluid(steam);

        // block
        GOLD_PLATED_IRON_BLOCK = new BlockBase("block_goldPlatedIron", Material.IRON, WaterGenerators.MATERIAL_TAB, null);
        machineShell_frame = new BlockBase("machineshell_frame", Material.IRON, WaterGenerators.MATERIAL_TAB, null) {
            public boolean canPlaceBlockAt(@Nonnull World worldIn, @Nonnull BlockPos pos) {
                return false;
            }
        };
        generatorCreate = new BlockGeneratorCreate();

        MaterialLiquid MaterialSteam = (new MaterialLiquid(MapColor.SNOW));
        blockSteam = (BlockFluidClassic) new BlockFluidClassic(steam, MaterialSteam).setRegistryName(WaterGenerators.MOD_ID, "steam").setUnlocalizedName(WaterGenerators.MOD_ID + ".steam");

        EnumModBlock.OTHER.addBlocks(selfRegister, new Block[]{GOLD_PLATED_IRON_BLOCK, machineShell_frame, generatorCreate, blockSteam});

        for (int i = 1; i < EnumModBlock.values().length; i++) {
            EnumModBlock modBlock = EnumModBlock.values()[i];
            Block[] blocks = new Block[levels.length];

            for (int i2 = 0; i2 < levels.length; i2++) {
                int i3 = levels[i2];
                blocks[i2] = modBlock.creat(i3, "level" + i3);
            }

            modBlock.addBlocks(selfRegister, blocks);
        }

        // item
        GOLD_PLATED_IRON_INGOT = new ItemBase("ingot_goldPlatedIron", WaterGenerators.MATERIAL_TAB);
        ductShell_bank = new ItemBase("ductShell_1", WaterGenerators.MATERIAL_TAB);
        ductShell = new ItemBase("ductShell_0", WaterGenerators.MATERIAL_TAB);
        information_finder = new ItemBase("information_finder") {
            @ParametersAreNonnullByDefault
            @SideOnly(Side.CLIENT)
            public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
                tooltip.add("用于查看机器的NBT");
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


        EnumModItems.OTHER.addItems(selfRegister,
                new Item[]{GOLD_PLATED_IRON_INGOT, ductShell_bank, ductShell, information_finder,
                        component_null, component_extract, component_powerGeneration});

        for (int i = 1; i < EnumModItems.values().length; i++) {
            EnumModItems modItems = EnumModItems.values()[i];
            Item[] items = new Item[levels.length];

            for (int i2 = 0; i2 < levels.length; i2++) {
                int i3 = levels[i2];
                items[i2] = modItems.creat("level" + i3);
            }

            modItems.addItems(selfRegister, items);
        }

    }

    @Override
    public void init() {
        steamBucket = this.getBucket(steam);
        ModRecipes.instance.inputItemStack = new ItemStack[]{
                EnumModItems.COIL.itemMap.get(EnumModRegister.MINECRAFT)[0],
                Items.BUCKET.getDefaultInstance()
        };
        ModRecipes.instance.mapFluidMaterial.put("water", Items.WATER_BUCKET.getDefaultInstance());
        ModRecipes.instance.mapFluidMaterial.put("steam", MinecraftRegister.steamBucket);

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

        final int[] i1 = {0};
        Arrays.stream(EnumModBlock.MACHINE_SHELL.blockMap.get(selfRegister))
                .filter(Objects::nonNull)
                .forEach(block -> {
                    int i = i1[0];
                    ModRecipes.instance.addRecipeShell(block,
                            i == 0 ? machineShell_frame : EnumModBlock.MACHINE_SHELL.blockMap.get(selfRegister)[i - 1],
                            levelIngotOres[i]);
                    ++i1[0];
                });
        i1[0] = 0;
        Arrays.stream(EnumModBlock.MACHINE_VAPORIZATION.blockMap.get(selfRegister))
                .filter(Objects::nonNull)
                .forEach(block -> {
                    int i = i1[0];
                    ModRecipes.instance.addRecipeMachineVaporization((BlockMachineBase) block,
                            (BlockMachineShell) EnumModBlock.MACHINE_SHELL.blockMap.get(selfRegister)[i],
                            EnumModItems.CONDUIT.itemMap.get(selfRegister)[i],
                            gearOres[i]);
                    ++i1[0];
                });
        i1[0] = 0;
        Arrays.stream(EnumModBlock.GENERATOR_turbine.blockMap.get(selfRegister))
                .filter(Objects::nonNull)
                .forEach(block -> {
                    int i = i1[0];
                    recipeGenerator(i,
                            (BlockGeneratorBasic) block,
                            i == 0 ? null : EnumModBlock.GENERATOR_turbine.getBlocks(selfRegister)[i - 1]);
                    ++i1[0];
                });
        i1[0] = 0;
        Arrays.stream(EnumModBlock.GENERATOR_fluid.blockMap.get(selfRegister))
                .filter(Objects::nonNull)
                .forEach(block -> {
                    int i = i1[0];
                    recipeGenerator(i,
                            (BlockGeneratorBasic) block,
                            i == 0 ? null : EnumModBlock.GENERATOR_fluid.getBlocks(selfRegister)[i - 1]);
                    ++i1[0];
                });
        i1[0] = 0;
        Arrays.stream(EnumModBlock.GENERATOR_water.blockMap.get(selfRegister))
                .filter(Objects::nonNull)
                .forEach(block -> {
                    int i = i1[0];
                    recipeGenerator(i,
                            (BlockGeneratorBasic) block,
                            i == 0 ? null : EnumModBlock.GENERATOR_water.getBlocks(selfRegister)[i - 1]);
                    ++i1[0];
                });
        i1[0] = 0;
        Arrays.stream(EnumModBlock.GENERATOR_steam.blockMap.get(selfRegister))
                .filter(Objects::nonNull)
                .forEach(block -> {
                    int i = i1[0];
                    recipeGenerator(i,
                            (BlockGeneratorBasic) block,
                            i == 0 ? null : EnumModBlock.GENERATOR_steam.getBlocks(selfRegister)[i - 1]);
                    ++i1[0];
                });

        // item
        GameRegistry.addSmelting(ductShell_bank, new ItemStack(ductShell, 8), 0.5f);

        ModRecipes.instance.addRecipeComponent(component_null, Items.AIR, null);
        ModRecipes.instance.addRecipeComponent(component_extract, component_null, Items.BUCKET);
        ModRecipes.instance.addRecipeComponent(component_powerGeneration, component_null, EnumModItems.TURBINE_ROTOR.itemMap.get(selfRegister)[4].getItem());

        int i = 0;
        for (ItemStack itemStack : EnumModItems.GEAR.itemMap.get(selfRegister)) {
            ModRecipes.instance.addRecipeGear(itemStack.getItem(), levelIngotOres[i]);
            ++i;
        }
        i = 0;
        for (ItemStack itemStack : EnumModItems.TURBINE_ROTOR.itemMap.get(selfRegister)) {
            ModRecipes.instance.addRecipeTurbineRotor(itemStack.getItem(), levelIngotOres[i], gearOres[i]);
            ++i;
        }
        i = 0;
        for (ItemStack itemStack : EnumModItems.COIL.itemMap.get(selfRegister)) {
            ModRecipes.instance.addRecipeCoil(itemStack.getItem(), i == 0 ? null : EnumModItems.COIL.itemMap.get(selfRegister)[i - 1].getItem(), levelIngotOres[i]);
            ++i;
        }
        i = 0;
        for (ItemStack itemStack : EnumModItems.CONDUIT.itemMap.get(selfRegister)) {
            Item coil = EnumModItems.COIL.itemMap.get(selfRegister)[i].getItem();
            if (i == 0) {
                ModRecipes.instance.addRecipeConduit(itemStack.getItem(), coil, coil);
            } else {
                ModRecipes.instance.addRecipeConduit(itemStack.getItem(),
                        coil, EnumModItems.CONDUIT.itemMap.get(selfRegister)[i - 1].getItem());
            }
            ++i;
        }
    }


    @Override
    public void posInit() {

    }

    private ItemStack getBucket(Fluid fluid) {
        UniversalBucket bucket = ForgeModContainer.getInstance().universalBucket;
        ItemStack itemBucket = new ItemStack(bucket);

        FluidStack fluidContents = new FluidStack(FluidRegistry.getFluid(fluid.getName()), bucket.getCapacity());

        itemBucket.setTagCompound(fluidContents.writeToNBT(new NBTTagCompound()));
        return itemBucket;
    }

    @Override
    public EnumModRegister getModRegister() {
        return selfRegister;
    }

    @Override
    public String getGearOre(int i) {
        return gearOres[i];
    }

}
