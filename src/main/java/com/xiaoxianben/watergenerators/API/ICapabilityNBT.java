package com.xiaoxianben.watergenerators.API;

import net.minecraft.nbt.NBTTagCompound;

public interface ICapabilityNBT {
    NBTTagCompound getCapabilityNBT();
    /**
     * 优先启动此方法
     * */
    void readCapabilityNBT(NBTTagCompound NBT);
}
