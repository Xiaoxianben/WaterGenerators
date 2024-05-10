package com.xiaoxianben.watergenerators.gui.guiContainer;

import com.xiaoxianben.watergenerators.rander.RenderFluid;
import com.xiaoxianben.watergenerators.util.ModInformation;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class GuiContainerBasic extends GuiContainer {

    protected final ResourceLocation TEXTURES;
    private final TileEntity tileEntity;


    public GuiContainerBasic(Container inventorySlotsIn, TileEntity tileEntity, int ID) {
        super(inventorySlotsIn);
        this.tileEntity = tileEntity;
        this.TEXTURES = new ResourceLocation(ModInformation.MOD_ID, "textures/gui/" + ID + ".png");
    }


    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.drawAllMouseRect(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.drawAllGUITextures();

        this.drawAllGUIText();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }


    /**
     * 绘制所有的鼠标矩形
     */
    protected abstract void drawAllMouseRect(int mouseX, int mouseY);

    /**
     * 绘制所有的文本
     * */
    protected void drawAllGUIText() {
        String text = this.tileEntity.getBlockType().getLocalizedName();
        drawGUIText(text, this.guiLeft + (this.xSize - this.fontRenderer.getStringWidth(text)) / 2, this.guiTop + 4);
    }

    /**
     * 绘制所有的矩形
     * */
    protected void drawAllGUITextures() {
        this.mc.getTextureManager().bindTexture(this.TEXTURES);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }


    protected void drawTexturedRectDownToUp(float newInt, float maxInt, int textureX, int windowX) {
        if (newInt > 0) {
            int height = (int) ((newInt / maxInt) * 72); // 计算矩形的高
            int RectY = this.guiTop + 72 + 4 - height; // 计算矩形顶部的位置
            int textureY = 72 - height; // 计算被截取的矩形的顶部的位置
            this.drawTexturedModalRect(this.guiLeft + windowX, RectY, textureX, textureY, 20, height);
        }
    }

    protected void drawFluid(FluidTank fluidTank, int startX, int startY, int wight, int height) {
        FluidStack fluidStack = fluidTank.getFluid();
        if (fluidStack == null || fluidStack.getFluid() == null) return;

        float fluidPercentage = (float) fluidTank.getFluidAmount() / fluidTank.getCapacity();
        int textureHighReal = (int) (fluidPercentage * height); // 计算 要绘制的矩形的 高
        int RectY = this.guiTop + startY + height - textureHighReal; // 计算 要绘制矩形的顶部的y值

        RenderFluid.renderGuiTank(fluidTank, this.guiLeft + startX, RectY, 100, wight, textureHighReal);
    }

    protected void drawMouseRect(int mouseX, int mouseY, int textureX, int textureY, int width, int height, String[] text) {
        if (mouseX > this.guiLeft + textureX && mouseX <= this.guiLeft + textureX + width &&
                mouseY > this.guiTop + textureY && mouseY <= this.guiTop + textureY + height
        ) {
            // 绘画图形的阴影
            drawRect(textureX, textureY,
                    textureX + width, textureY + height, 0x3FFFFFFF);
            // 绘画文字框
            drawHoveringText(Arrays.stream(text).collect(Collectors.toList()), mouseX - this.guiLeft, mouseY - this.guiTop);
        }
    }

    protected void drawGUIText(String text, int x, int y) {
        this.fontRenderer.drawString(text, x, y, 0x000000);
    }

}