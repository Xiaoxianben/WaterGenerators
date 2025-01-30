package com.xiaoxianben.watergenerators.enery;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.IEnergyStorage;

public class EnergyStorage implements IEnergyStorage {

    protected long energy;
    protected long capacity;
    protected long maxReceive;
    protected long maxExtract;

    protected static final String[] NBT_KEYS = {"EnergyStorage", "Energy", "Capacity", "MaxReceive", "MaxExtract"};


    public EnergyStorage(long capacity, long maxReceive, long maxExtract) {
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
    }

    public EnergyStorage(long capacity, boolean canReceive, boolean canExtract) {
        this(capacity, canReceive ? Integer.MAX_VALUE : 0, canExtract ? Integer.MAX_VALUE : 0);
    }


    public EnergyStorage setCapacity(long capacity) {
        this.capacity = capacity;

        if (energy > capacity) {
            energy = capacity;
        }
        return this;
    }

    public EnergyStorage setEnergy(long energy) {
        this.energy = energy > capacity ? capacity : (energy < 0 ? 0 : energy);

        return this;
    }

    public int getIntForLong(long l) {
        return l >= Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) l;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if (!canReceive())
            return 0;

        int energyReceived = (int) Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));
        if (!simulate)
            energy += energyReceived;
        return energyReceived;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        if (!canExtract())
            return 0;

        int energyExtracted = (int) Math.min(energy, Math.min(this.maxExtract, maxExtract));
        if (!simulate)
            energy -= energyExtracted;
        return energyExtracted;
    }

    @Override
    public int getEnergyStored() {
        return getIntForLong(energy);
    }

    @Override
    public int getMaxEnergyStored() {
        return getIntForLong(capacity);
    }

    @Override
    public boolean canExtract() {
        return this.maxExtract > 0;
    }

    @Override
    public boolean canReceive() {
        return this.maxReceive > 0;
    }

    public long getEnergyStoredLong() {
        return energy;
    }

    public long getMaxEnergyStoredLong() {
        return capacity;
    }

    /**
     * This function is included to allow the containing tile to directly and efficiently modify the energy contained in the EnergyStorage. Do not rely on this externally, as not all IEnergyHandlers are guaranteed to have it.
     *
     * @param energy The amount of energy to be injected into the storage.
     * @return 实际注入的能量。
     */
    public long modifyEnergyStored(long energy) {
        long energyR = this.energy;
        this.energy += energy;

        if (this.energy > capacity) {
            this.energy = capacity;
        } else if (this.energy < 0) {
            this.energy = 0;
        }
        return this.energy - energyR;
    }

    public EnergyStorage readFromNBT(NBTTagCompound nbt) {
        NBTTagCompound nbt1 = nbt.getCompoundTag(NBT_KEYS[0]);

        this.energy = nbt1.getLong(NBT_KEYS[1]);
        this.capacity = nbt1.getLong(NBT_KEYS[2]);
        this.maxReceive = nbt1.getLong(NBT_KEYS[3]);
        this.maxExtract = nbt1.getLong(NBT_KEYS[4]);

        if (energy > capacity) {
            energy = capacity;
        }
        return this;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        if (energy < 0) {
            energy = 0;
        }
        NBTTagCompound nbt1 = new NBTTagCompound();

        nbt1.setLong(NBT_KEYS[1], energy);
        nbt1.setLong(NBT_KEYS[2], capacity);
        nbt1.setLong(NBT_KEYS[3], maxReceive);
        nbt1.setLong(NBT_KEYS[4], maxExtract);

        nbt.setTag(NBT_KEYS[0], nbt1);
        return nbt;
    }

}
