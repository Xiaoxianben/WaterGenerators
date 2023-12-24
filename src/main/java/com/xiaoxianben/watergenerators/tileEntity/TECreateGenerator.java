package com.xiaoxianben.watergenerators.tileEntity;

public class TECreateGenerator extends TEEnergyBasic {

    public TECreateGenerator() {
        this((int) (Math.pow(2, 31) - 1), 0);
    }

    public TECreateGenerator(int capacity, int basePowerGeneration) {
        super(capacity, basePowerGeneration, "创造");
    }

    @Override
    public void updateEnergy(int updateEnergy) {
        this.finallyReceiveEnergy = this.getEnergyStored() < this.getMaxEnergyStored() ? this.basePowerGeneration:0;
        this.energyStorage.modifyEnergyStored(this.finallyReceiveEnergy);
    }

}
