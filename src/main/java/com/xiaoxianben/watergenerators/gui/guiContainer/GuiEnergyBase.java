package com.xiaoxianben.watergenerators.gui.guiContainer;

import com.xiaoxianben.watergenerators.gui.container.ContainerBasic;
import com.xiaoxianben.watergenerators.math.PrivateMath;
import com.xiaoxianben.watergenerators.tileEntity.TEEnergyBasic;

public abstract class GuiEnergyBase extends GuiBasic {


    private final TEEnergyBasic tileEntity;


    public GuiEnergyBase(ContainerBasic inventorySlotsIn, TEEnergyBasic tileEntity, int ID) {
        super(inventorySlotsIn, tileEntity, ID);


        this.tileEntity = tileEntity;
    }


    @Override
    protected void drawAllMouseRect(int mouseX, int mouseY) {
        this.drawMouseRect(mouseX, mouseY, 7, 4, 20, 72,
                String.format("FE:\n%sFE/%sFE",
                                PrivateMath.getRoughData(this.tileEntity.getEnergyStoredLong()),
                                PrivateMath.getRoughData(this.tileEntity.getMaxEnergyStoredLong()))
                        .split("\n"));
    }

    @Override
    protected void drawAllGUITextures() {
        this.drawEnergyTexturedRect(this.tileEntity.getEnergyStoredLong(), this.tileEntity.getMaxEnergyStoredLong());
    }

}
