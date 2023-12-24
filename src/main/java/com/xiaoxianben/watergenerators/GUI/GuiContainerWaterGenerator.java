package com.xiaoxianben.watergenerators.GUI;

import com.xiaoxianben.watergenerators.tileEntity.TEWaterGenerator;
import net.minecraft.inventory.Container;

public class GuiContainerWaterGenerator extends GuiContainerBasic {

    private final TEWaterGenerator tileEntity;

    public GuiContainerWaterGenerator(Container Container, TEWaterGenerator tileEntity) {
        super(Container, tileEntity, "水发电机",21, 2);
        this.tileEntity = tileEntity;
    }

    public void drawAllTexturedRectDownToUp() {
        this.drawTexturedRectDownToUp(this.tileEntity.getEnergyStored(), this.tileEntity.getMaxEnergyStored(), 175, 7);
        this.drawTexturedRectDownToUp(this.tileEntity.getFluidAmount(), this.tileEntity.getMaxFluidAmount(), 194, 149);
    }
    public void drawAllMouseRect(int mouseX, int mouseY) {
        this.drawMouseRect(mouseX, mouseY, 7, 4, 20, 72, 1);
        this.drawMouseRect(mouseX, mouseY, 149, 4, 20, 72, 2);
    }

    public void updateDrawStringList() {
        String[] strings = new String[4];
        strings[0] = "基础发电量："+this.tileEntity.getBasePowerGeneration();
        // strings[1] = "能量增/减："+this.tileEntity.getFinallyReceiveEnergySpecific()+"/-"+this.tileEntity.getFinallyExtractEnergySpecific();
        strings[1] = "能量存储："+this.tileEntity.getEnergyStored()+'/'+this.tileEntity.getMaxEnergyStored();
        strings[2] = "流体存储："+this.tileEntity.getFluidAmount()+'/'+this.tileEntity.getMaxFluidAmount();
        this.setDrawStringList(strings);
    }

}
