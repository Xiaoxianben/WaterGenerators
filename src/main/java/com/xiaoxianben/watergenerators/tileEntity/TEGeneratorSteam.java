package com.xiaoxianben.watergenerators.tileEntity;

import com.xiaoxianben.watergenerators.event.ConfigLoader;
import com.xiaoxianben.watergenerators.fluid.FluidTankGeneratorSteam;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class TEGeneratorSteam extends TEGeneratorBase {

    /**
     * 生产一次电量所需的液体数量，单位为 mB/次
     */
    public int basicAmountOfSteamToProduceEnergy = ConfigLoader.basicAmountOfSteamToProduceEnergy;
    public FluidTankGeneratorSteam steamTank;
    private long maxFluidDrain;

    public TEGeneratorSteam() {
        this((int) (Math.pow(2, 31) - 1), 0, "");
    }

    /**
     * @param capacity            容量 = basePowerGeneration * 500, 单位FE
     * @param basePowerGeneration 基础能量产生量，单位FE
     * @param levelName           等级名
     */
    public TEGeneratorSteam(long capacity, long basePowerGeneration, String levelName) {
        super(capacity, basePowerGeneration, levelName);

        this.steamTank = new FluidTankGeneratorSteam((int) (capacity * 2));
        this.steamTank.setCanDrain(false);

        this.maxFluidDrain = basePowerGeneration * 100;
    }

    @Override
    protected void updateEnergy() {
        if (this.getEnergyStoredLong() < this.getMaxEnergyStoredLong() && this.steamTank.getFluid() != null) {
            // 产生1FE能量所需的流体量，单位为 mB/FE
            float realSteamUtilization = (float) this.basicAmountOfSteamToProduceEnergy / this.getBasePowerGeneration();

            float energyLack = this.getMaxEnergyStoredLong() - this.getEnergyStoredLong();
            // 计算需要补充的液体的数量
            int fluidAmountLack = (int) (energyLack * realSteamUtilization);
            fluidAmountLack = fluidAmountLack <= 0 ? 1 : fluidAmountLack;

            // 尝试从存储罐中抽出指定数量的液体
            FluidStack fluidStack = this.steamTank.drainInternal((int) Math.min(fluidAmountLack, this.maxFluidDrain), true);

            // 如果抽出了液体，计算接收的电能，否则为0
            if (fluidStack != null) {
                this.finallyReceiveEnergy = (long) (fluidStack.amount / realSteamUtilization);
            } else {
                this.finallyReceiveEnergy = 0;
            }
        } else {
            this.finallyReceiveEnergy = 0;
        }
        this.modifyEnergyStored(this.finallyReceiveEnergy);
    }

    // 注入Capability
    @ParametersAreNonnullByDefault
    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(steamTank);
        }
        return super.getCapability(capability, facing);
    }

    @ParametersAreNonnullByDefault
    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return this.getCapability(capability, facing) != null;
    }

    // NBT
    @Override
    public NBTTagCompound getCapabilityNBT() {
        NBTTagCompound nbtTagCompound = super.getCapabilityNBT();

        NBTTagCompound nbtFluidTank = steamTank.writeToNBT(new NBTTagCompound());
        nbtTagCompound.setTag("FluidTank", nbtFluidTank);

        return nbtTagCompound;
    }

    @Override
    public void readCapabilityNBT(NBTTagCompound NBT) {
        super.readCapabilityNBT(NBT);

        this.maxFluidDrain = this.getBasePowerGeneration() * 100;
        NBTTagCompound nbtFluidTank = NBT.getCompoundTag("FluidTank");
        steamTank.readFromNBT(nbtFluidTank);
    }
}
