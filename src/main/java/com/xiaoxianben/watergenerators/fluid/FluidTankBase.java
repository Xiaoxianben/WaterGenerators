package com.xiaoxianben.watergenerators.fluid;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidTank;

public class FluidTankBase extends FluidTank {
    public FluidTankBase(int capacity) {
        super(capacity);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        NBTTagCompound nbtTagCompound = super.writeToNBT(nbt);
        nbtTagCompound.setInteger("fluidTankCapacity", this.getCapacity());
        return nbtTagCompound;
    }

    @Override
    public FluidTank readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        if (nbt.hasKey("fluidTankCapacity")) {
            this.setCapacity(nbt.getInteger("fluidTankCapacity"));
        }
        return this;
    }
}
