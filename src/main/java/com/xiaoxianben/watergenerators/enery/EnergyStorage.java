package com.xiaoxianben.watergenerators.enery;

import net.minecraft.nbt.NBTTagCompound;

public class EnergyStorage extends net.minecraftforge.energy.EnergyStorage {
    public EnergyStorage(int capacity) {
        super(capacity);
    }

    public EnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public EnergyStorage(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public EnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
    }

	public EnergyStorage setCapacity(int capacity) {

		this.capacity = capacity;

		if (energy > capacity) {
			energy = capacity;
		}
		return this;
	}

	public EnergyStorage setMaxTransfer(int maxTransfer) {

		setMaxReceive(maxTransfer);
		setMaxExtract(maxTransfer);
		return this;
	}

	public EnergyStorage setMaxReceive(int maxReceive) {

		this.maxReceive = maxReceive;
		return this;
	}

	public EnergyStorage setMaxExtract(int maxExtract) {

		this.maxExtract = maxExtract;
		return this;
	}


	/**
	 * This function is included to allow the containing tile to directly and efficiently modify the energy contained in the EnergyStorage. Do not rely on this externally, as not all IEnergyHandlers are guaranteed to have it.
	 */
	public void modifyEnergyStored(int energy) {

		this.energy += energy;

		if (this.energy > capacity) {
			this.energy = capacity;
		} else if (this.energy < 0) {
			this.energy = 0;
		}
	}

    public EnergyStorage readFromNBT(NBTTagCompound nbt) {

		this.energy = nbt.getInteger("Energy");

		if (energy > capacity) {
			energy = capacity;
		}
		return this;
	}

	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {

		if (energy < 0) {
			energy = 0;
		}
		nbt.setInteger("Energy", energy);
		return nbt;
	}


}
