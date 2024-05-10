package com.xiaoxianben.watergenerators.api;

import net.minecraft.nbt.NBTTagCompound;

public interface ICapabilityNBT {
    /**
     * 获取 此TileEntity的能力NBT数据
     * <p>用于在TileEntity<tt>网络更新</tt>和<tt>NBT读取</tt></p>
     */
    NBTTagCompound getCapabilityNBT();

    /**
     * 读取 此TileEntity的能力NBT数据
     * <p>用于在TileEntity<tt>网络更新</tt>和<tt>NBT读取</tt></p>
     */
    void readCapabilityNBT(NBTTagCompound NBT);
}
