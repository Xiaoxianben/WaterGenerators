package com.xiaoxianben.watergenerators.tileEntity;

import com.xiaoxianben.watergenerators.fluid.FluidTankBase;
import com.xiaoxianben.watergenerators.fluid.FluidTankFluidInput;
import com.xiaoxianben.watergenerators.recipe.recipeList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;
import java.util.Random;

public class TEMachineVaporization extends TEMachineBase {

    /**
     * 输入流体的tank
     */
    protected FluidTankFluidInput<FluidStack, FluidStack> fluidTankInt;
    /**
     * 输出流体的tank
     */
    protected FluidTankBase fluidTankOut;

    public TEMachineVaporization() {
        this(Long.MAX_VALUE, 5);
    }

    public TEMachineVaporization(long capacity, float level) {
        super(capacity, level);
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
//        FluidStack intFluid = this.getFluidTankInt().getFluid();
        FluidStack outFluid = this.getFluidTankOut().getFluid();

        FluidStack tryDrainFluidStack = this.getFluidTankInt().drainInternal(this.getFluidTankInt().getInputCount(), false);
        boolean canFill = outFluid == null || outFluid.amount < this.getFluidTankOut().getCapacity();
        boolean hasEnoughEnergy = this.getEnergyStored() >= 2 * this.getFluidTankInt().getInputCount();

        // 判断容器是否可以运行
        if (tryDrainFluidStack != null && canFill && hasEnoughEnergy) {
            open = true;
            this.modifyEnergyStored(-2L);
            FluidStack outputFluidStack = this.getFluidTankInt().getRecipeOutput();
            this.getFluidTankOut().fillInternal(new FluidStack(Objects.requireNonNull(outputFluidStack).getFluid(), outputFluidStack.amount), true);
            this.getFluidTankInt().drainInternal(this.getFluidTankInt().getInputCount(), true);
        } else
            open = false;
    }

    @Override
    protected void updateState() {
        // 这段代码用于计算tempNumber的值，是否有小数部分。
        int tempNumber = ((int) (this.level * 10)) - ((int) this.level) * 10;

        Random random = new Random();
        int number = random.nextInt(tempNumber / 5 + 1);

        for (int i = 0; i < ((int) this.getLevel()) + number; i++) {
            this.runMachine();
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
}
