package com.xiaoxianben.watergenerators.init;

import com.xiaoxianben.watergenerators.WaterGenerators;
import com.xiaoxianben.watergenerators.blocks.generator.BlockGeneratorBasic;
import com.xiaoxianben.watergenerators.blocks.generator.BlockGeneratorFluid;
import com.xiaoxianben.watergenerators.blocks.machine.BlockMachineBase;
import com.xiaoxianben.watergenerators.blocks.machine.BlockMachineShell;
import com.xiaoxianben.watergenerators.init.modRegister.EnumModRegister;
import com.xiaoxianben.watergenerators.items.component.ItemComponent;
import com.xiaoxianben.watergenerators.jsonRecipe.ModJsonRecipe;
import com.xiaoxianben.watergenerators.jsonRecipe.crafting.ShapedRecipes;
import com.xiaoxianben.watergenerators.jsonRecipe.crafting.ShapelessRecipes;
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
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;

public class ModRecipes {

    public static ModRecipes instance;

    public final HashMap<String, ItemStack> mapFluidMaterial = new HashMap<>();
    public final String[] nameType = new String[]{"turbine", "fluid"};
    public ItemStack[] inputItemStack;
    public HashMap<String, Integer> recipeIdMap = new HashMap<>();

    private void addShapedRecipe(ResourceLocation name, ResourceLocation group, int index, @Nonnull ItemStack output, Object... params) {
        CraftingHelper.ShapedPrimer primer = CraftingHelper.parseShaped(params);

        ShapedRecipes value = new ShapedRecipes(group == null ? "" : group.toString(), primer.width, primer.height, primer.input, output, index);
        value.setRegistryName(name);

        GameData.register_impl(value);
    }

    private void addShapelessRecipe(ResourceLocation name, ResourceLocation group, @Nonnull ItemStack output, Ingredient... params) {
        NonNullList<Ingredient> lst = NonNullList.create();
        lst.addAll(Arrays.asList(params));

        ShapelessRecipes value = new ShapelessRecipes(group == null ? "" : group.toString(), output, lst);
        value.setRegistryName(name);

        GameData.register_impl(value);
    }

    @ParametersAreNonnullByDefault
    public void addRecipeBlock(Item output, Item ingot) {
        Object[] params = {
                "III",
                "III",
                "III",
                'I', ingot
        };
        GameRegistry.addShapedRecipe(new ResourceLocation(Objects.requireNonNull(output.getRegistryName()).toString() + '1'), new ResourceLocation(WaterGenerators.MOD_ID, "block"),
                output.getDefaultInstance(), params);
        GameRegistry.addShapelessRecipe(new ResourceLocation(Objects.requireNonNull(ingot.getRegistryName()).toString() + '1'), new ResourceLocation(WaterGenerators.MOD_ID, "ingot"),
                new ItemStack(ingot, 9), Ingredient.fromItem(output));
    }

    /**
     * @param output   要输出的物品
     * @param block    合成物品的前置材料，可为 null
     * @param oreIngot 合成物品的基础材料，可为 null
     */
    public void addRecipeShell(@Nonnull Block output, @Nullable Block block, @Nullable String oreIngot) {
        Object[] params = {
                "OLO",
                "LIL",
                "OLO",
                'O', "ingotIron",
                'I', block == null ? "ingotIron" : block,
                'L', oreIngot == null ? Items.AIR : oreIngot
        };

        GameRegistry.addShapedRecipe(output.getRegistryName(), new ResourceLocation(WaterGenerators.MOD_ID, "shell"),
                Item.getItemFromBlock(output).getDefaultInstance(), params);
    }

    public void addRecipeGear(@Nonnull Item output, String materialOreName) {
        Object[] params = {
                " O ",
                "OGO",
                " O ",
                'O', materialOreName,
                'G', "ingotIron"
        };
        GameRegistry.addShapedRecipe(output.getRegistryName(), new ResourceLocation(WaterGenerators.MOD_ID, "gears"),
                output.getDefaultInstance(), params);
    }

