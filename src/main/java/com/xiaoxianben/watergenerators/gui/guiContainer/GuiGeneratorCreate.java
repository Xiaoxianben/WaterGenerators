package com.xiaoxianben.watergenerators.gui.guiContainer;

import com.xiaoxianben.watergenerators.gui.container.ContainerGeneratorCreate;
import com.xiaoxianben.watergenerators.tileEntity.generator.TEGeneratorCreate;

public class GuiGeneratorCreate extends GuiGeneratorBasic {

    public final TEGeneratorCreate tileEntity;

    public GuiGeneratorCreate(ContainerGeneratorCreate Container) {
        super(Container, 1, 33, 14, 113);
        this.tileEntity = (TEGeneratorCreate) super.tileEntity;
    }

}