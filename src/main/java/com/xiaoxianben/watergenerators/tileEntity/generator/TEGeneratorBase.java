package com.xiaoxianben.watergenerators.tileEntity.generator;

import com.xiaoxianben.watergenerators.items.component.ItemsComponent;
import com.xiaoxianben.watergenerators.items.itemHandler.ItemComponentHandler;
import com.xiaoxianben.watergenerators.tileEntity.TEEnergyBasic;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class TEGeneratorBase extends TEEnergyBasic {
    /**
     * 发电机等级
     */
    public float level;
    /**
     * 发电机的基础发电量
     */
    public long basePowerGeneration;

    protected ItemComponentHandler itemComponentHandler;

    /**
     * 不接受能量，只传输能量
     */
    public TEGeneratorBase(long basePowerGeneration, float level) {
        super(basePowerGeneration * 500, 0, Integer.MAX_VALUE);

        this.basePowerGeneration = basePowerGeneration;
        this.level = level;
        this.itemComponentHandler = new ItemComponentHandler(level, ItemComponentHandler.canPutItem_generator);
    }


    public long getRealPowerGeneration() {
        return this.basePowerGeneration * (this.itemComponentHandler.getComponentCount(ItemsComponent.component_powerGeneration) + 1);
    }

    protected long updateEnergy() {
        return modifyEnergyStored((long) (this.basePowerGeneration * this.level));
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


    // NBT
    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound) {
        NBTTagCompound nbtTagCompound = super.writeToNBT(compound);

        NBTTagCompound nbtGenerator = new NBTTagCompound();
        nbtGenerator.setLong("basePowerGeneration", this.basePowerGeneration);
        nbtGenerator.setFloat("level", this.level);
        nbtTagCompound.setTag("Attribute", nbtGenerator);

        return nbtTagCompound;
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound NBT) {
        NBTTagCompound nbtGenerator = NBT.getCompoundTag("Attribute");
        this.basePowerGeneration = nbtGenerator.getLong("basePowerGeneration");
        this.level = nbtGenerator.getFloat("level");

        super.readFromNBT(NBT);
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
        this.itemComponentHandler.level = this.level;
        this.updateState();
    }

    @Override
    public void updateNBT(NBTTagCompound NBT) {
        this.finallyReceiveEnergy = NBT.getLong("finallyReceiveEnergy");
        this.finallyExtractEnergy = NBT.getLong("finallyExtractEnergy");
    }

    @Override
    public NBTTagCompound getUpdateNBT() {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setLong("finallyReceiveEnergy", this.finallyReceiveEnergy);
        nbtTagCompound.setLong("finallyExtractEnergy", this.getFinallyExtractEnergy());
        return nbtTagCompound;
    }

}
