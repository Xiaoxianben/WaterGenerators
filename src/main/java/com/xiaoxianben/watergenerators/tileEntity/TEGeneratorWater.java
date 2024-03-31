package com.xiaoxianben.watergenerators.tileEntity;

import com.xiaoxianben.watergenerators.enery.EnergyLiquid;
import com.xiaoxianben.watergenerators.event.ConfigLoader;
import com.xiaoxianben.watergenerators.fluid.FluidTankGenerator;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class TEGeneratorWater extends TEGeneratorBase {

    /**
     * 产生能量所需的基础流体量，默认为100
     */
    private final int basicAmountOfFluidToProduceEnergy = ConfigLoader.basicAmountOfFluidToProduceEnergy;
    protected FluidTankGenerator tank;
    private long maxFluidDrain;

    public TEGeneratorWater() {
        this((int) (Math.pow(2, 31) - 1), 0, "");
    }

    /**
     * @param capacity            容量 = basePowerGeneration * 500, 单位FE
     * @param basePowerGeneration 基础能量产生量，单位mbT
     * @param level               等级名
     */
    public TEGeneratorWater(long capacity, long basePowerGeneration, String level) {
        super(capacity, basePowerGeneration, level);
        // =basePowerGeneration*1000
        this.tank = new FluidTankGenerator((int) (capacity * 2));
        this.tank.setCanDrain(false);
        // =basePowerGeneration*100
        this.maxFluidDrain = basePowerGeneration * 100;
    }

    public float getFluidAmount() {
        return this.tank.getFluidAmount();
    }

    public float getMaxFluidAmount() {
        return this.tank.getCapacity();
    }

    public String[] getRoughFluidAmount() {
        return this.getRoughData(this.tank.getFluidAmount());
    }

    public String[] getRoughMaxFluidAmount() {
        return this.getRoughData(this.tank.getCapacity());
    }

    @Override
    protected void updateEnergy() {
        if (this.getEnergyStoredLong() < this.getMaxEnergyStoredLong() && this.tank.getFluid() != null) {
            FluidStack fluid = this.tank.getFluid();
            // 每次生成实际基础能量和实际液体效率
            // 实际基础能量产生量 = 基础基础能量产生量 * 液体能量效率
            // 实际液体效率 = 一次基础发电所需液体量 / 实际基础能量产生量
            float realPowerGeneration = this.getBasePowerGeneration() * EnergyLiquid.getEnergyFromLiquid(fluid.getFluid());

            // 产生1FE能量所需的流体量，单位为 mB/FE
            float realLiquidUtilization = this.basicAmountOfFluidToProduceEnergy / realPowerGeneration;

            float energyLack = this.getMaxEnergyStoredLong() - this.getEnergyStoredLong();
            // 计算需要补充的液体的数量
            int fluidAmountLack = (int) (energyLack * realLiquidUtilization);
            fluidAmountLack = fluidAmountLack <= 0 ? 1 : fluidAmountLack;

            // 尝试从存储罐中抽出指定数量的液体
            FluidStack fluidStack = tank.drainInternal((int) Math.min(fluidAmountLack, this.maxFluidDrain), true);

            // 如果抽出了液体，计算接收的电能，否则为0
            if (fluidStack != null) {
                this.finallyReceiveEnergy = (long) (fluidStack.amount / realLiquidUtilization);
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
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(tank);
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

        NBTTagCompound nbtFluidTank = tank.writeToNBT(new NBTTagCompound());
        nbtTagCompound.setTag("FluidTank", nbtFluidTank);

        return nbtTagCompound;
    }

    @Override
    public void readCapabilityNBT(NBTTagCompound NBT) {
        super.readCapabilityNBT(NBT);

        this.maxFluidDrain = this.getBasePowerGeneration() * 100;
        NBTTagCompound nbtFluidTank = NBT.getCompoundTag("FluidTank");
        tank.readFromNBT(nbtFluidTank);
    }

}
