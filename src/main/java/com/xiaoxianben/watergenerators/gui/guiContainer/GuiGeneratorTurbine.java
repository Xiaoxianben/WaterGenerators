package com.xiaoxianben.watergenerators.gui.guiContainer;

import com.xiaoxianben.watergenerators.gui.container.ContainerGeneratorTurbine;
import com.xiaoxianben.watergenerators.tileEntity.generator.TEGeneratorTurbine;
import net.minecraft.client.resources.I18n;

public class GuiGeneratorTurbine extends GuiGeneratorBasic {

    public final TEGeneratorTurbine tileEntity;

    public GuiGeneratorTurbine(ContainerGeneratorTurbine Container) {
        super(Container, 1, 33, 14, 113);
        this.tileEntity = (TEGeneratorTurbine) super.tileEntity;
    }

    @Override
    public void updateInforGuiStringList() {
        super.updateInforGuiStringList();

        this.drawStringList.add(I18n.format("gui.fluidHeight.text", this.tileEntity.getLiquidHeight()));

    }

}
