package com.xiaoxianben.watergenerators.GUI;

import com.xiaoxianben.watergenerators.tileEntity.TECreateGenerator;
import net.minecraft.inventory.Container;

public class GuiContainerCreateGenerator extends GuiContainerBasic{

    public TECreateGenerator tileEntity;

    public GuiContainerCreateGenerator(Container Container, TECreateGenerator tileEntity) {
        super(Container, tileEntity, "创造发电机", 16, 1);
        this.tileEntity = tileEntity;
    }

    public void drawAllTexturedRectDownToUp() {
        this.drawTexturedRectDownToUp(this.tileEntity.getEnergyStored(), this.tileEntity.getMaxEnergyStored(), 175, 7);
    }
    public void drawAllMouseRect(int mouseX, int mouseY) {
        this.drawMouseRect(mouseX, mouseY, 7, 4, 20, 72, 1);
    }

    public void updateDrawStringList() {
        String[] strings = new String[3];
        strings[0] = "基础发电量："+this.tileEntity.getBasePowerGeneration();
        // strings[1] = "能量增/减："+this.tileEntity.getFinallyReceiveEnergySpecific()+"/-"+this.tileEntity.getFinallyExtractEnergySpecific();
        strings[1] = "能量存储："+this.tileEntity.getEnergyStored()+'/'+this.tileEntity.getMaxEnergyStored();
        this.setDrawStringList(strings);
    }

}
