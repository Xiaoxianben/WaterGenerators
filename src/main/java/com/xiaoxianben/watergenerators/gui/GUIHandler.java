package com.xiaoxianben.watergenerators.gui;

import com.xiaoxianben.watergenerators.gui.container.*;
import com.xiaoxianben.watergenerators.gui.guiContainer.*;
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
    public static final int GUIMachineConcentration = 5;

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
            case GUIMachineConcentration:
                return new ContainerMachineVa(player, tileEntity);
            default:
                return null;

        }
    }

    //在客户端中运行的逻辑
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        //通过编码创建客户端的Container与GuiContainer（Forge会自动托管服务端到客户端的Container同步）
        ContainerMa container = (ContainerMa) getServerGuiElement(ID, player, world, x, y, z);
        switch (ID) {
            case GUIFluidGenerator:
                return new GuiGeneratorFluid((ContainerGeneratorFluid) container);
            case GUITurbineGenerator:
                return new GuiGeneratorTurbine((ContainerGeneratorTurbine) container);
            case GUICreateGenerator:
                return new GuiGeneratorCreate((ContainerGeneratorCreate) container);
            case GUIMachineVa:
                return new GuiMachineVa((ContainerMachineVa) container);
            case GUIMachineConcentration:
                return new GuiMachineConcentration((ContainerMachineVa) container);
            default:
                return null;
        }
    }
}
