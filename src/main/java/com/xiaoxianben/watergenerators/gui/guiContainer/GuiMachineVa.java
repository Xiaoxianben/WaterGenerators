package com.xiaoxianben.watergenerators.gui.guiContainer;

import com.xiaoxianben.watergenerators.gui.container.ContainerMachineVa;
import com.xiaoxianben.watergenerators.tileEntity.machine.TEMachineVaporization;
import net.minecraftforge.fluids.FluidTank;

import java.util.ArrayList;
import java.util.List;

public class GuiMachineVa extends GuiEnergyBase {

    private final TEMachineVaporization teMachineVaporization;


    public GuiMachineVa(ContainerMachineVa Container) {
        super(Container, 3);
        this.teMachineVaporization = (TEMachineVaporization) super.tileEntity;
    }


    @Override
    protected void drawAllMouseRect(int mouseX, int mouseY) {
        super.drawAllMouseRect(mouseX, mouseY);
        this.drawMouseFluidText(teMachineVaporization.getFluidTankInt(), 51, 14, mouseX, mouseY);
        this.drawMouseFluidText(teMachineVaporization.getFluidTankOut(), 109, 14, mouseX, mouseY);

        //this.drawMouseRect(mouseX, mouseY, 7, 4, 20, 72, new String[]{"FE: " + tileEntity.getEnergyStoredLong() + "FE / " + tileEntity.getMaxEnergyStoredLong() + "FE"});
    }


    protected void drawAllGUIText() {
    }

    @Override
    protected void drawAllGUITextures() {
        super.drawAllGUITextures();
        if (teMachineVaporization.open) {
            this.drawTexturedModalRect(this.guiLeft + 70, this.guiTop + 28, 196, 0, 36, 17);
        }

        this.drawFluid(teMachineVaporization.getFluidTankInt(), 51, 14, 16, 58);
        this.drawFluid(teMachineVaporization.getFluidTankOut(), 109, 14, 16, 58);

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
