package com.xiaoxianben.watergenerators.tileEntity;

import com.xiaoxianben.watergenerators.API.ICapabilityNBT;
import com.xiaoxianben.watergenerators.API.IUpdateNBT;
import com.xiaoxianben.watergenerators.Main;
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

public abstract class TEEnergyBasic extends TileEntity implements IEnergyStorage, ITickable, ICapabilityNBT, IUpdateNBT {

    public EnumFacing[] transferEnergyFacings = new EnumFacing[6];

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

    protected void transferEnergy(TileEntity adjacentTE, EnumFacing facing) {
    }

    protected void onNeighborChanged(World world) {
    }

    protected void updateEnergy() {
    }

    protected void updateLight() {
        int light2 = this.getWorld().getLightFor(EnumSkyBlock.BLOCK, this.getPos());
        int light1 = this.getLightValue();
        if (light1 != light2 && this.getWorld().checkLightFor(EnumSkyBlock.BLOCK, this.getPos())) {
            this.getWorld().setLightFor(EnumSkyBlock.BLOCK, this.getPos(), this.getLightValue());
        }
    }

    protected void updateState() {
    }

    @Override
    public void update() {
        // 如果存在世界
        if (this.hasWorld()) {
            // 更新邻居
            this.onNeighborChanged(this.world);
            this.updateLight();
        }

        if (!this.getWorld().isRemote) {
            // 传输能量
            if (this.canExtract() && this.transferEnergyFacings[0] != null) {
                // 遍历facingList
                for (EnumFacing facing : this.transferEnergyFacings) {
                    // 如果facing不为空
                    if (facing != null) {
                        // 传输能量
                        this.transferEnergy(Objects.requireNonNull(this.getWorld().getTileEntity(this.getPos().offset(facing))), facing);
                    }
                }
            }
            // 更新状态
            this.updateState();
            // 更新能量
            this.updateEnergy();

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
        this.markDirty();
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

    public boolean canConnectEnergy(EnumFacing enumFacing) {
        return true;
    }

    protected void modifyEnergyStored(long energy) {
        this.energyStorage.modifyEnergyStored(energy);
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

    @Override
    public NBTTagCompound getCapabilityNBT() {
        return energyStorage.writeToNBT(new NBTTagCompound());
    }

    @Override
    public void readCapabilityNBT(NBTTagCompound NBT) {
        energyStorage.readFromNBT(NBT);
    }

    @Override
    public abstract void updateNBT(NBTTagCompound NBT);

    @Override
    public abstract NBTTagCompound getUpdateNBT();
}
