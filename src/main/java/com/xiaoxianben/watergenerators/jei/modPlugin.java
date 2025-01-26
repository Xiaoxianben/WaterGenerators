package com.xiaoxianben.watergenerators.jei;

import com.xiaoxianben.watergenerators.config.ConfigLoader;
import com.xiaoxianben.watergenerators.config.ConfigValue;
import com.xiaoxianben.watergenerators.gui.guiContainer.GuiGeneratorFluid;
import com.xiaoxianben.watergenerators.gui.guiContainer.GuiMachineVa;
import com.xiaoxianben.watergenerators.init.EnumModBlock;
import com.xiaoxianben.watergenerators.jei.advancedGuiHandlers.GeneratorAdvancedGuiHandlers;
import com.xiaoxianben.watergenerators.jei.recipeCategory.FluidRecipeCategory;
import com.xiaoxianben.watergenerators.jei.recipeCategory.vaporizationRecipeCategory;
import com.xiaoxianben.watergenerators.jei.wrapper.FluidGeneratorWrapper;
import com.xiaoxianben.watergenerators.jei.wrapper.steamWrapper;
import com.xiaoxianben.watergenerators.jsonRecipe.ModJsonRecipe;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static com.xiaoxianben.watergenerators.jei.recipeCategory.vaporizationRecipeCategory.vaporizationUID;

@JEIPlugin
public class modPlugin implements IModPlugin {

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
        registry.addRecipeCategories(new vaporizationRecipeCategory(guiHelper));
        registry.addRecipeCategories(new FluidRecipeCategory(guiHelper));
    }

    @Override
    public void register(@Nonnull IModRegistry registration) {
        for (Block[] vaporization : EnumModBlock.MACHINE_VAPORIZATION.blockMap.values()) {
            for (Block block : vaporization) {
                if (block != null && !Item.getItemFromBlock(block).getDefaultInstance().isEmpty())
                    registration.addRecipeCatalyst(Item.getItemFromBlock(block).getDefaultInstance(), vaporizationUID);
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
                    registration.addRecipeCatalyst(Item.getItemFromBlock(block).getDefaultInstance(), FluidRecipeCategory.FluidGeneratorUID);
            }
        }
        for (Block[] value : EnumModBlock.GENERATOR_steam.blockMap.values()) {
            for (Block block : value) {
                if (block != null && !Item.getItemFromBlock(block).getDefaultInstance().isEmpty())
                    registration.addRecipeCatalyst(Item.getItemFromBlock(block).getDefaultInstance(), FluidRecipeCategory.FluidGeneratorUID);
            }
        }

        registration.addRecipes(steamRecipes(), vaporizationUID);
        registration.addRecipes(FluidGeneratorRecipes(), FluidRecipeCategory.FluidGeneratorUID);

        registration.addAdvancedGuiHandlers(new GeneratorAdvancedGuiHandlers());
        registration.addRecipeClickArea(GuiMachineVa.class, 70, 28, 36, 17, vaporizationUID);
        registration.addRecipeClickArea(GuiGeneratorFluid.class, 40, 33, 20, 20, FluidRecipeCategory.FluidGeneratorUID);
    }

    private List<steamWrapper> steamRecipes() {
        List<steamWrapper> recipes = new ArrayList<>();

        // steam
        for (int i = 0; i < ModJsonRecipe.recipeVaporization.size(); i++) {
            FluidStack input = ModJsonRecipe.recipeVaporization.getInput(i);
            FluidStack Output = ModJsonRecipe.recipeVaporization.getOutput(i);

            recipes.add(new steamWrapper(input, Output));
        }

        return recipes;
    }
    private List<FluidGeneratorWrapper> FluidGeneratorRecipes() {
        List<FluidGeneratorWrapper> recipes = new ArrayList<>();

        // steam
        for (int i = 0; i < ModJsonRecipe.recipeFluidGenerator.size(); i++) {
            FluidStack input = new FluidStack(ModJsonRecipe.recipeFluidGenerator.getInput(i), ConfigValue.basicAmountOfFluidToProduceEnergy);
//            FluidStack Output = ModJsonRecipe.recipeVaporization.getOutput(i);

            recipes.add(new FluidGeneratorWrapper(input));
        }

        return recipes;
    }

}
