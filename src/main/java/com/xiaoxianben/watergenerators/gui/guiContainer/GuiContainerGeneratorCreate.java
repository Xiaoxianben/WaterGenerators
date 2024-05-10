package com.xiaoxianben.watergenerators.gui.guiContainer;

import com.xiaoxianben.watergenerators.gui.container.ContainerGeneratorCreate;
import com.xiaoxianben.watergenerators.tileEntity.generator.TEGeneratorCreate;

public class GuiContainerGeneratorCreate extends GuiContainerGeneratorBasic {

    public TEGeneratorCreate tileEntity;

    public GuiContainerGeneratorCreate(ContainerGeneratorCreate Container, TEGeneratorCreate tileEntity) {
        super(Container, tileEntity, 1, 33, 14, 113);
        this.tileEntity = tileEntity;
    }
}