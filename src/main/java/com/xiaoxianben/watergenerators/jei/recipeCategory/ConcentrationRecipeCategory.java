package com.xiaoxianben.watergenerators.jei.recipeCategory;

import com.xiaoxianben.watergenerators.WaterGenerators;
import mezz.jei.api.IGuiHelper;

import javax.annotation.Nonnull;

public class ConcentrationRecipeCategory extends VaporizationRecipeCategory {

    public final static String concentrationUID = WaterGenerators.MOD_ID + ":concentration";

    public ConcentrationRecipeCategory(IGuiHelper guiHelper) {
        super("concentration", guiHelper);
    }

    @Nonnull
    @Override
    public String getUid() {
        return concentrationUID;
    }

}
