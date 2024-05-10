package com.xiaoxianben.watergenerators.jei.advancedGuiHandlers;

import com.xiaoxianben.watergenerators.gui.guiContainer.GuiContainerGeneratorBasic;
import mezz.jei.api.gui.IAdvancedGuiHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;

public class GeneratorAdvancedGuiHandlers implements IAdvancedGuiHandler<GuiContainerGeneratorBasic> {

    @Nonnull
    @Override
    public Class<GuiContainerGeneratorBasic> getGuiContainerClass() {
        return GuiContainerGeneratorBasic.class;
    }

    @Nullable
    @Override
    public List<Rectangle> getGuiExtraAreas(@Nonnull GuiContainerGeneratorBasic guiContainer) {
        return guiContainer.getGuiExtraAreas();
    }

    @Nullable
    @Override
    public Object getIngredientUnderMouse(@Nonnull GuiContainerGeneratorBasic guiContainer, int mouseX, int mouseY) {
        return null;
    }
}
