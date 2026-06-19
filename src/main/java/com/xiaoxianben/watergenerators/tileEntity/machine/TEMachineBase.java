package com.xiaoxianben.watergenerators.tileEntity.machine;

import com.xiaoxianben.watergenerators.blocks.machine.BlockMachineBase;
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

    public TEMachineBase() {
        this(999);
    }

    public float getLevel() {
        return level;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        this.level = ((BlockMachineBase) getBlockType()).getLevel();
        this.energyStorage.setCapacity((long) (this.level * 10000));
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
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound NBT) {
        super.readFromNBT(NBT);
    }

    @Override
    protected long updateEnergy() {
        return 0;
    }
}
