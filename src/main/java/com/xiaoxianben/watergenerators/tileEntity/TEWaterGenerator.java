package com.xiaoxianben.watergenerators.tileEntity;

import com.xiaoxianben.watergenerators.liquid.FluidTankBasic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class TEWaterGenerator extends TEEnergyBasic {

    protected FluidTankBasic tank;
    public int maxFluidDrain;

    public TEWaterGenerator() {
        this((int) (Math.pow(2, 31) - 1), 0, "");
    }
    public TEWaterGenerator(int capacity, int basePowerGeneration, String level) {
        super(capacity, basePowerGeneration, level);
        this.tank = new FluidTankBasic(capacity / 8);
        this.tank.setCanDrain(false);
        this.maxFluidDrain = basePowerGeneration/2;
    }

    public boolean onBlockActivated(@Nonnull EntityPlayer player, @Nonnull EnumHand hand) {
        return !FluidUtil.interactWithFluidHandler(player, hand, this.tank);
    }

    public float getFluidAmount() {
        return this.tank.getFluidAmount();
    }
    public float getMaxFluidAmount() {
        return this.tank.getCapacity();
    }

    @Override
    public void updateEnergy(int updateEnergy) {
        if (this.getEnergyStored() < this.getMaxEnergyStored()) {
            int energy缺失 = this.getMaxEnergyStored(EnumFacing.NORTH) - this.getEnergyStored(EnumFacing.NORTH);
            FluidStack fluidStack = tank.drainInternal(Math.min((energy缺失 / updateEnergy), this.maxFluidDrain), true);
            if (fluidStack != null) {
                this.finallyReceiveEnergy = fluidStack.amount * updateEnergy;
            }
        } else {
            this.finallyReceiveEnergy = 0;
        }
        this.energyStorage.modifyEnergyStored(this.finallyReceiveEnergy);
    }

    // 注入Capability
    @ParametersAreNonnullByDefault
    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability,@Nullable EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(tank);
        }
        return super.getCapability(capability, facing);
    }
    @ParametersAreNonnullByDefault
    @Override
    public boolean hasCapability(Capability<?> capability,@Nullable EnumFacing facing) {
        return this.getCapability(capability, facing) != null;
    }

    @ParametersAreNonnullByDefault
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        tank.readFromNBT(compound);
        tank.setCapacity(compound.getInteger("fluidCapacity"));
        this.maxFluidDrain = this.getBasePowerGeneration()/2;
    }
    @ParametersAreNonnullByDefault
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        // 将相关数据保存到 compound 中
        NBTTagCompound NBT = tank.writeToNBT(super.writeToNBT(compound));
        NBT.setInteger("fluidCapacity", this.tank.getCapacity());
        return NBT;
    }

}
