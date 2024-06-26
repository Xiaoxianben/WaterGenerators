package com.xiaoxianben.watergenerators.api;

import net.minecraft.nbt.NBTTagCompound;

public interface INetworkUpdateNBT {
    /**
     * 获取 进行网络更新的nbt数据，只用于在网络更新时
     */
    NBTTagCompound getNetworkUpdateNBT();

    /**
     * 更新 进行网络更新的nbt数据，只用于在网络更新时
     *
     * @param NBT 网络更新时的nbt数据
     */
    void readNetworkUpdateNBT(NBTTagCompound NBT);
}
