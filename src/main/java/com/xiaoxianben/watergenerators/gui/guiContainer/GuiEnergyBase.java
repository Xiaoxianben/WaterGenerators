package com.xiaoxianben.watergenerators.gui.guiContainer;

import com.xiaoxianben.watergenerators.gui.container.ContainerMa;
import com.xiaoxianben.watergenerators.math.PrivateMath;
import com.xiaoxianben.watergenerators.tileEntity.TEEnergyBasic;

public abstract class GuiEnergyBase extends GuiBasic {


    private final TEEnergyBasic teEnergyBasic;


    public GuiEnergyBase(ContainerMa inventorySlotsIn, int ID) {
        super(inventorySlotsIn, ID);

        this.teEnergyBasic = (TEEnergyBasic) tileEntity;
    }


    @Override
    protected void drawAllMouseRect(int mouseX, int mouseY) {
        this.drawMouseRect(mouseX, mouseY, 7, 4, 20, 72,
                String.format("FE:\n%sFE/%sFE",
                                PrivateMath.getRoughData(this.teEnergyBasic.getEnergyStoredLong()),
                                PrivateMath.getRoughData(this.teEnergyBasic.getMaxEnergyStoredLong()))
                        .split("\n"));
    }

    @Override
    protected void drawAllGUITextures() {
        this.drawEnergyTexturedRect(this.teEnergyBasic.getEnergyStoredLong(), this.teEnergyBasic.getMaxEnergyStoredLong());
    }

}
