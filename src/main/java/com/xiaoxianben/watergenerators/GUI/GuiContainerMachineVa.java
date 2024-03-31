package com.xiaoxianben.watergenerators.GUI;

import com.xiaoxianben.watergenerators.tileEntity.TEMachineVaporization;
import com.xiaoxianben.watergenerators.util.ModInformation;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.xiaoxianben.watergenerators.rander.renderFluid.fluidTextures;

public class GuiContainerMachineVa extends GuiContainer {

    private final ResourceLocation TEXTURES;
    private final TEMachineVaporization tileEntity;

    public GuiContainerMachineVa(Container Container, TEMachineVaporization tileEntity) {
        super(Container);
        this.tileEntity = tileEntity;
        this.TEXTURES = new ResourceLocation(ModInformation.MOD_ID, "textures/gui/3.png");
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.mc.getTextureManager().bindTexture(this.TEXTURES);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        if (tileEntity.open) {
            this.drawTexturedModalRect(this.guiLeft + 70, this.guiTop + 28, 196, 0, 36, 17);
        }
        if (tileEntity.getEnergyStored() > 0) {
            this.drawTexturedRectDownToUp(this.tileEntity.getEnergyStoredLong(), this.tileEntity.getMaxEnergyStoredLong(), 176, 7);
        }

        FluidTank fluidTankOut = tileEntity.getFluidTankOut();
        FluidTank fluidTankInt = tileEntity.getFluidTankInt();

        this.drawFluid(fluidTankInt, 51, 14);
        this.drawFluid(fluidTankOut, 109, 14);


    }

    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);

        String text = this.tileEntity.getBlockType().getLocalizedName();
        drawGUIText(text, this.guiLeft + (this.xSize - this.fontRenderer.getStringWidth(text)) / 2, this.guiTop + 4);

        this.drawFluidText(tileEntity.getFluidTankInt(), 51, 12, mouseX, mouseY);
        this.drawFluidText(tileEntity.getFluidTankOut(), 109, 12, mouseX, mouseY);

        this.drawText(mouseX, mouseY, 7, 5, 20, 72, new String[]{"FE: " + tileEntity.getEnergyStored() + " / " + tileEntity.getMaxEnergyStored() + " RF"});

    }

    protected void drawFluid(FluidTank fluidTank, int startX, int startY) {
        FluidStack fluidStack = fluidTank.getFluid();
        if (fluidStack == null || fluidStack.getFluid() == null) return;
        ResourceLocation still = fluidStack.getFluid().getStill();

        TextureAtlasSprite textureAtlasSprite = fluidTextures.getTextureExtry(still.toString());

        int textureWidth = 16;
        int textureHigh = 58;

        if (textureAtlasSprite != null) {
            ResourceLocation texture = new ResourceLocation(still.getResourceDomain(), "textures/" + still.getResourcePath() + ".png");
            this.mc.getTextureManager().bindTexture(texture);

            float fluidPercentage = (float) fluidTank.getFluidAmount() / fluidTank.getCapacity();
            int textureHighReal = (int) (fluidPercentage * textureHigh); // 计算 要绘制的矩形的 高
            int RectY = this.guiTop + startY + textureHigh - textureHighReal; // 计算 要绘制矩形的顶部的y值

            this.drawTexturedModalRect(this.guiLeft + startX, RectY, textureAtlasSprite, textureWidth, textureHighReal);
        }

    }

    public void drawTexturedRectDownToUp(float newInt, float maxInt, int textureX, int windowX) {
        if (newInt > 0) {
            float height = newInt / maxInt;
            int RectY = this.guiTop + 4 + 72 - (int) (height * 72); // 计算矩形顶部的位置
            int textureY = 72 - (int) (height * 72); // 计算被截取的矩形的顶部的位置
            this.drawTexturedModalRect(this.guiLeft + windowX, RectY, textureX, textureY, 20, (int) (height * 72));
        }
    }

    public void drawGUIText(String text, int x, int y) {
        this.fontRenderer.drawString(text, x, y, 0x000000);
    }

    public void drawFluidText(FluidTank fluidTank, int startX, int startY, int mouseX, int mouseY) {
        int textureWidth = 16;
        int textureHigh = 60;

        if (this.guiLeft + startX < mouseX && mouseX <= this.guiLeft + startX + textureWidth &&
                this.guiTop + startY < mouseY && mouseY <= this.guiTop + startY + textureHigh) {

            float fluidPercentage = (float) fluidTank.getFluidAmount() / fluidTank.getCapacity();

            int textureHighReal = (int) (fluidPercentage * textureHigh); // 计算 要绘制的矩形的 高
            int RectY = this.guiTop + startY + textureHigh - textureHighReal; // 计算 要绘制矩形的顶部的y值

            drawRect(this.guiLeft + startX, RectY,
                    this.guiLeft + startX + textureWidth, this.guiTop + startY + textureHigh, 0x3FFFFFFF);
            // 绘画文字框
            List<String> text = new ArrayList<>();
            text.add(fluidTank.getFluid() == null ? "null" : fluidTank.getFluid().getLocalizedName() + ": ");
            text.add(fluidTank.getFluidAmount() + "/" + fluidTank.getCapacity());
            drawHoveringText(text, mouseX, mouseY);
        }

    }

    public void drawText(int mouseX, int mouseY, int startX, int startY, int width, int height, String[] text) {
        if (this.guiLeft + startX < mouseX && mouseX <= this.guiLeft + startX + width &&
                this.guiTop + startY < mouseY && mouseY <= this.guiTop + startY + height) {
            // 绘画图形的阴影
            drawRect(this.guiLeft + startX, this.guiTop + startY,
                    this.guiLeft + startX + width, this.guiTop + startY + height, 0x3FFFFFFF);

            // 绘画文字框
            List<String> text1 = Arrays.stream(text).collect(Collectors.toList());
            drawHoveringText(text1, mouseX, mouseY);
        }
    }

}
