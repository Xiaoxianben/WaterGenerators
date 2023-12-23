package com.xiaoxianben.watergenerators.tileEntity;

public class TECreateGenerator extends TEEnergyBasic {
    public TECreateGenerator(int capacity, int basePowerGeneration) {
        super(capacity, basePowerGeneration);
    }

    @Override
    public void updateEnergy(int updateEnergy) {
        this.finallyReceiveEnergy = this.getEnergyStored() < this.getMaxEnergyStored() ? this.basePowerGeneration:0;
        this.energyStorage.modifyEnergyStored(this.finallyReceiveEnergy);
    }

}