    public void addRecipeTurbineRotor(@Nonnull Item output, String inputOre, String gearOre) {
        Object[] params = {
                "OOO",
                "OGO",
                "OOO",
                'O', inputOre,
                'G', gearOre
        };
        GameRegistry.addShapedRecipe(output.getRegistryName(), new ResourceLocation(WaterGenerators.MOD_ID, "turbineRotor"), output.getDefaultInstance(), params);
    }

    public void addRecipeCoil(@Nonnull Item output, Item materialItem, String materialOreName) {
        Object[] params = {
                "OOO",
                "RCR",
                "OOO",
                'O', materialOreName,
                'C', materialItem == null ? materialOreName:materialItem,
                'R', "dustRedstone"
        };
        GameRegistry.addShapedRecipe(output.getRegistryName(), new ResourceLocation(WaterGenerators.MOD_ID, "coil"),
                output.getDefaultInstance(), params);
    }

    public void addRecipeConduit(@Nonnull Item output, Item coil, Item oldItem) {
        Object[] params = {
                "BBB",
                "CDC",
                "BBB",
                'C', coil,
                'D', oldItem,
                'B', "itemConduitBinder"
        };
        GameRegistry.addShapedRecipe(output.getRegistryName(),
                new ResourceLocation(WaterGenerators.MOD_ID, "duct_coil"),
                output.getDefaultInstance(),
                params);
    }

