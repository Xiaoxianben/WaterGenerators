package com.xiaoxianben.watergenerators.gui.guiContainer;

import com.xiaoxianben.watergenerators.tileEntity.TEMachineVaporization;
import net.minecraft.inventory.Container;
import net.minecraftforge.fluids.FluidTank;

import java.util.ArrayList;
import java.util.List;

public class GuiContainerMachineVa extends GuiContainerBasic {

    private final TEMachineVaporization tileEntity;


    public GuiContainerMachineVa(Container Container, TEMachineVaporization tileEntity) {
        super(Container, tileEntity, 3);
        this.tileEntity = tileEntity;
    }


    @Override
    protected void drawAllMouseRect(int mouseX, int mouseY) {
        this.drawMouseFluidText(tileEntity.getFluidTankInt(), 51, 14, mouseX, mouseY);
        this.drawMouseFluidText(tileEntity.getFluidTankOut(), 109, 14, mouseX, mouseY);

        this.drawMouseRect(mouseX, mouseY, 7, 4, 20, 72, new String[]{"FE: " + tileEntity.getEnergyStoredLong() + "FE / " + tileEntity.getMaxEnergyStoredLong() + "FE"});
    }


    protected void drawAllGUIText() {
        super.drawAllGUITextures();
    }

    protected void drawAllGUITextures() {
        super.drawAllGUITextures();
        if (tileEntity.open) {
            this.drawTexturedModalRect(this.guiLeft + 70, this.guiTop + 28, 196, 0, 36, 17);
        }
        this.drawTexturedRectDownToUp(this.tileEntity.getEnergyStoredLong(), this.tileEntity.getMaxEnergyStoredLong(), 176, 7);

        this.drawFluid(tileEntity.getFluidTankInt(), 51, 14, 16, 58);
        this.drawFluid(tileEntity.getFluidTankOut(), 109, 14, 16, 58);

    }


    public void drawMouseFluidText(FluidTank fluidTank, int startX, int startY, int mouseX, int mouseY) {
        int textureWidth = 16;
        int textureHigh = 58;
        // 绘画文字框
        List<String> text = new ArrayList<>();
        text.add(fluidTank.getFluid() == null ? "null" : fluidTank.getFluid().getLocalizedName() + ": ");
        text.add(fluidTank.getFluidAmount() + "/" + fluidTank.getCapacity());

        this.drawMouseRect(mouseX, mouseY, startX, startY, textureWidth, textureHigh, text.toArray(new String[0]));
    }

}
