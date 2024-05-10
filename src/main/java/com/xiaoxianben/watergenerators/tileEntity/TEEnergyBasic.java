package com.xiaoxianben.watergenerators.tileEntity;

import com.xiaoxianben.watergenerators.Main;
import com.xiaoxianben.watergenerators.api.ICapabilityNBT;
import com.xiaoxianben.watergenerators.api.IHasItemNBT;
import com.xiaoxianben.watergenerators.api.IUpdateNBT;
import com.xiaoxianben.watergenerators.enery.EnergyStorage;
import com.xiaoxianben.watergenerators.event.PacketConsciousness;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

public abstract class TEEnergyBasic extends TileEntity implements IEnergyStorage, ITickable, ICapabilityNBT, IUpdateNBT, IHasItemNBT {

    /**
     * 每tick的传输能量的方向列表
     */
    public EnumFacing[] transferEnergyFacings = new EnumFacing[6];
    /**
     * 每tick的传输能量的数值列表
     */
    public long[] finallyExtractEnergyList = new long[]{0, 0, 0, 0, 0, 0};
    /**
     * 每tick的实际发电量
     */
    public long finallyReceiveEnergy;
    /**
     * 每tick的实际传输能量
     */
    public long finallyExtractEnergy;

    protected EnergyStorage energyStorage;


    public TEEnergyBasic(long capacity, long maxReceive, long maxExtract) {
        super();
        energyStorage = new EnergyStorage(capacity, maxReceive, maxExtract);
    }


    protected int getMaxLightValue() {
        return 15;
    }

    public int getLightValue() {
        return (int) (getMaxLightValue() * ((float) this.getEnergyStored() / this.getMaxEnergyStored()));
    }

    public long getFinallyExtractEnergy() {
        long rf = 0;
        for (long temp : this.finallyExtractEnergyList) {
            rf += temp;
        }
        return rf;
    }

    public long getFinallyReceiveEnergy() {
        return this.finallyReceiveEnergy;
    }


    // ------------------------------------------传输能量的部分-------------------------------------------------
    protected boolean updateTransferFacings(World world) {
        this.transferEnergyFacings = new EnumFacing[this.transferEnergyFacings.length];
        this.finallyExtractEnergyList = new long[]{0, 0, 0, 0, 0, 0};
        byte fa = 0;
        for (EnumFacing facing : EnumFacing.VALUES) {
            TileEntity adjacentTE = world.getTileEntity(this.getPos().offset(facing));
            if (this.canTransferEnergy(adjacentTE, facing.getOpposite())) {
                this.transferEnergyFacings[fa++] = facing;
            }
        }
        return fa > 0;
    }

    protected boolean canTransferEnergy(TileEntity TE, EnumFacing facing) {
        if (TE != null && TE.hasCapability(CapabilityEnergy.ENERGY, facing)) {
            return Objects.requireNonNull(TE.getCapability(CapabilityEnergy.ENERGY, facing)).canReceive();
        }
        return false;
    }

    protected void transferEnergy(TileEntity adjacentTE, EnumFacing facing) {
        int temp = (Objects.requireNonNull(adjacentTE.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite()))).
                receiveEnergy(this.getEnergyStored(), false);

        this.finallyExtractEnergyList[facing.getIndex()] += this.extractEnergy(temp, false);
    }
    // ------------------------------------------------------------------------------------------------------


    /**
     * 更新能量，在方法内部实际注入能量，并返回实际注入的能量
     */
    protected long updateEnergy() {
        return 0;
    }

    protected void updateLight() {
        int light2 = this.getWorld().getLightFor(EnumSkyBlock.BLOCK, this.getPos());
        int light1 = this.getLightValue();
        if (light1 != light2 && this.getWorld().checkLightFor(EnumSkyBlock.BLOCK, this.getPos())) {
            this.getWorld().setLightFor(EnumSkyBlock.BLOCK, this.getPos(), light1);
        }
    }

    protected void updateState() {
        finallyExtractEnergy = this.getFinallyExtractEnergy();
    }


    @Override
    public void update() {
        // 如果存在世界
        if (this.hasWorld()) {
            this.updateLight();
        }

        if (!this.getWorld().isRemote) {
            // 传输能量
            if (this.canExtract() && this.updateTransferFacings(this.world)) {
                for (EnumFacing facing : this.transferEnergyFacings) {
                    if (facing == null) break;
                    this.transferEnergy(Objects.requireNonNull(this.getWorld().getTileEntity(this.getPos().offset(facing))), facing);
                }
            }
            // 更新状态
            this.updateState();
            // 更新能量
            this.finallyReceiveEnergy = this.updateEnergy();

            // 创建更新NBT
            NBTTagCompound updateNBT = new NBTTagCompound();
            // 设置更新标签
            NBTTagCompound capabilityNBT = this.getCapabilityNBT();

            NBTTagCompound blockPos = new NBTTagCompound();
            blockPos.setInteger("x", this.getPos().getX());
            blockPos.setInteger("y", this.getPos().getY());
            blockPos.setInteger("z", this.getPos().getZ());

            updateNBT.setTag("capability", capabilityNBT);
            updateNBT.setTag("updateNBT", this.getUpdateNBT());
            updateNBT.setTag("blockPos", blockPos);
            // 发送更新标签
            Main.getNetwork().sendToAll(new PacketConsciousness(updateNBT));
        }
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

    @ParametersAreNonnullByDefault
    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return this.getCapability(capability, facing) != null;
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

    public long getEnergyStoredLong() {
        return energyStorage.getEnergyStoredLong();
    }

    public long getMaxEnergyStoredLong() {
        return energyStorage.getMaxEnergyStoredLong();
    }

    @Override
    public boolean canExtract() {
        return energyStorage.canExtract();
    }

    @Override
    public boolean canReceive() {
        return energyStorage.canReceive();
    }

    // NBT
    @ParametersAreNonnullByDefault
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        // 从 compound 中读取相关数据
        this.readCapabilityNBT(compound.getCompoundTag("Capability"));

        super.readFromNBT(compound);
    }

    @ParametersAreNonnullByDefault
    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        // 将相关数据保存到 compound 中
        compound.setTag("Capability", this.getCapabilityNBT());

        return super.writeToNBT(compound);
    }

    @Nonnull
    @Override
    public NBTTagCompound getUpdateTag() {
        return this.writeToNBT(new NBTTagCompound());
    }

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

    @Override
    public abstract void updateNBT(NBTTagCompound NBT);

    @Override
    public abstract NBTTagCompound getUpdateNBT();
}
