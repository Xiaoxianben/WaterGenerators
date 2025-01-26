package com.xiaoxianben.watergenerators.tileEntity.machine;

import com.xiaoxianben.watergenerators.config.ConfigValue;
import com.xiaoxianben.watergenerators.fluids.fluidTank.FluidTankBase;
import com.xiaoxianben.watergenerators.fluids.fluidTank.FluidTankInput;
import com.xiaoxianben.watergenerators.jsonRecipe.ModJsonRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class TEMachineVaporization extends TEMachineBase {

    protected final int runNumber = ConfigValue.basicAmountOfFluidToProduceEnergy / 5;
    /**
     * 输入流体的tank
     */
    protected FluidTankInput fluidTankInt;
    /**
     * 输出流体的tank
     */
    protected FluidTankBase fluidTankOut;


    @SuppressWarnings("unused")
    public TEMachineVaporization() {
        this(999);
    }

    public TEMachineVaporization(float level) {
        super(level);
        fluidTankInt = new FluidTankInput((int) (1000 * level), ModJsonRecipe.recipeVaporization);
        fluidTankOut = new FluidTankBase((int) (1000 * level));

        fluidTankInt.setCanDrain(false);
        fluidTankInt.setCanFill(true);

        fluidTankOut.setCanDrain(true);
        fluidTankOut.setCanFill(false);
    }

    public FluidTankInput getFluidTankInt() {
        return fluidTankInt;
    }

    public FluidTankBase getFluidTankOut() {
        return fluidTankOut;
    }

    /**
     * 运行机器
     */
    private void runMachine(FluidStack inputFluid, FluidStack outputFluid, int energyDe) {
        this.modifyEnergyStored(-energyDe);
        this.getFluidTankOut().fillInternal(outputFluid.copy(), true);
        this.getFluidTankInt().drainInternal(inputFluid.copy(), true);
    }

    private int getNumberRun(FluidStack recipeInputFluid, FluidStack recipeOutputFluid, int energyDe) {
        if (recipeInputFluid == null || getEnergyStoredLong() <= 0) return 0;
        int outFluidNumber = Math.max(getFluidTankOut().getCapacity() - getFluidTankOut().getFluidAmount(), recipeOutputFluid.amount) / recipeOutputFluid.amount;
        int inputFluidNumber = getFluidTankInt().getFluidAmount() / recipeInputFluid.amount;
        long energyNumber = getEnergyStoredLong() / energyDe;
        return (int) Math.min(Math.min(outFluidNumber, inputFluidNumber), energyNumber);
    }

    @Override
    public void updateStateInSever() {
        this.open = false;
        FluidStack recipeFluidInput = this.getFluidTankInt().getRecipeFluidInput();
        if (recipeFluidInput == null) return;
        FluidStack recipeOutput = this.getFluidTankInt().getRecipeOutput();
        int energyDeplete = ModJsonRecipe.recipeVaporization.getEnergyDeplete(recipeFluidInput);

        // 这段代码用于计算运算次数。
        int numberRun = (int) Math.min(getNumberRun(recipeFluidInput, recipeOutput, energyDeplete), this.getLevel() * runNumber);
        if (numberRun <= 0) {
            return;
        }

        this.open = true;
        for (int i = 0; i < numberRun; i++) {
            this.runMachine(recipeFluidInput, recipeOutput, energyDeplete);
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

        return nbtTagCompound;
    }

    @Override
    public void readCapabilityNBT(NBTTagCompound NBT) {
        // 从 compound 中读取相关数据
        super.readCapabilityNBT(NBT);

        NBTTagCompound nbtFluidTank = NBT.getCompoundTag("FluidTank");
        fluidTankInt.readFromNBT(nbtFluidTank.getCompoundTag("Int"));
        fluidTankOut.readFromNBT(nbtFluidTank.getCompoundTag("Out"));
    }

    @Override
    public NBTTagCompound getItemNbt() {
        NBTTagCompound NBT = super.getItemNbt();

        NBTTagCompound inputFluidNbt = new NBTTagCompound();
        if (this.fluidTankInt.getFluid() != null) {
            inputFluidNbt = this.fluidTankInt.getFluid().writeToNBT(inputFluidNbt);
        }
        NBT.setTag("inputFluid", inputFluidNbt);

        NBTTagCompound outputFluidNbt = new NBTTagCompound();
        if (this.fluidTankOut.getFluid() != null) {
            outputFluidNbt = this.fluidTankOut.getFluid().writeToNBT(outputFluidNbt);
        }
        NBT.setTag("outputFluid", outputFluidNbt);

        return NBT;
    }

    @Override
    public void readItemNbt(NBTTagCompound NBT) {
        super.readItemNbt(NBT);

        NBTTagCompound inputFluidNbt = NBT.getCompoundTag("inputFluid");
        this.getFluidTankInt().setFluid(FluidStack.loadFluidStackFromNBT(inputFluidNbt));

        NBTTagCompound outputFluidNbt = NBT.getCompoundTag("outputFluid");
        this.getFluidTankOut().setFluid(FluidStack.loadFluidStackFromNBT(outputFluidNbt));
    }

}
