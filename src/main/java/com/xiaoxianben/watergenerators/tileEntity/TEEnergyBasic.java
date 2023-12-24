package com.xiaoxianben.watergenerators.tileEntity;

import cofh.redstoneflux.api.IEnergyProvider;
import cofh.redstoneflux.api.IEnergyReceiver;
import com.xiaoxianben.watergenerators.Main;
import com.xiaoxianben.watergenerators.enery.EnergyStorage;
import com.xiaoxianben.watergenerators.event.PacketConsciousness;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

public class TEEnergyBasic extends TileEntity implements IEnergyProvider, IEnergyStorage,ITickable {
    public String level;
    public EnergyStorage energyStorage;
    public int finallyReceiveEnergy;
    public int basePowerGeneration;
    public int[] finallyExtractEnergyList = new int[]{0, 0, 0, 0, 0, 0};
    public EnumFacing[] facingList = new EnumFacing[6];

    public TEEnergyBasic() {
        this((int) (Math.pow(2, 31) - 1), 0, "");
    }
    public TEEnergyBasic(int capacity, int basePowerGeneration, String level) {
        super();
        energyStorage = new EnergyStorage(capacity, 0, (int) (Math.pow(2, 31) - 1));
        this.basePowerGeneration = basePowerGeneration;
        this.level = level;
    }

    public boolean onBlockActivated(@Nonnull EntityPlayer player, @Nonnull EnumHand hand) {
        return true;
    }
    public void onNeighborChanged (World world) {
        this.facingList = new EnumFacing[this.facingList.length];
        byte fa = 0;
        for (EnumFacing facing : EnumFacing.values()) {
            if (!this.canConnectEnergy(facing)) continue;
            TileEntity adjacentTE = world.getTileEntity(this.getPos().offset(facing));
            if (this.canTransferEnergy(adjacentTE, facing.getOpposite())) {
                this.facingList[fa++] = facing;
            }
        }
    }

    public int getBasePowerGeneration() {
        return this.basePowerGeneration;
    }
    public void setBasePowerGeneration(int basePowerGeneration) {
        this.basePowerGeneration = basePowerGeneration;
    }
    public int getFinallyReceiveEnergySpecific() {
        return this.finallyReceiveEnergy;
    }
    public int getFinallyExtractEnergySpecific() {
        int rf = 0;
        for (int temp : this.finallyExtractEnergyList) {
            rf += temp;
        }
        return rf;
    }
    public String[] getFinallyExtractEnergyRough() {
        float rf = this.getFinallyExtractEnergySpecific();
        String[] rfList;

        if(rf > 600000) rfList = new String[]{String.valueOf(rf / 1000000), "6"};
        else if(rf > 600) rfList = new String[]{String.valueOf(rf / 1000), "3"};
        else rfList = new String[]{String.valueOf(rf), "0"};

        return rfList;
    }

