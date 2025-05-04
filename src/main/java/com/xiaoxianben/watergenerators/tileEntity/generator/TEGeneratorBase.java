package com.xiaoxianben.watergenerators.tileEntity.generator;

import com.xiaoxianben.watergenerators.api.IComponentItemHandler;
import com.xiaoxianben.watergenerators.items.ItemsComponent;
import com.xiaoxianben.watergenerators.items.itemHandler.ItemComponentHandler;
import com.xiaoxianben.watergenerators.items.itemHandler.ItemStackHandler;
import com.xiaoxianben.watergenerators.tileEntity.TEEnergyBasic;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public abstract class TEGeneratorBase extends TEEnergyBasic implements IComponentItemHandler {

    /**
     * 发电机的基础发电量
     */
    public long basePowerGeneration;

    protected ItemComponentHandler itemComponentHandler;

    /**
     * 不接受能量，只传输能量
     */
    public TEGeneratorBase(long basePowerGeneration) {
        super(basePowerGeneration, false, true);

        this.basePowerGeneration = basePowerGeneration;
        this.itemComponentHandler = new ItemComponentHandler(ItemComponentHandler.canPutItem_generator);
    }


    /**
     * 获取一次发电得到的最终的发电量。
     * */
    public long getFinallyPowerGeneration() {
        return this.basePowerGeneration * (this.itemComponentHandler.getComponentCount(ItemsComponent.component_powerGeneration) + 1);
    }

    @Override
    public ItemStackHandler getComponentItemHandler() {
        return itemComponentHandler;
    }

    // 注入Capability
    @ParametersAreNonnullByDefault
    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemComponentHandler);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public NBTTagCompound getCapabilityNBT() {
        NBTTagCompound nbtTagCompound = super.getCapabilityNBT();

        nbtTagCompound.setTag("ItemHandler", itemComponentHandler.serializeNBT());

        return nbtTagCompound;
    }

    @Override
    public void readCapabilityNBT(NBTTagCompound NBT) {
        super.readCapabilityNBT(NBT);

        itemComponentHandler.deserializeNBT(NBT.getCompoundTag("ItemHandler"));
    }

    // NBT
    @Override
    public void readFromNBT(@Nonnull NBTTagCompound NBT) {
        NBTTagCompound nbtGenerator = NBT.getCompoundTag("Attribute");
        this.basePowerGeneration = nbtGenerator.getLong("basePowerGeneration");

        super.readFromNBT(NBT);
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound) {
        NBTTagCompound nbtTagCompound = super.writeToNBT(compound);

        NBTTagCompound nbtGenerator = new NBTTagCompound();
        nbtGenerator.setLong("basePowerGeneration", this.basePowerGeneration);
        nbtTagCompound.setTag("Attribute", nbtGenerator);

        return nbtTagCompound;
    }

    @Override
    public NBTTagCompound getNetworkUpdateNBT() {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setLong("finallyReceiveEnergy", this.finallyReceiveEnergy);
        nbtTagCompound.setLong("finallyExtractEnergy", this.finallyExtractEnergy);
        return nbtTagCompound;
    }

    @Override
    public void readNetworkUpdateNBT(NBTTagCompound NBT) {
        this.finallyReceiveEnergy = NBT.getLong("finallyReceiveEnergy");
        this.finallyExtractEnergy = NBT.getLong("finallyExtractEnergy");
    }

}
