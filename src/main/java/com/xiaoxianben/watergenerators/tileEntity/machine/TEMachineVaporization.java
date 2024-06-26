package com.xiaoxianben.watergenerators.tileEntity.machine;

import com.xiaoxianben.watergenerators.fluid.fluidTank.FluidTankBase;
import com.xiaoxianben.watergenerators.fluid.fluidTank.FluidTankFluidInput;
import com.xiaoxianben.watergenerators.recipe.recipeList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class TEMachineVaporization extends TEMachineBase {

    /**
     * 输入流体的tank
     */
    protected FluidTankFluidInput<FluidStack, FluidStack> fluidTankInt;
    /**
     * 输出流体的tank
     */
    protected FluidTankBase fluidTankOut;


    @SuppressWarnings("unused")
    public TEMachineVaporization() {
        this(5);
    }

    public TEMachineVaporization(float level) {
        super(level);
        fluidTankInt = new FluidTankFluidInput<>((int) (5000 * level), recipeList.recipeVaporization);
        fluidTankOut = new FluidTankBase((int) (5000 * level));

        fluidTankInt.setCanDrain(false);
        fluidTankInt.setCanFill(true);

        fluidTankOut.setCanDrain(true);
        fluidTankOut.setCanFill(false);
    }

    public FluidTankFluidInput<FluidStack, FluidStack> getFluidTankInt() {
        return fluidTankInt;
    }

    public FluidTankBase getFluidTankOut() {
        return fluidTankOut;
    }

    /**
     * 运行机器
     */
    private void runMachine() {

        FluidStack outputFluidStack = this.getFluidTankInt().getRecipeOutput();
        FluidStack inputFluidStack = this.getFluidTankInt().getRecipeFluidInput();

        open = true;
        this.modifyEnergyStored(recipeList.recipeVaporization.getEnergyDeplete(inputFluidStack));
        this.getFluidTankOut().fillInternal(outputFluidStack.copy(), true);
        this.getFluidTankInt().drainInternal(inputFluidStack.copy(), true);
    }

    private boolean canRunMachine() {
        if (this.getFluidTankInt().getRecipeOutput() == null) return false;
        FluidStack inputFluidStack = this.getFluidTankInt().getRecipeFluidInput();
        if (this.getFluidTankInt().drainInternal(inputFluidStack.copy(), false) == null) return false;

        FluidStack outFluid = this.getFluidTankOut().getFluid();
        return outFluid == null || outFluid.amount < this.getFluidTankOut().getCapacity() || this.getEnergyStored() >= recipeList.recipeVaporization.getEnergyDeplete(inputFluidStack.copy());
    }

    @Override
    public void updateStateInSever() {
        // 这段代码用于计算tempNumber的值，是否有小数部分。
        int tempNumber = ((int) (this.level * 10)) - ((int) this.level) * 10;

        int number = this.getWorld().rand.nextInt(tempNumber > 0 ? 2 : 1);

        for (int i = 0; i < ((int) this.getLevel()) + number; i++) {
            if (this.canRunMachine()) {
                this.open = true;
                this.runMachine();
            } else {
                this.open = false;
                break;
            }
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
