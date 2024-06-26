package com.xiaoxianben.watergenerators.tileEntity.generator;

import com.xiaoxianben.watergenerators.items.itemHandler.ItemComponentHandler;

public class TEGeneratorCreate extends TEGeneratorBase {

    @SuppressWarnings("unused")
    public TEGeneratorCreate() {
        this(0);
    }

    public TEGeneratorCreate(long basePowerGeneration) {
        super(basePowerGeneration, 92.5f);
        this.itemComponentHandler = new ItemComponentHandler(null);
    }

    @Override
    public long updateEnergy() {
        long receiveEnergy = this.getEnergyStoredLong() < this.getMaxEnergyStoredLong() ? this.basePowerGeneration : 0;
        return this.modifyEnergyStored(receiveEnergy);
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return energyStorage.extractEnergy(maxExtract, true);
    }

}
