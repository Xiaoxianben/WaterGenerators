package com.xiaoxianben.watergenerators.GUI;

import com.xiaoxianben.watergenerators.tileEntity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GUIHandler implements IGuiHandler {
    //对每个自定义GUI进行序号编码
    public static final int GUIWaterGenerator = 1;
    public static final int GUITurbineGenerator = 2;
    public static final int GUICreateGenerator = 3;
    public static final int GUISteamGenerator = 4;
    public static final int GUIMachineVa = 5;

    //在服务端中运行的逻辑
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        //通过编码创建服务端的Container
        switch (ID) {
            case GUIWaterGenerator:
                return new ContainerWaterGenerator(player);
            case GUITurbineGenerator:
                return new ContainerTurbineGenerator(player);
            case GUICreateGenerator:
                return new ContainerCreateGenerator(player);
            case GUISteamGenerator:
                return new ContainerSteamGenerator(player);
            case GUIMachineVa:
                return new ContainerMachineVa(player);
            default:
                return null;

        }
    }

    //在客户端中运行的逻辑
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        //通过编码创建客户端的Container与GuiContainer（Forge会自动托管服务端到客户端的Container同步）
        switch (ID) {
            case GUIWaterGenerator:
                TEGeneratorWater TE1 = (TEGeneratorWater) world.getTileEntity(pos);
                return new GuiContainerGeneratorWater(new ContainerWaterGenerator(player), TE1);
            case GUITurbineGenerator:
                TEGeneratorTurbine TE2 = (TEGeneratorTurbine) world.getTileEntity(pos);
                return new GuiContainerGeneratorTurbine(new ContainerTurbineGenerator(player), TE2);
            case GUICreateGenerator:
                TEGeneratorCreate TE3 = (TEGeneratorCreate) world.getTileEntity(pos);
                return new GuiContainerGeneratorCreate(new ContainerCreateGenerator(player), TE3);
            case GUISteamGenerator:
                TEGeneratorSteam TE5 = (TEGeneratorSteam) world.getTileEntity(pos);
                return new GuiContainerGeneratorSteam(new ContainerSteamGenerator(player), TE5);
            case GUIMachineVa:
                TEMachineVaporization TE4 = (TEMachineVaporization) world.getTileEntity(pos);
                return new GuiContainerMachineVa(new ContainerMachineVa(player), TE4);
            default:
                return null;
        }
    }
}
