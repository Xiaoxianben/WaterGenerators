package com.xiaoxianben.watergenerators.gui.guiContainer;

import com.xiaoxianben.watergenerators.gui.container.ContainerGeneratorFluid;
import com.xiaoxianben.watergenerators.math.PrivateMath;
import com.xiaoxianben.watergenerators.tileEntity.generator.TEGeneratorFluid;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fluids.FluidTank;

public class GuiGeneratorFluid extends GuiGeneratorBasic {

    private final TEGeneratorFluid tileEntity;

    public GuiGeneratorFluid(ContainerGeneratorFluid Container, TEGeneratorFluid tileEntity) {
        super(Container, tileEntity, 2, 98, 15, 69);
        this.tileEntity = tileEntity;
    }


    @Override
    public void drawDefaultGUITextures() {
        super.drawDefaultGUITextures();
        this.drawFluid(this.tileEntity.fluidTank, 74, 14, 16, 58);
    }

    @Override
    public void drawDefaultMouseRect(int mouseX, int mouseY) {
        super.drawDefaultMouseRect(mouseX, mouseY);

        FluidTank fluidTank = this.tileEntity.fluidTank;
        this.drawMouseRect(mouseX, mouseY, 74, 14, 16, 58,
                String.format("%s:\n%smB/%smB", fluidTank.getFluid() == null ? "null" : fluidTank.getFluid().getLocalizedName(), PrivateMath.getRoughData((long) this.tileEntity.getFluidAmount()), PrivateMath.getRoughData((long) this.tileEntity.getCapacity()))
                        .split("\n"));
    }

    @Override
    public void updateDrawStringList() {
        super.updateDrawStringList();

        this.drawStringList.add(I18n.format("gui.fluidMagnification.text", PrivateMath.getRoughData(this.tileEntity.getEnergyMagnification())));
        this.drawStringList.add(I18n.format("gui.baseFluidAmount.text", PrivateMath.getRoughData(this.tileEntity.basicAmountOfFluidToProduceEnergy)));

    }

}