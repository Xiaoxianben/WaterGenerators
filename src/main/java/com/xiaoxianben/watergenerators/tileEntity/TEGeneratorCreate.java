package com.xiaoxianben.watergenerators.tileEntity;

public class TEGeneratorCreate extends TEGeneratorBase {

    public TEGeneratorCreate() {
        this(Integer.MAX_VALUE, 0);
    }

    public TEGeneratorCreate(long capacity, long basePowerGeneration) {
        super(capacity, basePowerGeneration, "创造");
    }

    @Override
    public void updateEnergy() {
        this.finallyReceiveEnergy = this.getEnergyStored() < this.getMaxEnergyStored() ? this.basePowerGeneration : 0;
        this.energyStorage.modifyEnergyStored(this.finallyReceiveEnergy);
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return energyStorage.extractEnergy(maxExtract, true);
    }

}
