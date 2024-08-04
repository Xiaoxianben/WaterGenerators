package com.xiaoxianben.watergenerators.init;

import com.xiaoxianben.watergenerators.api.IModInit;
import com.xiaoxianben.watergenerators.blocks.BlocksGenerator;
import com.xiaoxianben.watergenerators.blocks.BlocksMachine;
import com.xiaoxianben.watergenerators.blocks.generator.BlockGeneratorBasic;
import com.xiaoxianben.watergenerators.blocks.machine.BlockMachineBase;
import com.xiaoxianben.watergenerators.blocks.machine.BlockMachineShell;
import com.xiaoxianben.watergenerators.items.ItemsMaterial;
import com.xiaoxianben.watergenerators.items.component.ItemComponent;
import com.xiaoxianben.watergenerators.recipe.RecipeList;
import com.xiaoxianben.watergenerators.recipe.crafting.ShapedRecipes;
import com.xiaoxianben.watergenerators.recipe.crafting.ShapelessRecipes;
import com.xiaoxianben.watergenerators.util.ModInformation;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.GameData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class ModRecipes implements IModInit {

    private static String[] nameType;
    private static ItemStack[] inputItemStack;

    private static void addShapedRecipe(ResourceLocation name, ResourceLocation group, int index, @Nonnull ItemStack output, Object... params) {
        CraftingHelper.ShapedPrimer primer = CraftingHelper.parseShaped(params);

        ShapedRecipes value = new ShapedRecipes(group == null ? "" : group.toString(), primer.width, primer.height, primer.input, output, index);
        value.setRegistryName(name);

        GameData.register_impl(value);
    }

    private static void addShapelessRecipe(ResourceLocation name, ResourceLocation group, @Nonnull ItemStack output, Ingredient... params) {
        NonNullList<Ingredient> lst = NonNullList.create();
        lst.addAll(Arrays.asList(params));

        ShapelessRecipes value = new ShapelessRecipes(group == null ? "" : group.toString(), output, lst);
        value.setRegistryName(name);

        GameData.register_impl(value);
    }

    @ParametersAreNonnullByDefault
    public static void addRecipeBlock(Item output, Item ingot) {
        Object[] params = {
                "III",
                "III",
                "III",
                'I', ingot
        };
        GameRegistry.addShapedRecipe(new ResourceLocation(Objects.requireNonNull(output.getRegistryName()).toString() + '1'), new ResourceLocation(ModInformation.MOD_ID, "block"),
                output.getDefaultInstance(), params);
        GameRegistry.addShapelessRecipe(new ResourceLocation(Objects.requireNonNull(ingot.getRegistryName()).toString() + '1'), new ResourceLocation(ModInformation.MOD_ID, "ingot"),
                new ItemStack(ingot, 9), Ingredient.fromItem(output));
    }

    /**
     * @param output   要输出的物品
     * @param block    合成物品的前置材料，可为 null
     * @param oreIngot 合成物品的基础材料，可为 null
     */
    public static void addRecipeShell(@Nonnull Block output, @Nullable Block block, @Nullable String oreIngot) {
        Object[] params = {
                "OLO",
                "LIL",
                "OLO",
                'O', "ingotIron",
                'I', block == null ? "ingotIron" : block,
                'L', oreIngot == null ? Items.AIR : oreIngot
        };

        GameRegistry.addShapedRecipe(output.getRegistryName(), new ResourceLocation(ModInformation.MOD_ID, "shell"),
                Item.getItemFromBlock(output).getDefaultInstance(), params);
    }

    public static void addRecipeGear(@Nonnull Item output, String materialOreName) {
        Object[] params = {
                " O ",
                "OGO",
                " O ",
                'O', materialOreName,
                'G', "ingotIron"
        };
        GameRegistry.addShapedRecipe(output.getRegistryName(), new ResourceLocation(ModInformation.MOD_ID, "gears"),
                output.getDefaultInstance(), params);
    }

    public static void addRecipeTurbineRotor(@Nonnull Item output, String inputOre, String gearOre) {
        Object[] params = {
                "OOO",
                "OGO",
                "OOO",
                'O', inputOre,
                'G', gearOre
        };
        GameRegistry.addShapedRecipe(output.getRegistryName(), new ResourceLocation(ModInformation.MOD_ID, "turbineRotor"), output.getDefaultInstance(), params);
    }

    public static void addRecipeCoil(@Nonnull Item output, Item materialItem, String materialOreName) {
        Object[] params = {
                "OOO",
                "RCR",
                "OOO",
                'O', materialOreName,
                'C', materialItem,
                'R', "dustRedstone"
        };
        GameRegistry.addShapedRecipe(output.getRegistryName(), new ResourceLocation(ModInformation.MOD_ID, "coil"),
                output.getDefaultInstance(), params);
    }

    public static void addRecipeConduit(@Nonnull Item output, Item coil) {
        Object[] params = {
                "DDD",
                "CCC",
                "DDD",
                'C', coil,
                'D', "itemConduitBinder"
        };
        GameRegistry.addShapedRecipe(output.getRegistryName(), new ResourceLocation(ModInformation.MOD_ID, "duct_coil"),
                output.getDefaultInstance(), params);
    }

    public static void addRecipeGenerator(BlockGeneratorBasic output, ItemStack conduit, Item turbineRotor, BlockMachineShell blockMachineShell, Block oldGenerator, String gearOreName) {
        addRecipeGenerator(output, conduit, turbineRotor, blockMachineShell, Item.getItemFromBlock(oldGenerator).getDefaultInstance(), gearOreName);
    }

    /**
     * 注册发电机的配方
     *
     * @param output            配方输出的发电机
     * @param conduit           配方中的导管
     * @param turbineRotor      配方中的涡轮叶片
     * @param gearOreName       配方中的齿轮
     * @param blockMachineShell 配方中的外壳
     * @param oldGenerator      配方中的上一个级别的发电机
     */
    public static void addRecipeGenerator(BlockGeneratorBasic output, ItemStack conduit, Item turbineRotor, BlockMachineShell blockMachineShell, ItemStack oldGenerator, String gearOreName) {
        Object[] params = {
                "FZF",
                "DCD",
                "FGF",
                'F', blockMachineShell,
                'D', conduit,
                'Z', turbineRotor,
                'G', gearOreName,
                'C', oldGenerator
        };

        String[] nameStr = Objects.requireNonNull(output.getRegistryName()).getResourcePath().split("_");
        // 输出的发电机的类型
        if (Arrays.stream(nameType).anyMatch(s -> Objects.equals(s, nameStr[0]))) {
            addGeneratorRecipe(
                    output,
                    inputItemStack[Arrays.stream(nameType).collect(Collectors.toList()).indexOf(nameStr[0])],
                    nameStr[2],
                    Arrays.stream(nameType.clone()).filter(s -> !Objects.equals(s, nameStr[0])).toArray(String[]::new)
            );
        }

        addShapedRecipe(
                output.getRegistryName(),
                new ResourceLocation(ModInformation.MOD_ID, "generator"),
                4,
                Item.getItemFromBlock(output).getDefaultInstance(),
                params
        );
    }

    /**
     * 注册液体类发电机的初始配方
     *
     * @param output         配方输出的发电机
     * @param otherFluidItem 配方中的 oldGenerator 上方的物品，装有液体的桶
     */
    public static void addRecipeGenerator(BlockGeneratorBasic output, ItemStack otherFluidItem) {
        Object[] params = {
                "FZF",
                "DCD",
                "FGF",
                'F', BlocksMachine.machineShells[0],
                'D', ItemsMaterial.conduits[0],
                'Z', ItemsMaterial.turbines[0],
                'G', otherFluidItem,
                'C', BlocksGenerator.blockGeneratorFluid[0]
        };
        addShapedRecipe(
                output.getRegistryName(),
                new ResourceLocation(ModInformation.MOD_ID, "generator"),
                4,
                Item.getItemFromBlock(output).getDefaultInstance(),
                params
        );

    }

    public static void addRecipeMachineVaporization(BlockMachineBase output, BlockMachineShell machineShell, Item conduit, String gearOre) {
        addRecipeMachineVaporization(output, machineShell, conduit.getDefaultInstance(), gearOre);
    }

    public static void addRecipeMachineVaporization(BlockMachineBase output, BlockMachineShell machineShell, ItemStack conduit, String gearOre) {
        Object[] params = {
                "FCF",
                "DGD",
                "FCF",
                'F', machineShell,
                'D', Items.BUCKET,
                'C', conduit,
                'G', gearOre
        };

        GameRegistry.addShapedRecipe(
                output.getRegistryName(),
                new ResourceLocation(ModInformation.MOD_ID, "machine"),
                Item.getItemFromBlock(output).getDefaultInstance(),
                params);
    }

    /**
     * 注册发电机配方
     *
     * @param name     发电机名称
     * @param nameStr  发电机类型列表
     * @param output   输出的发电机
     * @param fuelItem 配方的另一个成分
     */
    public static void addGeneratorRecipe(Block output, ItemStack fuelItem, String name, String[] nameStr) {
        Item generator = Item.getItemFromBlock(output);

        Item[] otherGenerators = new Item[nameStr.length];
        for (int i = 0; i < otherGenerators.length; i++) {
            otherGenerators[i] = Item.getByNameOrId(ModInformation.MOD_ID + ":" + nameStr[i] + "_generator_" + name);
        }

        int i = 0;
        for (Item otherGenerator : otherGenerators) {
            addShapelessRecipe(
                    new ResourceLocation(ModInformation.MOD_ID, Objects.requireNonNull(generator.getRegistryName()).getResourcePath() + "_" + i),
                    new ResourceLocation(ModInformation.MOD_ID, "generator2"),
                    generator.getDefaultInstance(),
                    Ingredient.fromItem(otherGenerator),
                    Ingredient.fromStacks(fuelItem)
            );
            i++;
        }
    }

    public static void addRecipeComponent(ItemComponent component, Item oldItem, Item recipeItem) {
        Object[] params = {
                "GGG",
                "ROR",
                "GGG",
                'G', "ingotGoldPlatedIron",
                'R', recipeItem != null ? recipeItem : "ingotGoldPlatedIron",
                'O', oldItem
        };

        GameRegistry.addShapedRecipe(
                component.getRegistryName(),
                new ResourceLocation(ModInformation.MOD_ID, "component"),
                component.getDefaultInstance(),
                params
        );
    }

    @Override
    public void preInit() {
        nameType = new String[]{"turbine", "fluids"};
        inputItemStack = new ItemStack[]{
                ItemsMaterial.coils[0].getDefaultInstance(),
                Items.BUCKET.getDefaultInstance()
        };
    }

    @Override
    public void init() {
        RecipeList.init();
        GameRegistry.addSmelting(ModItems.ductShell_bank, new ItemStack(ModItems.ductShell, 8), 0.5f);

        GameRegistry.addShapedRecipe(ModItems.GOLD_PLATED_IRON_INGOT.getRegistryName(),
                new ResourceLocation(ModInformation.MOD_ID, "ingot"),
                new ItemStack(ModItems.GOLD_PLATED_IRON_INGOT, 8),
                "III",
                "IGI",
                "III",
                'I', "ingotIron",
                'G', "ingotGold"
        );
        GameRegistry.addShapedRecipe(Item.getItemFromBlock(ModBlocks.GOLD_PLATED_IRON_BLOCK).getRegistryName(),
                new ResourceLocation(ModInformation.MOD_ID, "block"),
                new ItemStack(Item.getItemFromBlock(ModBlocks.GOLD_PLATED_IRON_BLOCK), 8),
                "III",
                "IGI",
                "III",
                'I', "blockIron",
                'G', "blockGold"
        );
        /*
        1GameRegistry.addShapedRecipe(ModItems.information_finder.getRegistryName(), null, ModItems.information_finder.getDefaultInstance(),
                "IBI",
                "IRI",
                "IGI",
                'I', "ingotIron",
                'B', "blockGlassColorless",
                'R', "dustRedstone",
                'G', "ingotGoldPlatedIron"
        );
         */
    }
}
