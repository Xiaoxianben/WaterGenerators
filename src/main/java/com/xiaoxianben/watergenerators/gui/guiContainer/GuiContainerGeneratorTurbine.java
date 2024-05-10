package com.xiaoxianben.watergenerators.gui.guiContainer;

import com.xiaoxianben.watergenerators.gui.container.ContainerGeneratorTurbine;
import com.xiaoxianben.watergenerators.math.PrivateMath;
import com.xiaoxianben.watergenerators.tileEntity.generator.TEGeneratorTurbine;
import net.minecraft.client.resources.I18n;

import java.util.ArrayList;

public class GuiContainerGeneratorTurbine extends GuiContainerGeneratorBasic {

    public TEGeneratorTurbine tileEntity;

    public GuiContainerGeneratorTurbine(ContainerGeneratorTurbine Container, TEGeneratorTurbine tileEntity) {
        super(Container, tileEntity, 1, 33, 14, 113);
        this.tileEntity = tileEntity;
    }

    @Override
    public void updateDrawStringList() {
        super.updateDrawStringList();

        ArrayList<String> strings = new ArrayList<>(this.drawStringList);

        strings.add(I18n.format("gui.fluidHeight.text", this.tileEntity.getLiquidHeight()));
        this.setDrawStringList(strings);
    }

}
