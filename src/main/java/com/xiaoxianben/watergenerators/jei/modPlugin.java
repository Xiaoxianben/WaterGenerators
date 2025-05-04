package com.xiaoxianben.watergenerators.jei;

import com.xiaoxianben.watergenerators.config.ConfigValue;
import com.xiaoxianben.watergenerators.gui.guiContainer.GuiGeneratorFluid;
import com.xiaoxianben.watergenerators.gui.guiContainer.GuiMachineConcentration;
import com.xiaoxianben.watergenerators.gui.guiContainer.GuiMachineVa;
import com.xiaoxianben.watergenerators.init.EnumModBlock;
import com.xiaoxianben.watergenerators.jei.advancedGuiHandlers.GeneratorAdvancedGuiHandlers;
import com.xiaoxianben.watergenerators.jei.recipeCategory.ConcentrationRecipeCategory;
import com.xiaoxianben.watergenerators.jei.recipeCategory.DedicatedFluidRecipeCategory;
import com.xiaoxianben.watergenerators.jei.recipeCategory.FluidRecipeCategory;
import com.xiaoxianben.watergenerators.jei.recipeCategory.VaporizationRecipeCategory;
import com.xiaoxianben.watergenerators.jei.wrapper.FluidGeneratorWrapper;
import com.xiaoxianben.watergenerators.jei.wrapper.FluidToFluidWrapper;
import com.xiaoxianben.watergenerators.jsonRecipe.ModJsonRecipe;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



@JEIPlugin
public class modPlugin implements IModPlugin {

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
        registry.addRecipeCategories(new VaporizationRecipeCategory(guiHelper));
        registry.addRecipeCategories(new ConcentrationRecipeCategory(guiHelper));
        registry.addRecipeCategories(new FluidRecipeCategory(guiHelper));

