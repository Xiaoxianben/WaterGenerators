package com.xiaoxianben.watergenerators.jei.advancedGuiHandlers;

import com.xiaoxianben.watergenerators.gui.guiContainer.GuiBasic;
import mezz.jei.api.gui.IAdvancedGuiHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;

public class GeneratorAdvancedGuiHandlers implements IAdvancedGuiHandler<GuiBasic> {

    @Nonnull
    @Override
    public Class<GuiBasic> getGuiContainerClass() {
        return GuiBasic.class;
    }

    @Nullable
    @Override
    public List<Rectangle> getGuiExtraAreas(@Nonnull GuiBasic guiContainer) {
        return guiContainer.getGuiExtraAreas();
    }

    @Nullable
    @Override
    public Object getIngredientUnderMouse(@Nonnull GuiBasic guiContainer, int mouseX, int mouseY) {
        return null;
    }
}
