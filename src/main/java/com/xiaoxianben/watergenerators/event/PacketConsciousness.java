package com.xiaoxianben.watergenerators.event;

import com.xiaoxianben.watergenerators.tileEntity.TEEnergyBasic;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketConsciousness implements IMessage {
    public NBTTagCompound compound;

    // 默认构造函数（必需）
    public PacketConsciousness() {
    }

    public PacketConsciousness(NBTTagCompound compound) {
        this.compound = compound;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        compound = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, compound);
    }

    public static class Handler implements IMessageHandler<PacketConsciousness, IMessage> {
        @Override
        public IMessage onMessage(PacketConsciousness message, MessageContext ctx) {
            //判断是否为客户端（接收端）
            if (ctx.side == Side.CLIENT) {
                //获取接受数据中"consciousness"这一NBT标识
                final NBTTagCompound capabilityNBT = message.compound.getCompoundTag("capability");
                final NBTTagCompound updateNBT = message.compound.getCompoundTag("readNetworkUpdateNBT");
                final NBTTagCompound blockPosNBT = message.compound.getCompoundTag("blockPos");
                Minecraft.getMinecraft().addScheduledTask(() -> {
                    //获取客户端（接收端）玩家对象
                    EntityPlayer player = Minecraft.getMinecraft().player;
                    BlockPos blockPos = new BlockPos(blockPosNBT.getInteger("x"), blockPosNBT.getInteger("y"), blockPosNBT.getInteger("z"));
                    TEEnergyBasic tileEntity = (TEEnergyBasic) player.getEntityWorld().getTileEntity(blockPos);
                    if (tileEntity != null) {
                        tileEntity.readCapabilityNBT(capabilityNBT);
                        tileEntity.readNetworkUpdateNBT(updateNBT);
                    }
                });
            }
            return null;
        }
    }
}
