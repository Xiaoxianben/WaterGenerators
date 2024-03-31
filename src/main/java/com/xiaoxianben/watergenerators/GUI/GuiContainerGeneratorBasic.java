package com.xiaoxianben.watergenerators.GUI;

import com.xiaoxianben.watergenerators.tileEntity.TEGeneratorBase;
import com.xiaoxianben.watergenerators.util.ModInformation;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import java.util.Arrays;
import java.util.stream.Collectors;

import static com.xiaoxianben.watergenerators.rander.renderFluid.fluidTextures;


public abstract class GuiContainerGeneratorBasic extends GuiContainer {

    private final TEGeneratorBase tileEntity;
    private final ResourceLocation TEXTURES;
    protected String[] drawStringList = new String[6];
    private String[] MouseStringList = new String[2];

    public GuiContainerGeneratorBasic(Container inventorySlotsIn,
                                      TEGeneratorBase tileEntity, int ID) {
        super(inventorySlotsIn);
        this.tileEntity = tileEntity;
        this.TEXTURES = new ResourceLocation(ModInformation.MOD_ID, "textures/gui/" + ID + ".png");
    }

    public void setDrawStringList(String... drawStringList) {
        this.drawStringList = drawStringList;
    }

    public void setMouseStringList(String... MouseStringList) {
        this.MouseStringList = MouseStringList;
    }

    public void drawTexturedRectDownToUp(float newInt, float maxInt, int textureX, int windowX) {
        if (newInt > 0) {
            float height = newInt / maxInt;
            int RectY = this.guiTop + 76 - (int) (height * 72); // 计算矩形顶部的位置
            int textureY = 72 - (int) (height * 72); // 计算被截取的矩形的顶部的位置
            this.drawTexturedModalRect(this.guiLeft + windowX, RectY, textureX, textureY, 20, (int) (height * 72));
        }
    }

    public void drawFluidRectDownToUp(FluidTank fluidTank, int startX, int startY, int textureWidth, int textureHigh) {
        FluidStack fluidStack = fluidTank.getFluid();
        if (fluidStack == null || fluidStack.getFluid() == null) return;
        ResourceLocation still = fluidStack.getFluid().getStill();

        TextureAtlasSprite textureAtlasSprite = fluidTextures.getTextureExtry(still.toString());


        if (textureAtlasSprite != null) {
            ResourceLocation texture = new ResourceLocation(still.getResourceDomain(), "textures/" + still.getResourcePath() + ".png");
            this.mc.getTextureManager().bindTexture(texture);

            float fluidPercentage = (float) fluidTank.getFluidAmount() / fluidTank.getCapacity();
            int textureHighReal = (int) (fluidPercentage * textureHigh); // 计算 要绘制的矩形的 高
            textureHighReal = textureHighReal == 0 ? 1 : textureHighReal; // 防止 0 出现
            int RectY = this.guiTop + startY + textureHigh - textureHighReal; // 计算 要绘制矩形的顶部的y值

            this.drawTexturedModalRect(this.guiLeft + startX, RectY, textureAtlasSprite, textureWidth, textureHighReal);
        }

    }

    public void drawMouseRect(int mouseX, int mouseY, int textureX, int textureY, int width, int height,
                              int MouseStringListIndex) {
        drawMouseRect(mouseX, mouseY, textureX, textureY, width, height, new String[]{this.MouseStringList[MouseStringListIndex]});
    }

    public void drawMouseRect(int mouseX, int mouseY, int textureX, int textureY, int width, int height, String[] text) {
        if (mouseX > this.guiLeft + textureX && mouseX <= this.guiLeft + textureX + width &&
                mouseY > this.guiTop + textureY && mouseY <= this.guiTop + textureY + height) {
            // 绘画图形的阴影
            drawRect(this.guiLeft + textureX, this.guiTop + textureY,
                    this.guiLeft + textureX + width, this.guiTop + textureY + height, 0x3FFFFFFF);
            // 绘画文字框
            drawHoveringText(Arrays.stream(text).collect(Collectors.toList()), mouseX, mouseY);
        }
    }

    public void drawAllStringOld() {
        int XString = this.guiLeft + 34;
        int tempNum = 0;

        for (String string : this.drawStringList) {
            this.fontRenderer.drawString(string, XString, this.guiTop + 15 + (8 * tempNum++), 0x000000);
        }

    }

    /**
     * 绘制所有的鼠标矩形
     */
    public abstract void drawAllMouseRect(int mouseX, int mouseY);

    /**
     * 绘制所有矩形
     */
    public abstract void drawAllTexturedRectDownToUp();

    /**
     * 绘制所有的字
     */
    public abstract void drawAllString();


    /**
     * 更新要绘制的字
     */
    public abstract void updateDrawStringList();

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
        String TEName = this.tileEntity.getBlockType().getLocalizedName();
        this.fontRenderer.drawString(TEName, this.guiLeft + ((this.xSize - this.fontRenderer.getStringWidth(TEName)) / 2), this.guiTop + 3, 0x000000);

        this.drawAllString();
        this.drawAllMouseRect(mouseX, mouseY);
    }

}
