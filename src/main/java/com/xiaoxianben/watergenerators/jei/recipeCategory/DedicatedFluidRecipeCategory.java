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
import net.minecraftforge.fluids.Fluid;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class DedicatedFluidRecipeCategory implements IRecipeCategory<FluidGeneratorWrapper> {

    public final static String FluidGeneratorUID = WaterGenerators.MOD_ID + ":fluidGenerator";


    private final String title;
    private final String name;
    private final IDrawable background;

    public DedicatedFluidRecipeCategory(String name, IGuiHelper guiHelper) {
        this.name = name;
        this.title = I18n.format("RC.fluidGenerator.title");
        this.background = guiHelper.createDrawable(new ResourceLocation(WaterGenerators.MOD_ID, "textures/gui/2.png"), 73, 13, 18, 60);
    }

    @Nonnull
    @Override
    public String getUid() {
        return WaterGenerators.MOD_ID + ":" + name;
    }

    @Nonnull
    @Override
    public String getTitle() {
        return this.title;
    }

    @Nonnull
    @Override
    public String getModName() {
        return WaterGenerators.NAME;
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void drawExtras(@Nonnull Minecraft minecraft) {
//        minecraft.fontRenderer.drawString();
    }


    @ParametersAreNonnullByDefault
    @Override
    public void setRecipe(IRecipeLayout recipeLayout, FluidGeneratorWrapper recipeWrapper, IIngredients ingredients) {
        IGuiFluidStackGroup fluidStacks = recipeLayout.getFluidStacks();


        fluidStacks.init(0, true, 1, 1, 16, 58, 1, false, null);

        fluidStacks.set(0, ingredients.getInputs(VanillaTypes.FLUID).get(0));
    }


    public static String getFluidUid(String name) {
        return WaterGenerators.MOD_ID + ":" + name;
    }
}
