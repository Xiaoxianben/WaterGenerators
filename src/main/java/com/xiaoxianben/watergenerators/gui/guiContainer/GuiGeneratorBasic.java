package com.xiaoxianben.watergenerators.gui.guiContainer;

import com.xiaoxianben.watergenerators.gui.container.ContainerBasic;
import com.xiaoxianben.watergenerators.math.PrivateMath;
import com.xiaoxianben.watergenerators.tileEntity.generator.TEGeneratorBase;
import net.minecraft.client.resources.I18n;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class GuiGeneratorBasic extends GuiEnergyBase {

    private final TEGeneratorBase tileEntity;

    protected List<String> drawStringList = new ArrayList<>();
    protected int textStartX;
    protected int textStartY;
    protected int textWidth;


    public GuiGeneratorBasic(ContainerBasic inventorySlotsIn,
                             TEGeneratorBase tileEntity, int ID, int textStartX, int textStartY, int textWidth) {
        super(inventorySlotsIn, tileEntity, ID);
        this.tileEntity = tileEntity;

        this.textStartX = textStartX;
        this.textStartY = textStartY;
        this.textWidth = textWidth;
    }


    @Override
    protected void drawAllGUIText() {
        super.drawAllGUIText();

        this.updateDrawStringList();

        int XString = this.guiLeft + this.textStartX;
        StringBuilder s = new StringBuilder();

        for (String string : this.drawStringList) {
            s.append(string).append("\n");
        }
        s.delete(s.length() - 1, s.length());
        this.fontRenderer.drawSplitString(s.toString(), XString, this.guiTop + this.textStartY, this.textWidth, 0x000000);
    }

    @Override
    protected void drawAllGUITextures() {
        super.drawAllGUITextures();

        for (int i = 0; i < ((ContainerBasic) this.inventorySlots).rectangles.size(); i++) {
            Rectangle rectangle = ((ContainerBasic) this.inventorySlots).rectangles.get(i);
            this.drawTexturedModalRect(this.guiLeft + rectangle.x, this.guiTop + rectangle.y, 7, 141, rectangle.width, rectangle.height);
        }
    }

    protected void updateDrawStringList() {
        this.drawStringList.clear();
        this.drawStringList.add(I18n.format("gui.basePowerGeneration.text", PrivateMath.getRoughData(this.tileEntity.getRealPowerGeneration(), 1)));
        this.drawStringList.add(I18n.format("gui.energyIncreaseDecrease.text", PrivateMath.getRoughData(this.tileEntity.getFinallyReceiveEnergy(), 1), PrivateMath.getRoughData(this.tileEntity.getFinallyExtractEnergy(), 1)));
    }

}
