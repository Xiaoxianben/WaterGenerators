package com.xiaoxianben.watergenerators.jei.recipeCategory;

import com.xiaoxianben.watergenerators.WaterGenerators;
import com.xiaoxianben.watergenerators.jei.wrapper.FluidGeneratorWrapper;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class FluidRecipeCategory implements IRecipeCategory<FluidGeneratorWrapper> {

    public final static String FluidGeneratorUID = WaterGenerators.MOD_ID + ":fluidGenerator";


    private final String title;
    private final IDrawable background;

    public FluidRecipeCategory(IGuiHelper guiHelper) {
        this.title = I18n.format("RC.fluidGenerator.title");
        this.background = guiHelper.createDrawable(new ResourceLocation(WaterGenerators.MOD_ID, "textures/gui/2.png"), 73, 13, 18, 60);
    }

    @Override
    public String getUid() {
        return FluidGeneratorUID;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getModName() {
        return WaterGenerators.NAME;
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void drawExtras(@Nonnull Minecraft minecraft) {
//        minecraft.fontRenderer.drawString();
    }


    @Override
    public void setRecipe(IRecipeLayout recipeLayout, FluidGeneratorWrapper recipeWrapper, IIngredients ingredients) {
        IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();


        fluidStacks.init(0, true, 1, 1, 16, 58, 1, false, null);

        fluidStacks.set(0, ingredients.getInputs(VanillaTypes.FLUID).get(0));
    }
}
