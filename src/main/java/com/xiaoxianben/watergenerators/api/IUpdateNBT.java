package com.xiaoxianben.watergenerators.api;

import net.minecraft.nbt.NBTTagCompound;

public interface IUpdateNBT {

    /**
     * 更新 进行网络更新的nbt数据，只用于在网络更新时
     *
     * @param NBT 网络更新时的nbt数据
     */
    void updateNBT(NBTTagCompound NBT);

    /**
     * 获取 进行网络更新的nbt数据，只用于在网络更新时
     */
    NBTTagCompound getUpdateNBT();
}
