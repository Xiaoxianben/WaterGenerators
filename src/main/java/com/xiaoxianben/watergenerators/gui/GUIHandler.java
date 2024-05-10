package com.xiaoxianben.watergenerators.gui;

import com.xiaoxianben.watergenerators.gui.container.ContainerGeneratorCreate;
import com.xiaoxianben.watergenerators.gui.container.ContainerGeneratorFluid;
import com.xiaoxianben.watergenerators.gui.container.ContainerGeneratorTurbine;
import com.xiaoxianben.watergenerators.gui.container.ContainerMachineVa;
import com.xiaoxianben.watergenerators.gui.guiContainer.GuiContainerGeneratorCreate;
import com.xiaoxianben.watergenerators.gui.guiContainer.GuiContainerGeneratorFluid;
import com.xiaoxianben.watergenerators.gui.guiContainer.GuiContainerGeneratorTurbine;
import com.xiaoxianben.watergenerators.gui.guiContainer.GuiContainerMachineVa;
import com.xiaoxianben.watergenerators.tileEntity.TEMachineVaporization;
import com.xiaoxianben.watergenerators.tileEntity.generator.TEGeneratorCreate;
import com.xiaoxianben.watergenerators.tileEntity.generator.TEGeneratorFluid;
import com.xiaoxianben.watergenerators.tileEntity.generator.TEGeneratorTurbine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GUIHandler implements IGuiHandler {
    //对每个自定义GUI进行序号编码
    public static final int GUIFluidGenerator = 1;
    public static final int GUITurbineGenerator = 2;
    public static final int GUICreateGenerator = 3;
    public static final int GUIMachineVa = 4;

    //在服务端中运行的逻辑
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        // 通过编码创建服务端的Container
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
        switch (ID) {
            case GUIFluidGenerator:
                return new ContainerGeneratorFluid(player, tileEntity);
            case GUITurbineGenerator:
                return new ContainerGeneratorTurbine(player, tileEntity);
            case GUICreateGenerator:
                return new ContainerGeneratorCreate(player, tileEntity);
            case GUIMachineVa:
                return new ContainerMachineVa(player, tileEntity);
            default:
                return null;

        }
    }

    //在客户端中运行的逻辑
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        //通过编码创建客户端的Container与GuiContainer（Forge会自动托管服务端到客户端的Container同步）
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
        switch (ID) {
            case GUIFluidGenerator:
                return new GuiContainerGeneratorFluid(new ContainerGeneratorFluid(player, tileEntity), (TEGeneratorFluid) tileEntity);
            case GUITurbineGenerator:
                return new GuiContainerGeneratorTurbine(new ContainerGeneratorTurbine(player, tileEntity), (TEGeneratorTurbine) tileEntity);
            case GUICreateGenerator:
                return new GuiContainerGeneratorCreate(new ContainerGeneratorCreate(player, tileEntity), (TEGeneratorCreate) tileEntity);
            case GUIMachineVa:
                return new GuiContainerMachineVa(new ContainerMachineVa(player, tileEntity), (TEMachineVaporization) tileEntity);
            default:
                return null;
        }
    }
}
