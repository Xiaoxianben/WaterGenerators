package com.xiaoxianben.watergenerators.GUI;

import com.xiaoxianben.watergenerators.tileEntity.TEGeneratorTurbine;
import net.minecraft.inventory.Container;

public class GuiContainerGeneratorTurbine extends GuiContainerGeneratorBasic {

    public TEGeneratorTurbine tileEntity;

    public GuiContainerGeneratorTurbine(Container Container, TEGeneratorTurbine tileEntity) {
        super(Container, tileEntity, 1);
        this.tileEntity = tileEntity;
    }

    public void drawAllTexturedRectDownToUp() {
        this.drawTexturedRectDownToUp(this.tileEntity.getEnergyStoredLong(), this.tileEntity.getMaxEnergyStoredLong(), 175, 7);
    }

    @Override
    public void drawAllString() {
        this.drawAllStringOld();
    }

    public void drawAllMouseRect(int mouseX, int mouseY) {
        this.drawMouseRect(mouseX, mouseY, 7, 4, 20, 72, 0);
    }

    public void updateDrawStringList() {
        String[] strings = new String[3];
        strings[0] = "基础发电量：" + this.tileEntity.getBasePowerGeneration();
        // strings[1] = "能量增/减："+this.tileEntity.getFinallyReceiveEnergySpecific()+"/-"+this.tileEntity.getFinallyExtractEnergySpecific();
        String[] rfList = this.tileEntity.getRoughEnergyStored();
        String[] rfMaxList = this.tileEntity.getRoughMaxEnergyStored();
        strings[1] = "能量存储：" + (rfList[0] + rfList[2]) + '/' + (rfMaxList[0] + rfMaxList[2]);
        this.setDrawStringList(strings);

        strings = new String[1];
        strings[0] = "能量存储：" + this.tileEntity.getEnergyStoredLong() + '/' + this.tileEntity.getMaxEnergyStoredLong();
        this.setMouseStringList(strings);
    }

}
