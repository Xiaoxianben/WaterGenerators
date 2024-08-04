package com.xiaoxianben.watergenerators.tileEntity;

import com.xiaoxianben.watergenerators.api.IHasItemNBT;
import com.xiaoxianben.watergenerators.enery.EnergyStorage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashSet;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public abstract class TEEnergyBasic extends TEBase implements IEnergyStorage, IHasItemNBT {

    /**
     * 每tick的实际发电量
     */
    public long finallyReceiveEnergy;
    /**
     * 每tick的实际传输能量
     */
    public long finallyExtractEnergy;
    /**
     * 每tick的将要传输能量的方向的列表
     */
    protected HashSet<EnumFacing> transferEnergyFacings = new HashSet<>();
    /**
     * 每tick的传输能量的数值列表
     */
    protected HashSet<Long> finallyExtractEnergyList = new HashSet<>();

    protected EnergyStorage energyStorage;


    public TEEnergyBasic(long capacity, boolean isGenerator) {
        super();
        energyStorage = new EnergyStorage(capacity, !isGenerator, isGenerator);
    }


    protected long getFinallyExtractEnergyP() {
        AtomicLong rf = new AtomicLong();
        this.finallyExtractEnergyList.forEach(rf::addAndGet);
        return rf.get();
    }

    public long getFinallyExtractEnergy() {
        return this.finallyExtractEnergy;
    }

    public long getFinallyReceiveEnergy() {
        return this.finallyReceiveEnergy;
    }


    // ------------------------------------------传输能量的部分-------------------------------------------------
    protected boolean updateTransferFacings(World world) {
        this.transferEnergyFacings.clear();
        this.finallyExtractEnergyList.clear();

        for (EnumFacing facing : EnumFacing.VALUES) {
            if (this.canTransferEnergy(world.getTileEntity(this.getPos().offset(facing)), facing.getOpposite())) {
                this.transferEnergyFacings.add(facing);
            }
        }
        return !this.transferEnergyFacings.isEmpty();
    }

    /**
     * @param TE     要传输能量的相邻TE
     * @param facing 相邻TE要传输能量的方向
     */
    protected boolean canTransferEnergy(TileEntity TE, EnumFacing facing) {
        if (TE != null && TE.hasCapability(CapabilityEnergy.ENERGY, facing)) {
            IEnergyStorage iEnergyStorage = Objects.requireNonNull(TE.getCapability(CapabilityEnergy.ENERGY, facing));
            return iEnergyStorage.canReceive() && iEnergyStorage.getEnergyStored() < iEnergyStorage.getMaxEnergyStored();
        }
        return false;
    }

    protected void transferEnergy(TileEntity adjacentTE, EnumFacing facing) {
        int temp = (Objects.requireNonNull(adjacentTE.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite()))).
                receiveEnergy(this.getEnergyStored(), false);

        this.finallyExtractEnergyList.add(this.modifyEnergyStored(-temp));
    }
    // ------------------------------------------------------------------------------------------------------


    /**
     * 更新能量，在方法内部实际注入能量，并返回实际注入的能量
     */
    protected abstract long updateEnergy();


    public void updateStateInSever() {
        // 传输能量
        if (this.canExtract() && this.updateTransferFacings(this.world)) {
            for (EnumFacing facing : this.transferEnergyFacings) {
                this.transferEnergy(Objects.requireNonNull(this.getWorld().getTileEntity(this.getPos().offset(facing))), facing);
            }
        }

        this.finallyReceiveEnergy = this.updateEnergy();
        this.finallyExtractEnergy = this.getFinallyExtractEnergyP();
    }


    protected long modifyEnergyStored(long energy) {
        return this.energyStorage.modifyEnergyStored(energy);
    }

    // FE的接口
    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return energyStorage.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return energyStorage.extractEnergy(maxExtract, simulate);
    }

    @Override
    public int getEnergyStored() {
        return energyStorage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored() {
        return energyStorage.getMaxEnergyStored();
    }

    @Override
    public boolean canExtract() {
        return energyStorage.canExtract();
    }

    @Override
    public boolean canReceive() {
        return energyStorage.canReceive();
    }

    public long getEnergyStoredLong() {
        return energyStorage.getEnergyStoredLong();
    }

    public long getMaxEnergyStoredLong() {
        return energyStorage.getMaxEnergyStoredLong();
    }


    // 注入Capability
    @ParametersAreNonnullByDefault
    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            return CapabilityEnergy.ENERGY.cast(this);
        }
        return super.getCapability(capability, facing);
    }

    // NBT
    @Override
    public NBTTagCompound getCapabilityNBT() {
        return energyStorage.writeToNBT(new NBTTagCompound());
    }

    @Override
    public void readCapabilityNBT(NBTTagCompound NBT) {
        energyStorage.readFromNBT(NBT);
    }

    @Override
    public NBTTagCompound getItemNbt() {
        NBTTagCompound NBT = new NBTTagCompound();
        NBT.setLong("Energy", this.getEnergyStoredLong());
        return NBT;
    }

    @Override
    public void readItemNbt(NBTTagCompound NBT) {
        this.modifyEnergyStored(NBT.getLong("Energy"));
    }

}
