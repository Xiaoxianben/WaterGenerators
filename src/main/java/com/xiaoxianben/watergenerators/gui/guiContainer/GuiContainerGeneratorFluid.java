package com.xiaoxianben.watergenerators.gui.guiContainer;

import com.xiaoxianben.watergenerators.gui.container.ContainerGeneratorFluid;
import com.xiaoxianben.watergenerators.math.PrivateMath;
import com.xiaoxianben.watergenerators.tileEntity.generator.TEGeneratorFluid;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fluids.FluidTank;

import java.util.ArrayList;

public class GuiContainerGeneratorFluid extends GuiContainerGeneratorBasic {

    private final TEGeneratorFluid tileEntity;

    public GuiContainerGeneratorFluid(ContainerGeneratorFluid Container, TEGeneratorFluid tileEntity) {
        super(Container, tileEntity, 2);
        this.tileEntity = tileEntity;
    }


    @Override
    public void drawAllGUITextures() {
        super.drawAllGUITextures();
        this.drawFluid(this.tileEntity.fluidTank, 74, 14, 16, 58);
    }

    @Override
    public void drawAllMouseRect(int mouseX, int mouseY) {
        super.drawAllMouseRect(mouseX, mouseY);
        this.drawMouseRect(mouseX, mouseY, 74, 14, 16, 58, 1);
    }

    @Override
    public void updateDrawStringList() {
        super.updateDrawStringList();

        ArrayList<String> strings = new ArrayList<>(this.drawStringList);
        strings.add(I18n.format("gui.fluidBV.text", PrivateMath.getRoughData((long) this.tileEntity.getFluidMagnification(), 1)));

        strings.clear();
        strings.addAll(this.MouseStringList);
        FluidTank fluidTank = this.tileEntity.fluidTank;
        strings.add(String.format("%s:\n%smB/%smB", fluidTank.getFluid() == null ? "null" : fluidTank.getFluid().getLocalizedName(), PrivateMath.getRoughData((long) this.tileEntity.getFluidAmount(), 1), PrivateMath.getRoughData((long) this.tileEntity.getCapacity(), 1)));
        this.setMouseStringList(strings);
    }

}