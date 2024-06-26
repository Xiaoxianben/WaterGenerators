package com.xiaoxianben.watergenerators.tileEntity.generator;

import com.xiaoxianben.watergenerators.config.ConfigValue;
import com.xiaoxianben.watergenerators.enery.EnergyLiquid;
import com.xiaoxianben.watergenerators.fluid.fluidTank.FluidTankGenerator;
import com.xiaoxianben.watergenerators.items.ItemsComponent;
import com.xiaoxianben.watergenerators.items.itemHandler.ItemComponentHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.math.BigDecimal;
import java.util.ArrayList;

public class TEGeneratorFluid extends TEGeneratorBase {

    /**
     * 产生能量所需的基础流体量，大部分情况下，默认为 1000 mB
     * 蒸汽为 1 mB
     */
    public int basicAmountOfFluidToProduceEnergy = ConfigValue.basicAmountOfFluidToProduceEnergy;
    public FluidTankGenerator fluidTank;

    private int maxFluidDrain;


    @SuppressWarnings("unused")
    public TEGeneratorFluid() {
        this(0, 999);
    }

    public TEGeneratorFluid(long basePowerGeneration, float level) {
        super(basePowerGeneration, level);

        this.fluidTank = new FluidTankGenerator(new BigDecimal(65 * basicAmountOfFluidToProduceEnergy).intValue(), new ArrayList<>(EnergyLiquid.liquidEnergy.keySet()));
        this.fluidTank.setCanDrain(false);
        this.itemComponentHandler = new ItemComponentHandler(ItemComponentHandler.canPutItem_fluidGenerator);
    }


    /**
     * 获取当前存储的流体量
     */
    public float getFluidAmount() {
        return this.fluidTank.getFluidAmount();
    }

    /**
     * 获取当前可存储的最大存储量
     */
    public float getCapacity() {
        return this.fluidTank.getCapacity();
    }

    /**
     * 获取当前的流体倍率
     */
    public float getFluidMagnification() {
        if (this.fluidTank.getFluid() != null) {
            return EnergyLiquid.getEnergyFromLiquid(this.fluidTank.getFluid().getFluid());
        }
        return 0.0f;
    }


    @Override
    protected long updateEnergy() {
        long receiveEnergy = 0;
        FluidStack fluid = this.fluidTank.getFluid();
        if (this.getEnergyStoredLong() < this.getMaxEnergyStoredLong() && fluid != null && fluid.amount >= this.basicAmountOfFluidToProduceEnergy) {
            long realPowerGeneration = (long) (this.getRealPowerGeneration() * this.getFluidMagnification());

            int theAmountOfFluidRequired = (int) (Math.max((this.getMaxEnergyStoredLong() - this.getEnergyStoredLong()) / realPowerGeneration, 1) * this.basicAmountOfFluidToProduceEnergy);
            int canDrainAmount = Math.min(fluid.amount / this.basicAmountOfFluidToProduceEnergy * this.basicAmountOfFluidToProduceEnergy, this.maxFluidDrain);

            // 尝试从存储罐中抽出指定数量的液体
            FluidStack fluidStack = fluidTank.drainInternal(Math.min(theAmountOfFluidRequired, canDrainAmount), true);


            // 如果抽出了液体，计算接收的电能，否则为0
            if (fluidStack != null) {
                receiveEnergy = (fluidStack.amount / this.basicAmountOfFluidToProduceEnergy) * realPowerGeneration;
            }

            receiveEnergy = Math.max(1, receiveEnergy);
        }
        return this.modifyEnergyStored(receiveEnergy);
    }


    @Override
    public void updateStateInSever() {
        this.maxFluidDrain = (this.itemComponentHandler.getComponentCount(ItemsComponent.component_extract) + 1) * this.basicAmountOfFluidToProduceEnergy;
        super.updateStateInSever();
    }


    // 注入Capability
    @ParametersAreNonnullByDefault
    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(fluidTank);

        } else if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemComponentHandler);
        }
        return super.getCapability(capability, facing);
    }

    // NBT
    @Override
    public NBTTagCompound getItemNbt() {
        NBTTagCompound NBT = super.getItemNbt();
        if (this.fluidTank.getFluid() != null) {
            this.fluidTank.getFluid().writeToNBT(NBT);
        }
        return NBT;
    }

    @Override
    public void readItemNbt(NBTTagCompound NBT) {
        super.readItemNbt(NBT);
        FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(NBT);
        if (fluidStack != null && this.fluidTank.canFillFluidType(fluidStack)) {
            this.fluidTank.setFluid(FluidStack.loadFluidStackFromNBT(NBT));
        }
    }

    @Override
    public NBTTagCompound getCapabilityNBT() {
        NBTTagCompound nbtTagCompound = super.getCapabilityNBT();

        NBTTagCompound nbtFluidTank = fluidTank.writeToNBT(new NBTTagCompound());
        nbtTagCompound.setTag("FluidTank", nbtFluidTank);

        return nbtTagCompound;
    }

    @Override
    public void readCapabilityNBT(NBTTagCompound NBT) {
        super.readCapabilityNBT(NBT);

        NBTTagCompound nbtFluidTank = NBT.getCompoundTag("FluidTank");
        fluidTank.readFromNBT(nbtFluidTank);
    }

}
