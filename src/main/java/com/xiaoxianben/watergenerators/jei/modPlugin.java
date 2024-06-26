package com.xiaoxianben.watergenerators.jei;

import com.xiaoxianben.watergenerators.blocks.BlocksMachine;
import com.xiaoxianben.watergenerators.blocks.machine.BlockMachineVaporization;
import com.xiaoxianben.watergenerators.jei.advancedGuiHandlers.GeneratorAdvancedGuiHandlers;
import com.xiaoxianben.watergenerators.jei.recipeCategory.vaporizationRecipeCategory;
import com.xiaoxianben.watergenerators.jei.wrapper.steamWrapper;
import com.xiaoxianben.watergenerators.otherModsItems.EnderIOInit;
import com.xiaoxianben.watergenerators.otherModsItems.TFInit;
import com.xiaoxianben.watergenerators.recipe.recipeList;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.xiaoxianben.watergenerators.jei.recipeCategory.vaporizationRecipeCategory.vaporizationUID;

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
        vaporizationList.addAll(Arrays.asList(BlocksMachine.machineVaporizations));
        vaporizationList.addAll(Arrays.asList(TFInit.TF_machineVaporization));
        vaporizationList.addAll(Arrays.asList(EnderIOInit.enderIO_machineVaporization));
        for (BlockMachineVaporization vaporization : vaporizationList) {
            if (vaporization != null)
                registration.addRecipeCatalyst(Item.getItemFromBlock(vaporization).getDefaultInstance(), vaporizationUID);
        }

        registration.addRecipes(steamRecipes(), vaporizationUID);

        registration.addAdvancedGuiHandlers(new GeneratorAdvancedGuiHandlers());
    }

    private List<steamWrapper> steamRecipes() {
        List<steamWrapper> recipes = new ArrayList<>();

        // steam
        for (int i = 0; i < recipeList.recipeVaporization.size(); i++) {
            FluidStack input = recipeList.recipeVaporization.getInput(i);
            FluidStack Output = recipeList.recipeVaporization.getOutput(i);

            recipes.add(new steamWrapper(input, Output));
        }

        return recipes;
    }

}