    public void addRecipeGenerator(BlockGeneratorBasic output, ItemStack conduit, Item turbineRotor, BlockMachineShell blockMachineShell, Block oldGenerator, String gearOreName) {
        addRecipeGenerator(output, conduit, turbineRotor, blockMachineShell, oldGenerator == null ? null : Item.getItemFromBlock(oldGenerator).getDefaultInstance(), gearOreName);
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
    public void addRecipeGenerator(BlockGeneratorBasic output, ItemStack conduit, Item turbineRotor, BlockMachineShell blockMachineShell, ItemStack oldGenerator, String gearOreName) {
        String[] nameStr = Objects.requireNonNull(output.getRegistryName()).getResourcePath().split("_");


        // 让基础发电机可以互相转化
        if (Arrays.stream(nameType).anyMatch(s -> Objects.equals(s, nameStr[0]))) {
            addGeneratorRecipe(
                    output,
                    inputItemStack[Arrays.stream(nameType).collect(Collectors.toList()).indexOf(nameStr[0])],
                    nameStr[2],
                    Arrays.stream(nameType.clone()).filter(s -> !Objects.equals(s, nameStr[0])).toArray(String[]::new)
            );
        }
        // 流体发电机的配方的特别添加
        if (output instanceof BlockGeneratorFluid) {
            ItemStack materialItem = mapFluidMaterial.get(nameStr[0]);
            if (materialItem != null) {
                BlockGeneratorFluid inBlock = Objects.requireNonNull((BlockGeneratorFluid) Block.getBlockFromName(WaterGenerators.MOD_ID + ":fluid_generator_" + nameStr[2]));
                addShapelessRecipe(
                        new ResourceLocation(WaterGenerators.MOD_ID, getRecipeGeneratorPath(inBlock)),
                        new ResourceLocation(WaterGenerators.MOD_ID, "generator_downgrade"),
                        Item.getItemFromBlock(inBlock).getDefaultInstance(),
                        Ingredient.fromItem(Item.getItemFromBlock(output)),
                        Ingredient.fromItem(Items.FLINT)
                );
                if (nameStr[2].equals("level1")) {
                    addGeneratorFluid(output, inBlock, materialItem);
                    return;
                }
                addGeneratorFluid(output, inBlock, materialItem, 1);
            }
        }

        Object[] params = {
                "FZF",
                "DCD",
                "FGF",
                'F', blockMachineShell,
                'D', conduit,
                'Z', turbineRotor,
                'G', gearOreName,
                'C', oldGenerator == null ? inputItemStack[Arrays.stream(nameType).collect(Collectors.toList()).indexOf(nameStr[0])] : oldGenerator
        };


        addShapedRecipe(
                output.getRegistryName(),
                null,
                4,
                Item.getItemFromBlock(output).getDefaultInstance(),
                params
        );
    }

    /**
     * 注册液体类发电机的初始配方，和流体类发电机的转化。
     *
     * @param output         配方输出的发电机
     * @param otherFluidItem 配方中的 oldGenerator 上方的物品，装有液体的桶
     */
    private void addGeneratorFluid(BlockGeneratorBasic output, BlockGeneratorFluid inBlock, ItemStack otherFluidItem) {
        addGeneratorFluid(output, inBlock, otherFluidItem, 0);
    }

    private void addGeneratorFluid(BlockGeneratorBasic output, BlockGeneratorFluid inBlock, ItemStack otherFluidItem, int id) {
        Object[] params = {
                "FZF",
                "DCD",
                "FGF",
                'F', EnumModBlock.MACHINE_SHELL.blockMap.get(EnumModRegister.MINECRAFT)[0],
                'D', EnumModItems.CONDUIT.itemMap.get(EnumModRegister.MINECRAFT)[0],
                'Z', EnumModItems.TURBINE_ROTOR.itemMap.get(EnumModRegister.MINECRAFT)[0],
                'G', otherFluidItem,
                'C', inBlock
        };
        addShapedRecipe(
                new ResourceLocation(WaterGenerators.MOD_ID, id == 0 ? output.getRegistryName().getResourcePath() : getRecipeGeneratorPath(output)),
                new ResourceLocation(WaterGenerators.MOD_ID, "generator_fluid"),
                4,
                Item.getItemFromBlock(output).getDefaultInstance(),
                params
        );
    }

    /**
     * 注册相互转化基础发电机的配方
     *
     * @param name     发电机名称
     * @param nameStr  发电机类型列表
     * @param output   输出的发电机
     * @param fuelItem 配方的另一个成分
     */
    private void addGeneratorRecipe(Block output, ItemStack fuelItem, String name, String[] nameStr) {
        Item generator = Item.getItemFromBlock(output);

        Item[] otherGenerators = new Item[nameStr.length];
        for (int i = 0; i < otherGenerators.length; i++) {
            otherGenerators[i] = Item.getByNameOrId(WaterGenerators.MOD_ID + ":" + nameStr[i] + "_generator_" + name);
        }

        for (Item otherGenerator : otherGenerators) {
            addShapelessRecipe(
                    new ResourceLocation(WaterGenerators.MOD_ID, getRecipeGeneratorPath(output)),
                    new ResourceLocation(WaterGenerators.MOD_ID, "generator_transformation"),
                    generator.getDefaultInstance(),
                    Ingredient.fromItem(otherGenerator),
                    Ingredient.fromStacks(fuelItem)
            );
        }
    }

    private String getRecipeGeneratorPath(Block block) {
        int i = getRecipeGeneratorId(Objects.requireNonNull(block.getRegistryName()).toString());
        return block.getRegistryName().getResourcePath()+"_"+i;
    }
    private int getRecipeGeneratorId(String recipeName) {
        Integer i = recipeIdMap.get(recipeName);
        if (i == null) {
            recipeIdMap.put(recipeName, 1);
            i = 0;
        } else {
            recipeIdMap.put(recipeName, i + 1);
        }
        return i;
    }

    public void addRecipeMachineVaporization(BlockMachineBase output, BlockMachineShell machineShell, ItemStack conduit, String gearOre) {
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
                new ResourceLocation(WaterGenerators.MOD_ID, "machine"),
                Item.getItemFromBlock(output).getDefaultInstance(),
                params);
    }
    public void addRecipeMachineConcentration(BlockMachineBase output, BlockMachineShell machineShell, ItemStack conduit, BlockMachineBase inMachine) {
        Object[] params = {
                "FCF",
                "DGD",
                "FCF",
                'F', machineShell,
                'D', Items.BUCKET,
                'C', conduit,
                'G', inMachine
        };

        GameRegistry.addShapedRecipe(
                output.getRegistryName(),
                new ResourceLocation(WaterGenerators.MOD_ID, "machine"),
                Item.getItemFromBlock(output).getDefaultInstance(),
                params);
    }

    public void addRecipeComponent(ItemComponent component, Item oldItem, Item recipeItem) {
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
                new ResourceLocation(WaterGenerators.MOD_ID, "component"),
                component.getDefaultInstance(),
                params
        );
    }


    public void init() {

        new ModJsonRecipe().init();

        recipeIdMap = null;
    }

}
