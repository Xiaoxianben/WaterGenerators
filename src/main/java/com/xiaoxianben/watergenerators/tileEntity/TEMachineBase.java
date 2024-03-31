package com.xiaoxianben.watergenerators.tileEntity;

import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;

public class TEMachineBase extends TEEnergyBasic {

    public boolean open = false;
    protected float level;

    /**
     * 接受能量，不传输能量
     */

    public TEMachineBase(long capacity, float level) {
        super(capacity, Integer.MAX_VALUE, 0);
        this.level = level;
    }

    public float getLevel() {
        return level;
    }

    // NBT
    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound) {
        NBTTagCompound nbtTagCompound = super.writeToNBT(compound);

        NBTTagCompound nbtMachine = new NBTTagCompound();
        nbtMachine.setFloat("Level", this.getLevel());
        nbtTagCompound.setTag("Attribute", nbtMachine);

        return nbtTagCompound;
    }

    @Override
    public void updateNBT(NBTTagCompound NBT) {
        this.open = NBT.getBoolean("open");
    }

    @Override
    public NBTTagCompound getUpdateNBT() {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setBoolean("open", this.open);
        return nbtTagCompound;
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound NBT) {
        NBTTagCompound nbtMachine = NBT.getCompoundTag("Attribute");
        this.level = nbtMachine.getFloat("Level");

        super.readFromNBT(NBT);
    }

}
