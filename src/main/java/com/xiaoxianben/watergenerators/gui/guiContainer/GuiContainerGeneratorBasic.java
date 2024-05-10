package com.xiaoxianben.watergenerators.gui.guiContainer;

import com.xiaoxianben.watergenerators.gui.container.ContainerBasic;
import com.xiaoxianben.watergenerators.math.PrivateMath;
import com.xiaoxianben.watergenerators.tileEntity.generator.TEGeneratorBase;
import net.minecraft.client.resources.I18n;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public abstract class GuiContainerGeneratorBasic extends GuiContainerBasic {

    private final TEGeneratorBase tileEntity;

    protected List<String> drawStringList = new ArrayList<>();
    protected List<String> MouseStringList = new ArrayList<>();
    protected int textStartX;
    protected int textStartY;
    protected int textWidth;


    public GuiContainerGeneratorBasic(ContainerBasic inventorySlotsIn,
                                      TEGeneratorBase tileEntity, int ID, int textStartX, int textStartY, int textWidth) {
        super(inventorySlotsIn, tileEntity, ID);
        this.tileEntity = tileEntity;

        this.textStartX = textStartX;
        this.textStartY = textStartY;
        this.textWidth = textWidth;
    }

    public GuiContainerGeneratorBasic(ContainerBasic inventorySlotsIn, TEGeneratorBase tileEntity, int ID) {
        this(inventorySlotsIn, tileEntity, ID, 98, 15, 69);
    }


    protected void drawAllMouseRect(int mouseX, int mouseY) {
        this.drawMouseRect(mouseX, mouseY, 7, 4, 20, 72, 0);
    }

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

    protected void drawAllGUITextures() {
        super.drawAllGUITextures();
        this.drawTexturedRectDownToUp(this.tileEntity.getEnergyStoredLong(), this.tileEntity.getMaxEnergyStoredLong(), 176, 7);

        for (int i = 0; i < ((ContainerBasic) this.inventorySlots).rectangles.size(); i++) {
            Rectangle rectangle = ((ContainerBasic) this.inventorySlots).rectangles.get(i);
            this.drawTexturedModalRect(this.guiLeft + rectangle.x, this.guiTop + rectangle.y, 7, 141, rectangle.width, rectangle.height);
        }
    }


    /**
     * 更新要绘制的字
     */
    public void updateDrawStringList() {
        ArrayList<String> strings = new ArrayList<>();

        strings.add(I18n.format("gui.basePowerGeneration.text", PrivateMath.getRoughData(this.tileEntity.getRealPowerGeneration(), 1)));
        strings.add(I18n.format("gui.energyIncreaseDecrease.text", PrivateMath.getRoughData(this.tileEntity.getFinallyReceiveEnergy(), 1), PrivateMath.getRoughData(this.tileEntity.getFinallyExtractEnergy(), 1)));
        this.setDrawStringList(strings);

        strings.clear();
        strings.add(String.format("FE:\n%sFE/%sFE", PrivateMath.getRoughData(this.tileEntity.getEnergyStoredLong(), 1), PrivateMath.getRoughData(this.tileEntity.getMaxEnergyStoredLong(), 1)));
        this.setMouseStringList(strings);
    }


    public void setDrawStringList(List<String> drawStringList) {
        this.drawStringList.clear();
        this.drawStringList.addAll(drawStringList);
    }

    public void setMouseStringList(List<String> MouseStringList) {
        this.MouseStringList.clear();
        this.MouseStringList.addAll(MouseStringList);
    }

    protected void drawMouseRect(int mouseX, int mouseY, int textureX, int textureY, int width, int height, int MouseStringListIndex) {
        super.drawMouseRect(mouseX, mouseY, textureX, textureY, width, height, this.MouseStringList.get(MouseStringListIndex).split("\n"));
    }


    public List<Rectangle> getGuiExtraAreas() {
        List<Rectangle> rectangles = new ArrayList<>();

        for (Rectangle rectangle : ((ContainerBasic) this.inventorySlots).rectangles) {
            rectangles.add(new Rectangle(this.guiLeft + rectangle.x, this.guiTop + rectangle.y, rectangle.width, rectangle.height));
        }

        return rectangles;
    }

}
