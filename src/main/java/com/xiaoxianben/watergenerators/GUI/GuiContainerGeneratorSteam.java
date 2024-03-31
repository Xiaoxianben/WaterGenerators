package com.xiaoxianben.watergenerators.GUI;

import com.xiaoxianben.watergenerators.tileEntity.TEGeneratorSteam;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraftforge.fluids.FluidTank;

public class GuiContainerGeneratorSteam extends GuiContainerGeneratorBasic {

    private final TEGeneratorSteam tileEntity;

    public GuiContainerGeneratorSteam(Container Container, TEGeneratorSteam tileEntity) {
        super(Container, tileEntity, 4);
        this.tileEntity = tileEntity;
    }

    @Override
    public void drawAllMouseRect(int mouseX, int mouseY) {
        this.drawMouseRect(mouseX, mouseY, 7, 4, 20, 72, new String[]{"FE: " + tileEntity.getEnergyStoredLong() + " / " + tileEntity.getMaxEnergyStoredLong() + " RF"});
        FluidTank steamTank = this.tileEntity.steamTank;
        this.drawMouseRect(mouseX, mouseY, 74, 14, 16, 58, new String[]{
                steamTank.getFluid() == null ? "null" : steamTank.getFluid().getLocalizedName() + ':', this.tileEntity.steamTank.getFluidAmount() + "/" + this.tileEntity.steamTank.getCapacity()});
    }

    @Override
    public void drawAllTexturedRectDownToUp() {
        this.drawTexturedRectDownToUp(this.tileEntity.getEnergyStoredLong(), this.tileEntity.getMaxEnergyStoredLong(), 176, 7);
        drawFluidRectDownToUp(tileEntity.steamTank, 74, 14, 16, 58);
    }

    @Override
    public void drawAllString() {
        this.drawStringList(I18n.format("gui.basePowerGeneration.text"), this.tileEntity.getBasePowerGeneration() + " FE/t", 98, 16);

        String[] roughFinallyReceiveEnergys = this.tileEntity.getRoughFinallyReceiveEnergy();
        String roughFinallyReceiveEnergy = roughFinallyReceiveEnergys[0] + ' ' + roughFinallyReceiveEnergys[2];

        this.drawStringList(I18n.format("gui.realPowerGeneration.text"), roughFinallyReceiveEnergy + "FE/t", 98, 32);
    }

    @Override
    public void updateDrawStringList() {

    }

    public void drawStringList(String text, String var, int x, int y) {
        this.fontRenderer.drawString(text, this.guiLeft + x, this.guiTop + y, 0x000000);
        this.fontRenderer.drawString("    "+ var, this.guiLeft + x, this.guiTop + y + 8, 0x000000);
    }
}