    public void updateEnergy(int updateEnergy) {
        int maxEnergy = energyStorage.getMaxEnergyStored();
        int energy = energyStorage.getEnergyStored();
        if (energy < maxEnergy) {
            this.finallyReceiveEnergy = energy;
            energyStorage.modifyEnergyStored(this.finallyReceiveEnergy);
        } else {
            this.finallyReceiveEnergy = 0;
        }
    }
    public boolean canTransferEnergy(TileEntity TE, EnumFacing facing) {
        if(TE == null) return false;
        else if(TE.hasCapability(CapabilityEnergy.ENERGY, facing)) {
            return TE.getCapability(CapabilityEnergy.ENERGY, facing).canReceive();
        } else if (TE instanceof IEnergyReceiver) {
            return ((IEnergyReceiver) TE).receiveEnergy(facing, this.getEnergyStored(), true) != 0;
        } else if (TE instanceof cofh.redstoneflux.api.IEnergyStorage) {
            return ((cofh.redstoneflux.api.IEnergyStorage) TE).receiveEnergy(this.getEnergyStored(), true) != 0;
        }
        return false;
    }
    public void transferEnergy(TileEntity adjacentTE, EnumFacing facing) {
        int temp = 0;
        if (adjacentTE.hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite())) {
            temp = (Objects.requireNonNull(adjacentTE.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite()))).
                    receiveEnergy(this.getEnergyStored(), false);
        } else if (adjacentTE instanceof IEnergyReceiver) {
            temp = ((IEnergyReceiver) adjacentTE).
                    receiveEnergy(facing.getOpposite(), this.getEnergyStored(), false);
        } else if (adjacentTE instanceof cofh.redstoneflux.api.IEnergyStorage) {
            temp = ((cofh.redstoneflux.api.IEnergyStorage) adjacentTE).
                    receiveEnergy(this.getEnergyStored(), false);
        }
        this.finallyExtractEnergyList[facing.getIndex()] += this.extractEnergy(temp, false);
    }

    public void initExtractEnergyList() {
        this.finallyExtractEnergyList = new int[]{0, 0, 0, 0, 0, 0};
    }

    @Override
    public void update() {
        this.updateEnergy(this.getBasePowerGeneration());
        this.initExtractEnergyList();
        if (this.hasWorld()) {
            this.onNeighborChanged(this.world);
        }
        if (this.facingList[0] != null) {
            for (EnumFacing facing : this.facingList) {
                if (facing != null) {
                    this.transferEnergy(this.getWorld().getTileEntity(this.getPos().offset(facing)), facing);
                }
            }
        }
        this.markDirty();

        if(!this.getWorld().isRemote) {
            NBTTagCompound updateNBT = new NBTTagCompound();
            updateNBT.setTag("consciousness", this.getUpdateTag());
            Main.getNetwork().sendToAll(new PacketConsciousness(updateNBT));
        }
    }

    // 注入Capability
    @ParametersAreNonnullByDefault
    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            return CapabilityEnergy.ENERGY.cast(energyStorage);
        }
        return super.getCapability(capability, facing);
    }
    @ParametersAreNonnullByDefault
    @Override
    public boolean hasCapability(Capability<?> capability,@Nullable EnumFacing facing) {
        return this.getCapability(capability, facing) != null;
    }

    // NBT
    @ParametersAreNonnullByDefault
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        // 从 compound 中读取相关数据
        energyStorage.readFromNBT(compound);

        energyStorage.setCapacity(compound.getInteger("capacity"));
        this.setBasePowerGeneration(compound.getInteger("basePowerGeneration"));
        super.readFromNBT(compound);
    }
    @ParametersAreNonnullByDefault
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        // 将相关数据保存到 compound 中
        NBTTagCompound NBT = energyStorage.writeToNBT(compound);
        NBT.setInteger("capacity", this.getMaxEnergyStored());
        NBT.setInteger("basePowerGeneration", this.getBasePowerGeneration());
        return super.writeToNBT(NBT);
    }
    @ParametersAreNonnullByDefault
    @Override
    public NBTTagCompound getUpdateTag() {
        return this.writeToNBT(new NBTTagCompound());
    }

    // RF的接口
    @Override
    public boolean canConnectEnergy(EnumFacing enumFacing) {
        return enumFacing != EnumFacing.UP;
    }
    @Override
    public int extractEnergy(EnumFacing enumFacing, int i, boolean b) {
        if (!this.canConnectEnergy(enumFacing)) return 0;
        return energyStorage.extractEnergy(i, b);
    }
    @Override
    public int getEnergyStored(EnumFacing enumFacing) {
        return energyStorage.getEnergyStored();
    }
    @Override
    public int getMaxEnergyStored(EnumFacing enumFacing) {
        return energyStorage.getMaxEnergyStored();
    }

    // FE的接口
    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return 0;
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
        return true;
    }
    @Override
    public boolean canReceive() {
        return false;
    }

}
