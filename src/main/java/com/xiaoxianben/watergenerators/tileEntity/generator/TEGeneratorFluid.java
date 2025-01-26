package com.xiaoxianben.watergenerators.tileEntity.generator;

import com.xiaoxianben.watergenerators.config.ConfigValue;
import com.xiaoxianben.watergenerators.fluids.fluidTank.FluidTankGenerator;
import com.xiaoxianben.watergenerators.items.ItemsComponent;
import com.xiaoxianben.watergenerators.items.itemHandler.ItemComponentHandler;
import com.xiaoxianben.watergenerators.jsonRecipe.ModJsonRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class TEGeneratorFluid extends TEGeneratorBase {

    /**
     * 产生能量所需的基础流体量，默认为 1000 mB
     */
    public final int basicAmountOfFluidToProduceEnergy = ConfigValue.basicAmountOfFluidToProduceEnergy;
    public FluidTankGenerator fluidTank;

    private int maxFluidDrain;


    @SuppressWarnings("unused")
    public TEGeneratorFluid() {
        this(Long.MAX_VALUE);
    }

    public TEGeneratorFluid(long basePowerGeneration) {
        super(basePowerGeneration);
        this.fluidTank = new FluidTankGenerator(65 * basicAmountOfFluidToProduceEnergy, ModJsonRecipe.recipeFluidGenerator.getInputs());
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
     * 获取当前的流体的发电倍率
     */
    public float getEnergyMagnification() {
        if (this.fluidTank.getFluid() != null) {
            return ModJsonRecipe.recipeFluidGenerator.getOutput(this.fluidTank.getFluid().getFluid());
        }
        return 0.0f;
    }

    @Override
    public long getFinallyPowerGeneration() {
        return (long) (super.getFinallyPowerGeneration() * getEnergyMagnification());
    }

    @Override
    protected long updateEnergy() {
        long receiveEnergy = 0;
        FluidStack fluid = this.fluidTank.getFluid();
        if (fluid != null &&
                this.getEnergyStoredLong() < this.getMaxEnergyStoredLong() &&
                fluid.amount >= this.basicAmountOfFluidToProduceEnergy) {
            // 一次 basicAmountOfFluidToProduceEnergy 所得到的能量
            long realPowerGeneration = this.getFinallyPowerGeneration();

            int theAmountOfFluidRequired = (int) (Math.max((this.getMaxEnergyStoredLong() - this.getEnergyStoredLong()) / realPowerGeneration, 1) * this.basicAmountOfFluidToProduceEnergy);
            int canDrainAmount = Math.min(fluid.amount / this.basicAmountOfFluidToProduceEnergy * this.basicAmountOfFluidToProduceEnergy, this.maxFluidDrain);

            // 尝试从存储罐中抽出指定数量的液体
            FluidStack fluidStack = fluidTank.drainInternal(Math.min(theAmountOfFluidRequired, canDrainAmount), true);


            // 如果抽出了液体，计算接收的电能，否则为0
            if (fluidStack != null) {
                receiveEnergy = (fluidStack.amount / this.basicAmountOfFluidToProduceEnergy) * realPowerGeneration;
            }

        }
        return this.modifyEnergyStored(receiveEnergy);
    }


    @Override
    public void updateStateInSever() {
        int extractCount = (this.itemComponentHandler.getComponentCount(ItemsComponent.component_extract) + 1);
        this.maxFluidDrain = extractCount * this.basicAmountOfFluidToProduceEnergy;
        this.energyStorage.setCapacity(Math.max(this.getFinallyPowerGeneration() * extractCount * 2, this.getEnergyStoredLong()));

        super.updateStateInSever();
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
