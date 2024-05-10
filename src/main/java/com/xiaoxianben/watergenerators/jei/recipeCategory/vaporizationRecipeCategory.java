package com.xiaoxianben.watergenerators.jei.recipeCategory;

import com.xiaoxianben.watergenerators.jei.wrapper.steamWrapper;
import com.xiaoxianben.watergenerators.util.ModInformation;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class vaporizationRecipeCategory implements IRecipeCategory<steamWrapper> {

    private static final int input1 = 0; // THE NUMBER = SLOT ID
    private static final int output1 = 1; // THE NUMBER = SLOT ID
    public static String vaporizationUID = ModInformation.MOD_ID + ":vaporization";
    private final String title;
    private final IDrawable background, animation;

    public vaporizationRecipeCategory(IGuiHelper guiHelper) {
        this.title = I18n.format("RC.vaporization.title");
        this.background = guiHelper.createDrawable(new ResourceLocation(ModInformation.MOD_ID, "textures/gui/steam.png"), 0, 0, 76, 36);
        this.animation = guiHelper.createAnimatedDrawable(
                guiHelper.createDrawable(new ResourceLocation(ModInformation.MOD_ID, "textures/gui/steam.png"), 76, 0, 36, 17),
                20 * 5,
                IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Nonnull
    @Override
    public String getUid() {
        return vaporizationUID;
    }

    @Nonnull
    @Override
    public String getTitle() {
        return title;
    }

    @Nonnull
    @Override
    public String getModName() {
        return ModInformation.NAME;
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void drawExtras(@Nonnull Minecraft minecraft) {
        this.animation.draw(minecraft, 20, 10);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, @Nonnull steamWrapper recipeWrapper, @Nonnull IIngredients ingredients) {

        IGuiFluidStackGroup stacks = recipeLayout.getFluidStacks();
        stacks.init(input1, true, 1, 1, 16, 34, 1, false, null);
        stacks.init(output1, false, 59, 1, 16, 34, 1, false, null);

        stacks.set(input1, ingredients.getInputs(VanillaTypes.FLUID).get(0));
        stacks.set(output1, ingredients.getOutputs(VanillaTypes.FLUID).get(0));

    }

}
