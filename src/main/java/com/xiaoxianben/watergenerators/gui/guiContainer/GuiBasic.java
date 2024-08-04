package com.xiaoxianben.watergenerators.gui.guiContainer;

import com.xiaoxianben.watergenerators.client.RenderFluid;
import com.xiaoxianben.watergenerators.gui.container.ContainerBasic;
import com.xiaoxianben.watergenerators.util.ModInformation;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class GuiBasic extends GuiContainer {

    protected final ResourceLocation TEXTURES;
    private final TileEntity tileEntity;


    public GuiBasic(ContainerBasic inventorySlotsIn, TileEntity tileEntity, int ID) {
        super(inventorySlotsIn);
        this.tileEntity = tileEntity;
        this.TEXTURES = new ResourceLocation(ModInformation.MOD_ID, "textures/gui/" + ID + ".png");
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.drawAllMouseRect(mouseX - this.guiLeft, mouseY - this.guiTop);

        for (GuiButton guibutton : this.buttonList) {
            if (guibutton.isMouseOver()) {
                guibutton.drawButtonForegroundLayer(mouseX - this.guiLeft, mouseY - this.guiTop);
                break;
            }
        }

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.mc.getTextureManager().bindTexture(this.TEXTURES);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        for (int i = 0; i < ((ContainerBasic) this.inventorySlots).rectangles.size(); i++) {
            Rectangle rectangle = ((ContainerBasic) this.inventorySlots).rectangles.get(i);
            this.drawTexturedModalRect(this.guiLeft + rectangle.x, this.guiTop + rectangle.y, 7, 141, rectangle.width, rectangle.height);
        }

        this.drawAllGUITextures();

        this.drawGuiBlockName();
        this.drawAllGUIText();
    }

    /**
     * 绘制方块的本地化名
     */
    protected void drawGuiBlockName() {
        String text = this.tileEntity.getBlockType().getLocalizedName();
        drawGUIText(text, this.guiLeft + (this.xSize - this.fontRenderer.getStringWidth(text)) / 2, this.guiTop + 3);
    }


    /**
     * 绘制所有的鼠标矩形
     */
    protected abstract void drawAllMouseRect(int mouseX, int mouseY);

    /**
     * 绘制所有的额外的文本
     */
    protected abstract void drawAllGUIText();

    /**
     * 绘制所有的额外的矩形
     */
    protected abstract void drawAllGUITextures();


    public java.util.List<Rectangle> getGuiExtraAreas() {
        List<Rectangle> rectangles = new ArrayList<>();

        for (Rectangle rectangle : ((ContainerBasic) this.inventorySlots).rectangles) {
            rectangles.add(new Rectangle(this.guiLeft + rectangle.x, this.guiTop + rectangle.y, rectangle.width, rectangle.height));
        }

        return rectangles;
    }


    protected void drawEnergyTexturedRect(float newInt, float maxInt) {
        if (newInt > 0) {
            int height = (int) ((newInt / maxInt) * 70); // 计算矩形的高
            int textureY = 70 - height; // 计算被截取的矩形的顶部的位置
            this.drawTexturedModalRect(this.guiLeft + 8, this.guiTop + 5 + textureY, 177, 1 + textureY, 18, height);
        }
    }

    protected void drawFluid(FluidTank fluidTank, int startX, int startY, int wight, int height) {
        FluidStack fluidStack = fluidTank.getFluid();
        if (fluidStack == null || fluidStack.getFluid() == null) return;

        int textureHighReal = (int) (((float) fluidTank.getFluidAmount()) / fluidTank.getCapacity() * height); // 计算 要绘制的矩形的 高
        int RectY = this.guiTop + startY + height - textureHighReal; // 计算 要绘制矩形的顶部的y值

        RenderFluid.renderGuiTank(fluidTank, this.guiLeft + startX, RectY, 100, wight, textureHighReal);
    }

    protected void drawMouseRect(int mouseX, int mouseY, int textureX, int textureY, int width, int height, String[] text) {
        if (mouseX > textureX && mouseX <= textureX + width &&
                mouseY > textureY && mouseY <= textureY + height
        ) {
            // 绘画图形的阴影
            drawRect(textureX, textureY,
                    textureX + width, textureY + height, 0x3000000);
            // 绘画文字框
            drawHoveringText(Arrays.stream(text).collect(Collectors.toList()), mouseX, mouseY);
        }
    }

    protected void drawGUIText(String text, int x, int y) {
        this.fontRenderer.drawString(text, x, y, 0x000000);
    }


}