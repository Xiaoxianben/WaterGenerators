package com.xiaoxianben.watergenerators.tileEntity.machine;

import com.xiaoxianben.watergenerators.tileEntity.TEEnergyBasic;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;

public class TEMachineBase extends TEEnergyBasic {

    public boolean open = false;
    protected float level;

    /**
     * 接受能量，不传输能量
     *
     * @param level 机器的等级
     */
    public TEMachineBase(float level) {
        super((long) (level * 10000), true, false);
        this.level = level;
    }

    public float getLevel() {
        return level;
    }


    // NBT
    @Override
    public NBTTagCompound getNetworkUpdateNBT() {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setBoolean("open", this.open);
        return nbtTagCompound;
    }

    @Override
    public void readNetworkUpdateNBT(NBTTagCompound NBT) {
        this.open = NBT.getBoolean("open");
    }

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
    public void readFromNBT(@Nonnull NBTTagCompound NBT) {
        NBTTagCompound nbtMachine = NBT.getCompoundTag("Attribute");
        this.level = nbtMachine.getFloat("Level");

        super.readFromNBT(NBT);
    }

    @Override
    protected long updateEnergy() {
        return 0;
    }
}
