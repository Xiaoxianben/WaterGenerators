package com.xiaoxianben.watergenerators.GUI;

import com.xiaoxianben.watergenerators.tileEntity.*;
import com.xiaoxianben.watergenerators.tileEntity.TETurbineGenerator;
import com.xiaoxianben.watergenerators.tileEntity.TEWaterGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GUIHandler implements IGuiHandler {
    //对每个自定义GUI进行序号编码
    public static final int GUIWaterGenerator = 1;
    public static final int GUITurbineGenerator = 2;
    public static final int GUICreateGenerator = 3;

    //在服务端中运行的逻辑
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        //通过编码创建服务端的Container
        switch (ID){
            case GUIWaterGenerator:
                return new ContainerWaterGenerator(player);
            case GUITurbineGenerator:
                return new ContainerTurbineGenerator(player);
            case GUICreateGenerator:
                return new ContainerCreateGenerator(player);
            default:
                return null;

        }
    }

    //在客户端中运行的逻辑
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        //通过编码创建客户端的Container与GuiContainer（Forge会自动托管服务端到客户端的Container同步）
        switch (ID){
            case GUIWaterGenerator:
                TEWaterGenerator TE1 = (TEWaterGenerator) world.getTileEntity(pos);
                return new GuiContainerWaterGenerator(new ContainerWaterGenerator(player), TE1);
            case GUITurbineGenerator:
                TETurbineGenerator TE2 = (TETurbineGenerator) world.getTileEntity(pos);
                return new GuiContainerTurbineGenerator(new ContainerTurbineGenerator(player), TE2);
            case GUICreateGenerator:
                TECreateGenerator TE3 = (TECreateGenerator) world.getTileEntity(pos);
                return new GuiContainerCreateGenerator(new ContainerCreateGenerator(player), TE3);
            default:
                return null;
        }
    }
}