        // 注册专门的流体发电机合成配方类别
        for (int i = 0; i < ModJsonRecipe.recipeFluidGenerator.size(); i++) {
            Fluid input = ModJsonRecipe.recipeFluidGenerator.getInput(i);
            registry.addRecipeCategories(new DedicatedFluidRecipeCategory(input.getName(), guiHelper));
        }
    }

    @Override
    public void register(@Nonnull IModRegistry registration) {
        for (Block[] vaporization : EnumModBlock.MACHINE_VAPORIZATION.blockMap.values()) {
            for (Block block : vaporization) {
                if (block != null && !Item.getItemFromBlock(block).getDefaultInstance().isEmpty())
                    registration.addRecipeCatalyst(Item.getItemFromBlock(block).getDefaultInstance(), VaporizationRecipeCategory.vaporizationUID);
            }
        }
        for (Block[] vaporization : EnumModBlock.MACHINE_CONCENTRATION.blockMap.values()) {
            for (Block block : vaporization) {
                if (block != null && !Item.getItemFromBlock(block).getDefaultInstance().isEmpty())
                    registration.addRecipeCatalyst(Item.getItemFromBlock(block).getDefaultInstance(), ConcentrationRecipeCategory.concentrationUID);
            }
        }
        for (Block[] value : EnumModBlock.GENERATOR_fluid.blockMap.values()) {
            for (Block block : value) {
                if (block != null && !Item.getItemFromBlock(block).getDefaultInstance().isEmpty())
                    registration.addRecipeCatalyst(Item.getItemFromBlock(block).getDefaultInstance(), FluidRecipeCategory.FluidGeneratorUID);
            }
        }
        for (Block[] value : EnumModBlock.GENERATOR_water.blockMap.values()) {
            for (Block block : value) {
                if (block != null && !Item.getItemFromBlock(block).getDefaultInstance().isEmpty())
                    registration.addRecipeCatalyst(Item.getItemFromBlock(block).getDefaultInstance(), DedicatedFluidRecipeCategory.getFluidUid("water"));
            }
        }
        for (Block[] value : EnumModBlock.GENERATOR_waterCompressed.blockMap.values()) {
            for (Block block : value) {
                if (block != null && !Item.getItemFromBlock(block).getDefaultInstance().isEmpty())
                    registration.addRecipeCatalyst(Item.getItemFromBlock(block).getDefaultInstance(), DedicatedFluidRecipeCategory.getFluidUid("watercompressed"));
            }
        }
        for (Block[] value : EnumModBlock.GENERATOR_steam.blockMap.values()) {
            for (Block block : value) {
                if (block != null && !Item.getItemFromBlock(block).getDefaultInstance().isEmpty())
                    registration.addRecipeCatalyst(Item.getItemFromBlock(block).getDefaultInstance(), DedicatedFluidRecipeCategory.getFluidUid("steam"));
            }
        }

        registration.addRecipes(vaporizationRecipes(), VaporizationRecipeCategory.vaporizationUID);
        registration.addRecipes(concentrationRecipes(), ConcentrationRecipeCategory.concentrationUID);
        registration.addRecipes(FluidGeneratorRecipes(), FluidRecipeCategory.FluidGeneratorUID);
        // 注册专门的流体发电机合成配方
        for (int i = 0; i < ModJsonRecipe.recipeFluidGenerator.size(); i++) {
            Fluid fluid = ModJsonRecipe.recipeFluidGenerator.getInput(i);
            FluidStack input = new FluidStack(fluid, ConfigValue.basicAmountOfFluidToProduceEnergy);

            registration.addRecipes(Collections.singleton(new FluidGeneratorWrapper(input)), DedicatedFluidRecipeCategory.getFluidUid(fluid.getName()));
        }


        registration.addAdvancedGuiHandlers(new GeneratorAdvancedGuiHandlers());

        // 注册合成点击区域
        registration.addRecipeClickArea(GuiMachineConcentration.class, 70, 28, 36, 17, ConcentrationRecipeCategory.concentrationUID);
        registration.addRecipeClickArea(GuiMachineVa.class, 70, 28, 36, 17, VaporizationRecipeCategory.vaporizationUID);

        String[] UIDS = new String[ModJsonRecipe.recipeFluidGenerator.size() + 1];
        UIDS[0] = FluidRecipeCategory.FluidGeneratorUID;
        for (int i = 1; i <= ModJsonRecipe.recipeFluidGenerator.size(); i++) {
            Fluid fluid = ModJsonRecipe.recipeFluidGenerator.getInput(i);
            UIDS[i] = DedicatedFluidRecipeCategory.getFluidUid(fluid.getName());
        }
        registration.addRecipeClickArea(GuiGeneratorFluid.class, 40, 33, 20, 20, UIDS);

    }

    private List<FluidToFluidWrapper> vaporizationRecipes() {
        List<FluidToFluidWrapper> recipes = new ArrayList<>();

        // steam
        for (int i = 0; i < ModJsonRecipe.recipeVaporization.size(); i++) {
            FluidStack input = ModJsonRecipe.recipeVaporization.getInput(i);
            FluidStack Output = ModJsonRecipe.recipeVaporization.getOutput(i).getFluidStack1();

            recipes.add(new FluidToFluidWrapper(input, Output));
        }

        return recipes;
    }
    private List<FluidToFluidWrapper> concentrationRecipes() {
        List<FluidToFluidWrapper> recipes = new ArrayList<>();

        // steam
        for (int i = 0; i < ModJsonRecipe.recipeConcentration.size(); i++) {
            FluidStack input = ModJsonRecipe.recipeConcentration.getInput(i);
            FluidStack Output = ModJsonRecipe.recipeConcentration.getOutput(i).getFluidStack1();

            recipes.add(new FluidToFluidWrapper(input, Output));
        }

        return recipes;
    }
    private List<FluidGeneratorWrapper> FluidGeneratorRecipes() {
        List<FluidGeneratorWrapper> recipes = new ArrayList<>();

        for (int i = 0; i < ModJsonRecipe.recipeFluidGenerator.size(); i++) {
            FluidStack input = new FluidStack(ModJsonRecipe.recipeFluidGenerator.getInput(i), ConfigValue.basicAmountOfFluidToProduceEnergy);

            recipes.add(new FluidGeneratorWrapper(input));
        }

        return recipes;
    }

}
