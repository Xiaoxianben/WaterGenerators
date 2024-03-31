package com.xiaoxianben.watergenerators.GUI;

import com.xiaoxianben.watergenerators.tileEntity.TEGeneratorWater;
import net.minecraft.inventory.Container;

public class GuiContainerGeneratorWater extends GuiContainerGeneratorBasic {

    private final TEGeneratorWater tileEntity;

    public GuiContainerGeneratorWater(Container Container, TEGeneratorWater tileEntity) {
        super(Container, tileEntity, 2);
        this.tileEntity = tileEntity;
    }

    public void drawAllTexturedRectDownToUp() {
        this.drawTexturedRectDownToUp(this.tileEntity.getEnergyStoredLong(), this.tileEntity.getMaxEnergyStoredLong(), 175, 7);
        this.drawTexturedRectDownToUp(this.tileEntity.getFluidAmount(), this.tileEntity.getMaxFluidAmount(), 194, 149);
    }

    @Override
    public void drawAllString() {
        this.drawAllStringOld();
    }

    public void drawAllMouseRect(int mouseX, int mouseY) {
        this.drawMouseRect(mouseX, mouseY, 6, 4, 20, 72, 0);
        this.drawMouseRect(mouseX, mouseY, 149, 4, 20, 72, 1);
    }

    public void updateDrawStringList() {
        String[] strings = new String[4];
        strings[0] = "基础发电量：" + this.tileEntity.getBasePowerGeneration();
        // strings[1] = "能量增/减："+this.tileEntity.getFinallyReceiveEnergySpecific()+"/-"+this.tileEntity.getFinallyExtractEnergySpecific();
        String[] rfList = this.tileEntity.getRoughEnergyStored();
        String[] rfMaxList = this.tileEntity.getRoughMaxEnergyStored();
        strings[1] = "能量存储：" + (rfList[0] + rfList[2]) + '/' + (rfMaxList[0] + rfMaxList[2]);
        String[] fluidAmountList = this.tileEntity.getRoughFluidAmount();
        String[] fluidAmountMaxList = this.tileEntity.getRoughMaxFluidAmount();
        strings[2] = "流体存储：" + (fluidAmountList[0] + fluidAmountList[2]) + '/' + (fluidAmountMaxList[0] + fluidAmountMaxList[2]);
        this.setDrawStringList(strings);

        strings = new String[2];
        strings[0] = "能量存储：" + this.tileEntity.getEnergyStoredLong() + '/' + this.tileEntity.getMaxEnergyStoredLong();
        strings[1] = "流体存储：" + this.tileEntity.getFluidAmount() + '/' + this.tileEntity.getMaxFluidAmount();
        this.setMouseStringList(strings);
    }

}
