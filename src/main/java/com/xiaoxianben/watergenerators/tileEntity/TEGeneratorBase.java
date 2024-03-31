package com.xiaoxianben.watergenerators.tileEntity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nonnull;
import java.util.Objects;

public class TEGeneratorBase extends TEEnergyBasic {
    public String level;
    /**
     * 每tick的实际发电量
     */
    public long finallyReceiveEnergy;
    public long finallyExtractEnergy;
    public long basePowerGeneration;
    public long[] finallyExtractEnergyList = new long[]{0, 0, 0, 0, 0, 0};

    public TEGeneratorBase() {
        this(Long.MAX_VALUE, 0, "");
    }

    /**
     * 不接受能量，只传输能量
     */
    public TEGeneratorBase(long capacity, long basePowerGeneration, String level) {
        super(capacity, 0, Integer.MAX_VALUE);
        this.basePowerGeneration = basePowerGeneration;
        this.level = level;
    }


    public long getBasePowerGeneration() {
        return this.basePowerGeneration;
    }

    public void setBasePowerGeneration(long basePowerGeneration) {
        this.basePowerGeneration = basePowerGeneration;
    }

    public long getFinallyReceiveEnergySpecific() {
        return this.finallyReceiveEnergy;
    }

    public long getFinallyExtractEnergySpecific() {
        long rf = 0;
        for (long temp : this.finallyExtractEnergyList) {
            rf += temp;
        }
        return rf;
    }

    protected String[] getRoughData(int I) {
        String[] strings;

        // 如果最终接收能量大于800000000，则将最终接收能量转换为G
        if (I > 800000000) strings = new String[]{String.format("%.2f", I / 1000000000.0), "9", "G"};
            // 如果最终接收能量大于800000，则将最终接收能量转换为M
        else if (I > 800000) strings = new String[]{String.format("%.2f", I / 1000000.0), "6", "M"};
            // 如果最终接收能量大于800，则将最终接收能量转换为K
        else if (I > 800) strings = new String[]{String.format("%.2f", I / 1000.0), "3", "K"};
            // 否则，将最终接收能量转换为0
        else strings = new String[]{String.valueOf(I), "0", ""};

        return strings;
    }

    protected String[] getRoughData(long I) {
        String[] strings;

        // 如果最终接收能量大于800000000，则将最终接收能量转换为G
        if (I > 800000000) strings = new String[]{String.format("%.2f", I / 1000000000.0), "9", "G"};
            // 如果最终接收能量大于800000，则将最终接收能量转换为M
        else if (I > 800000) strings = new String[]{String.format("%.2f", I / 1000000.0), "6", "M"};
            // 如果最终接收能量大于800，则将最终接收能量转换为K
        else if (I > 800) strings = new String[]{String.format("%.2f", I / 1000.0), "3", "K"};
            // 否则，将最终接收能量转换为0
        else strings = new String[]{String.valueOf(I), "0", ""};

        return strings;
    }

    /**
     * @return 获取最终接收能量的粗略值
     */
    public String[] getRoughFinallyReceiveEnergy() {
        return this.getRoughData(this.getFinallyReceiveEnergySpecific());
    }

    /**
     * @return 获取最终提取能量的粗略值
     */
    public String[] getRoughFinallyExtractEnergy() {
        return this.getRoughData(this.getFinallyExtractEnergySpecific());
    }

    /**
     * @return 获取现存能量的粗略值
     */
    public String[] getRoughEnergyStored() {
        return this.getRoughData(this.getEnergyStored());
    }

    /**
     * @return 获取最大能量储存的粗略值
     */
    public String[] getRoughMaxEnergyStored() {
        return this.getRoughData(this.getMaxEnergyStored());
    }

    protected void onNeighborChanged(World world) {
        this.transferEnergyFacings = new EnumFacing[this.transferEnergyFacings.length];
        this.initExtractEnergyList();
        byte fa = 0;
        for (EnumFacing facing : EnumFacing.VALUES) {
            if (this.canConnectEnergy(facing)) {
                TileEntity adjacentTE = world.getTileEntity(this.getPos().offset(facing));
                if (this.canTransferEnergy(adjacentTE, facing.getOpposite())) {
                    this.transferEnergyFacings[fa++] = facing;
                }
            }
        }
    }

    protected boolean canTransferEnergy(TileEntity TE, EnumFacing facing) {
        if (TE == null) return false;
        else if (TE.hasCapability(CapabilityEnergy.ENERGY, facing)) {
            return Objects.requireNonNull(TE.getCapability(CapabilityEnergy.ENERGY, facing)).canReceive();
        }
        return false;
    }

    protected void transferEnergy(TileEntity adjacentTE, EnumFacing facing) {
        int temp = 0;
        if (adjacentTE.hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite())) {
            temp = (Objects.requireNonNull(adjacentTE.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite()))).
                    receiveEnergy(this.getEnergyStored(), false);
        }
        this.finallyExtractEnergyList[facing.getIndex()] += this.extractEnergy(temp, false);
    }

    protected void initExtractEnergyList() {
        this.finallyExtractEnergyList = new long[]{0, 0, 0, 0, 0, 0};
    }

    protected void updateEnergy() {
        int maxEnergy = energyStorage.getMaxEnergyStored();
        int energy = energyStorage.getEnergyStored();
        if (energy < maxEnergy) {
            this.finallyReceiveEnergy = energy;
        } else {
            this.finallyReceiveEnergy = 0;
        }
        modifyEnergyStored(this.finallyReceiveEnergy);
    }

    @Override
    protected void updateState() {
        finallyExtractEnergy = this.getFinallyExtractEnergySpecific();
    }

    // NBT
    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound) {
        NBTTagCompound nbtTagCompound = super.writeToNBT(compound);

        NBTTagCompound nbtGenerator = new NBTTagCompound();
        nbtGenerator.setLong("basePowerGeneration", this.getBasePowerGeneration());
        nbtGenerator.setString("level", this.level);
        nbtTagCompound.setTag("Attribute", nbtGenerator);

        return nbtTagCompound;
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
        nbtTagCompound.setLong("finallyExtractEnergy", this.getFinallyExtractEnergySpecific());
        return nbtTagCompound;
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound NBT) {
        NBTTagCompound nbtGenerator = NBT.getCompoundTag("Attribute");
        this.level = nbtGenerator.getString("level");
        this.setBasePowerGeneration(nbtGenerator.getLong("basePowerGeneration"));

        super.readFromNBT(NBT);
    }

}
