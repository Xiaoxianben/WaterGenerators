package com.xiaoxianben.watergenerators.tileEntity.machine;

import com.xiaoxianben.watergenerators.api.IComponentItemHandler;
import com.xiaoxianben.watergenerators.config.ConfigValue;
import com.xiaoxianben.watergenerators.fluids.fluidTank.FluidTankBase;
import com.xiaoxianben.watergenerators.fluids.fluidTank.FluidTankRecipe;
import com.xiaoxianben.watergenerators.items.ItemsComponent;
import com.xiaoxianben.watergenerators.items.itemHandler.ItemComponentHandler;
import com.xiaoxianben.watergenerators.items.itemHandler.ItemStackHandler;
import com.xiaoxianben.watergenerators.jsonRecipe.ModJsonRecipe;
import com.xiaoxianben.watergenerators.jsonRecipe.ingredients.FluidStackAndEnergy;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class TEMachineVaporization extends TEMachineBase implements IComponentItemHandler {

    protected final int runNumber = ConfigValue.basicAmountOfFluidToProduceEnergy / 5;
    /**
     * 输入流体的tank
     */
    protected FluidTankRecipe fluidTankInt;
    /**
     * 输出流体的tank
     */
    protected FluidTankBase fluidTankOut;
    protected ItemComponentHandler itemComponentHandler;


    @SuppressWarnings("unused")
    public TEMachineVaporization() {
        this(999);
    }

    public TEMachineVaporization(float level) {
        super(level);
        fluidTankInt = new FluidTankRecipe((int) (10000 * level), ModJsonRecipe.recipeVaporization);
        fluidTankOut = new FluidTankBase((int) (10000 * level));
        this.itemComponentHandler = new ItemComponentHandler(ItemComponentHandler.canPutItem_vaporization);

        fluidTankInt.setCanDrain(false);
        fluidTankInt.setCanFill(true);

        fluidTankOut.setCanDrain(true);
        fluidTankOut.setCanFill(false);
    }

    public FluidTankRecipe getFluidTankInt() {
        return fluidTankInt;
    }

    public FluidTankBase getFluidTankOut() {
        return fluidTankOut;
    }

    /**
     * 运行机器
     */
    @ParametersAreNonnullByDefault
    private void runMachine(FluidStack inputFluid, FluidStack outputFluid, int energyDe) {
        this.modifyEnergyStored(-energyDe);
        this.fluidTankOut.fillInternal(outputFluid.copy(), true);
        this.fluidTankInt.drainInternal(inputFluid.copy(), true);
    }

    @ParametersAreNonnullByDefault
    private int getNumberRun(FluidStack recipeInputFluid, FluidStack recipeOutputFluid, int energyDe) {
        if (getEnergyStoredLong() < energyDe) return 0;
        int outFluidNumber = (fluidTankOut.getCapacity() - fluidTankOut.getFluidAmount()) / recipeOutputFluid.amount;
        int inputFluidNumber = fluidTankInt.getFluidAmount() / recipeInputFluid.amount;
        long energyNumber = getEnergyStoredLong() / energyDe;
        return (int) Math.min(Math.min(outFluidNumber, inputFluidNumber), energyNumber);
    }

    @Override
    public void updateStateInSever() {
        this.open = false;
        FluidStack recipeFluidInput = this.fluidTankInt.getRecipeFluidInput();
        if (recipeFluidInput == null) return;
        FluidStackAndEnergy recipeOutput = this.fluidTankInt.getRecipeOutput();
        if (recipeOutput == null) return;

        int energyDepleteValue = -recipeOutput.getEnergyValue();

        // 这段代码用于计算运算次数。
        int numberRun = (int) Math.min(getNumberRun(recipeFluidInput, recipeOutput.getFluidStack1(), energyDepleteValue),
                this.getLevel() * runNumber * (itemComponentHandler.getComponentCount(ItemsComponent.component_efficiency) + 1));
        if (numberRun <= 0) {
            return;
        }

        this.open = true;
        for (int i = 0; i < numberRun; i++) {
            this.runMachine(recipeFluidInput, recipeOutput.getFluidStack1(), energyDepleteValue);
        }
    }

    // 注入Capability
    @ParametersAreNonnullByDefault
    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(new FluidHandlerConcatenate(fluidTankInt, fluidTankOut));
        }
        return super.getCapability(capability, facing);
    }

    // NBT
    @Override
    public NBTTagCompound getCapabilityNBT() {
        NBTTagCompound nbtTagCompound = super.getCapabilityNBT();

        // 将相关数据保存到 compound 中
        NBTTagCompound nbtFluidTank = new NBTTagCompound();
        nbtFluidTank.setTag("Int", fluidTankInt.writeToNBT(new NBTTagCompound()));
        nbtFluidTank.setTag("Out", fluidTankOut.writeToNBT(new NBTTagCompound()));
        nbtTagCompound.setTag("FluidTank", nbtFluidTank);

        nbtTagCompound.setTag("ItemHandler", itemComponentHandler.serializeNBT());

        return nbtTagCompound;
    }

    @Override
    public void readCapabilityNBT(NBTTagCompound NBT) {
        // 从 compound 中读取相关数据
        super.readCapabilityNBT(NBT);

        NBTTagCompound nbtFluidTank = NBT.getCompoundTag("FluidTank");
        fluidTankInt.readFromNBT(nbtFluidTank.getCompoundTag("Int"));
        fluidTankOut.readFromNBT(nbtFluidTank.getCompoundTag("Out"));

        itemComponentHandler.deserializeNBT(NBT.getCompoundTag("ItemHandler"));
    }

    @Override
    public NBTTagCompound getItemNbt() {
        NBTTagCompound NBT = super.getItemNbt();

        NBTTagCompound inputFluidNbt = new NBTTagCompound();
        if (this.fluidTankInt.getFluid() != null) {
            this.fluidTankInt.writeToNBT(inputFluidNbt);
        }
        NBT.setTag("inputFluid", inputFluidNbt);

        NBTTagCompound outputFluidNbt = new NBTTagCompound();
        if (this.fluidTankOut.getFluid() != null) {
             this.fluidTankOut.writeToNBT(outputFluidNbt);
        }
        NBT.setTag("outputFluid", outputFluidNbt);

        return NBT;
    }

    @Override
    public void readItemNbt(NBTTagCompound NBT) {
        super.readItemNbt(NBT);

        NBTTagCompound inputFluidNbt = NBT.getCompoundTag("inputFluid");
        this.fluidTankInt.readFromNBT(inputFluidNbt);

        NBTTagCompound outputFluidNbt = NBT.getCompoundTag("outputFluid");
        this.fluidTankOut.readFromNBT(outputFluidNbt);
    }

    @Override
    public ItemStackHandler getComponentItemHandler() {
        return itemComponentHandler;
    }
}
