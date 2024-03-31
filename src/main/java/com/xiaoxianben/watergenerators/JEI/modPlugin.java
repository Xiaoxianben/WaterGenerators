package com.xiaoxianben.watergenerators.JEI;

import com.xiaoxianben.watergenerators.JEI.recipeCategory.vaporizationRecipeCategory;
import com.xiaoxianben.watergenerators.JEI.wrapper.steamWrapper;
import com.xiaoxianben.watergenerators.blocks.BlockMachineVaporization;
import com.xiaoxianben.watergenerators.blocks.BlocksMachine;
import com.xiaoxianben.watergenerators.recipe.recipeList;
import com.xiaoxianben.watergenerators.recipe.recipeTypes;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static com.xiaoxianben.watergenerators.JEI.recipeCategory.vaporizationRecipeCategory.vaporizationUID;

@JEIPlugin
public class modPlugin implements IModPlugin {

    public static List<BlockMachineVaporization> vaporizationList = new ArrayList<>();

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
        registry.addRecipeCategories(new vaporizationRecipeCategory(guiHelper));
    }

    @Override
    public void register(@Nonnull IModRegistry registration) {

        for (BlockMachineVaporization vaporization : vaporizationList) {
            registration.addRecipeCatalyst(Item.getItemFromBlock(vaporization).getDefaultInstance(), vaporizationUID);
        }

        registration.addRecipes(steamRecipes(), vaporizationUID);
    }

    private List<steamWrapper> steamRecipes() {
        List<steamWrapper> recipes = new ArrayList<>();

        // steam
        for (int i = 0; i < recipeList.recipeVaporizations.size(); i++) {
            FluidStack input = recipeList.recipeVaporizations.get(i).getInputs(recipeTypes.FLUID).get(0);
            FluidStack Output = recipeList.recipeVaporizations.get(i).getOutputs(recipeTypes.FLUID).get(0);

            recipes.add(new steamWrapper(input, Output));
        }

        return recipes;
    }

}
