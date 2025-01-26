package com.xiaoxianben.watergenerators.tileEntity;

import com.xiaoxianben.watergenerators.api.ICapabilityNBT;
import com.xiaoxianben.watergenerators.api.INetworkUpdateNBT;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public abstract class TEBase extends TileEntity implements INetworkUpdateNBT, ICapabilityNBT, ITickable {

    @Override
    public void update() {
        if (this.world.isRemote) {
            this.updateStateInClient();
            return;
        }

        this.updateStateInSever();

        SPacketUpdateTileEntity packet = this.getUpdatePacket();
        // 获取当前正在“追踪”目标 TileEntity 所在区块的玩家。
        // 之所以这么做，是因为在逻辑服务器上，不是所有的玩家都需要获得某个 TileEntity 更新的信息。
        // 比方说，有一个玩家和需要同步的 TileEntity 之间差了八千方块，或者压根儿就不在同一个维度里。
        // 这个时候就没有必要同步数据——强行同步数据实际上也没有什么用，因为大多数时候这样的操作都应会被
        // World.isBlockLoaded（func_175667_e）的检查拦截下来，避免意外在逻辑客户端上加载多余的区块。
        PlayerChunkMapEntry trackingEntry = ((WorldServer) this.world).getPlayerChunkMap().getEntry(this.pos.getX() >> 4, this.pos.getZ() >> 4);
        if (trackingEntry != null) {
            for (EntityPlayerMP player : trackingEntry.getWatchingPlayers()) {
                player.connection.sendPacket(packet);
            }
        }

    }

    @Nonnull
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setTag("capabilityNBT", this.getCapabilityNBT());
        nbtTagCompound.setTag("network", this.getNetworkUpdateNBT());

        return new SPacketUpdateTileEntity(pos, 1, nbtTagCompound);
    }

    @ParametersAreNonnullByDefault
    @Override
    public void onDataPacket(net.minecraft.network.NetworkManager net, SPacketUpdateTileEntity pkt) {

        NBTTagCompound nbtTagCompound = pkt.getNbtCompound();
        this.readCapabilityNBT(nbtTagCompound.getCompoundTag("capabilityNBT"));
        this.readNetworkUpdateNBT(nbtTagCompound.getCompoundTag("network"));

    }

    // NBT
    @ParametersAreNonnullByDefault
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        // 从 compound 中读取相关数据
        this.readCapabilityNBT(compound.getCompoundTag("Capability"));

        super.readFromNBT(compound);
    }

    @ParametersAreNonnullByDefault
    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        // 将相关数据保存到 compound 中
        compound.setTag("Capability", this.getCapabilityNBT());

        return super.writeToNBT(compound);
    }

    @Nonnull
    @Override
    public NBTTagCompound getUpdateTag() {
        return this.writeToNBT(new NBTTagCompound());
    }

    // capability
    @ParametersAreNonnullByDefault
    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return this.getCapability(capability, facing) != null;
    }


    /**
     * 在 服务端 进行状态更新。
     */
    public abstract void updateStateInSever();

    /**
     * 在 客户端 进行状态更新。
     */
    public void updateStateInClient() {
    }
}
