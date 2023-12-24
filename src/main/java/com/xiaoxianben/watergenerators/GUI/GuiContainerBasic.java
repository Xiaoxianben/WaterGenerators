package com.xiaoxianben.watergenerators.GUI;

import com.xiaoxianben.watergenerators.tileEntity.TEEnergyBasic;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import java.util.Objects;


public class GuiContainerBasic extends GuiContainer {

    private final TEEnergyBasic tileEntity;
    private final int tileEntityNameIndex;
    private final String tileEntityName;
    private final ResourceLocation TEXTURES;
    private String[] drawStringList = new String[6];

    public GuiContainerBasic(Container inventorySlotsIn,
                             TEEnergyBasic tileEntity, String tileEntityName, int tileEntityNameIndex, int ID) {
        super(inventorySlotsIn);
        this.tileEntity = tileEntity;
        this.tileEntityName = tileEntityName;
        this.tileEntityNameIndex = tileEntityNameIndex;
        this.TEXTURES = new ResourceLocation("watergenerators", "textures/gui/"+ID+".png");
    }

    public void setDrawStringList(String... drawStringList) {
        this.drawStringList = drawStringList;
    }

    public void drawTexturedRectDownToUp(float newInt, float maxInt, int textureX, int windowX) {
        if(newInt > 0) {
            float height = newInt / maxInt;
            int RectY = this.guiTop + 76 - (int)(height * 72); // 计算矩形顶部的位置
            int textureY = 72 - (int)(height * 72); // 计算被截取的矩形的顶部的位置
            this.drawTexturedModalRect(this.guiLeft + windowX, RectY, textureX, textureY, 20, (int)(height * 72));
        }
    }
    public void drawMouseRect(int mouseX, int mouseY, int textureX, int textureY, int width, int height,
                              int drawStringListIndex) {
        if(mouseX > this.guiLeft + textureX && mouseX < this.guiLeft + textureX + width &&
        mouseY > this.guiTop + textureY && mouseY < this.guiTop + textureY + height) {
            drawRect(this.guiLeft + textureX, this.guiTop + textureY,
                    this.guiLeft + textureX + width, this.guiTop + textureY + height, 0x3FFFFFFF);
            drawRect(mouseX + 5, mouseY - 1,
                    mouseX + 5 + (this.drawStringList[2].length() * 5), mouseY + 10,0x90000000);
            fontRenderer.drawString(this.drawStringList[drawStringListIndex],
                    mouseX + 5, mouseY, 0xFFFFFFFF);
        }
    }
    public void drawAllMouseRect(int mouseX, int mouseY) {}
    public void drawAllTexturedRectDownToUp() {}
    public void updateDrawStringList() {}

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.mc.getTextureManager().bindTexture(this.TEXTURES);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        this.drawAllTexturedRectDownToUp();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
        this.updateDrawStringList();
        // 绘制能量条等其他GUI元素
        int XString = this.guiLeft + 45;
        int tempNum = 1;
        String level = this.tileEntity.level;
        this.fontRenderer.drawString("名字："+tileEntityName+level+"级", XString, this.guiTop+9, 0x000000);
        for(String string : this.drawStringList) {
            this.fontRenderer.drawString(string, XString, this.guiTop+(9*++tempNum), 0x000000);
        }
        this.drawAllMouseRect(mouseX, mouseY);
    }

}
