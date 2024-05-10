package com.xiaoxianben.watergenerators.api;

import net.minecraft.nbt.NBTTagCompound;

public interface IHasItemNBT {
    /**
     * 获取方块实体要保存的物品NBT数据
     */
    NBTTagCompound getItemNbt();

    /**
     * 读取方块实体要保存的物品NBT数据
     *
     * @param nbt 要读取的物品NBT数据
     */
    void readItemNbt(NBTTagCompound nbt);
}
