package com.xiaoxianben.watergenerators.gui.guiContainer;

import com.xiaoxianben.watergenerators.gui.container.ContainerBasic;
import com.xiaoxianben.watergenerators.math.PrivateMath;
import com.xiaoxianben.watergenerators.tileEntity.TEEnergyBasic;

import java.awt.*;

public class GuiEnergyBase extends GuiBasic {


    private final TEEnergyBasic tileEntity;


    public GuiEnergyBase(ContainerBasic inventorySlotsIn, TEEnergyBasic tileEntity, int ID) {
        super(inventorySlotsIn, tileEntity, ID);


        this.tileEntity = tileEntity;
    }


    @Override
    protected void drawAllMouseRect(int mouseX, int mouseY) {
        this.drawMouseRect(mouseX, mouseY, 7, 4, 20, 72,
                String.format("FE:\n%sFE/%sFE",
                                PrivateMath.getRoughData(this.tileEntity.getEnergyStoredLong(), 1),
                                PrivateMath.getRoughData(this.tileEntity.getMaxEnergyStoredLong(), 1))
                        .split("\n"));
    }

    @Override
    protected void drawAllGUITextures() {
        super.drawAllGUITextures();
        this.drawEnergyTexturedRect(this.tileEntity.getEnergyStoredLong(), this.tileEntity.getMaxEnergyStoredLong());

        for (int i = 0; i < ((ContainerBasic) this.inventorySlots).rectangles.size(); i++) {
            Rectangle rectangle = ((ContainerBasic) this.inventorySlots).rectangles.get(i);
            this.drawTexturedModalRect(this.guiLeft + rectangle.x, this.guiTop + rectangle.y, 7, 141, rectangle.width, rectangle.height);
        }
    }

}
