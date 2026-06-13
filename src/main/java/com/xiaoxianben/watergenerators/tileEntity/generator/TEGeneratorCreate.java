package com.xiaoxianben.watergenerators.tileEntity.generator;

import com.xiaoxianben.watergenerators.items.itemHandler.ItemComponentHandler;

public class TEGeneratorCreate extends TEGeneratorBase {

    public TEGeneratorCreate() {
        super();
        this.itemComponentHandler = new ItemComponentHandler(null);
    }

    @Override
    public long updateEnergy() {
        this.energyStorage.setEnergy(this.basePowerGeneration);
        return this.basePowerGeneration;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return maxExtract;
    }

}
