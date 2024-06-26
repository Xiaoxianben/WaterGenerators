package com.xiaoxianben.watergenerators.gui.guiContainer;

import com.xiaoxianben.watergenerators.gui.container.ContainerGeneratorCreate;
import com.xiaoxianben.watergenerators.tileEntity.generator.TEGeneratorCreate;

public class GuiGeneratorCreate extends GuiGeneratorBasic {

    public TEGeneratorCreate tileEntity;

    public GuiGeneratorCreate(ContainerGeneratorCreate Container, TEGeneratorCreate tileEntity) {
        super(Container, tileEntity, 1, 33, 14, 113);
        this.tileEntity = tileEntity;
    }
}