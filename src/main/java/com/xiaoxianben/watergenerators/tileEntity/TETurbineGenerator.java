package com.xiaoxianben.watergenerators.tileEntity;

import com.xiaoxianben.watergenerators.liquid.EnergyLiquid;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidBlock;

public class TETurbineGenerator extends TEEnergyBasic {

    public TETurbineGenerator() {
        this((int) (Math.pow(2, 31) - 1), 0, "");
    }
    public TETurbineGenerator(int capacity, int basePowerGeneration, String level) {
        super(capacity, basePowerGeneration, level);
    }

    public float getLiquidLeve(World world, BlockPos pos, float liquidLeve) {
        BlockPos upPos = pos.up();
        IBlockState upBlockState = world.getBlockState(upPos);
        Block upBlock = upBlockState.getBlock();
        if (EnergyLiquid.containsKey(upBlock)) {
            float liquidRF = EnergyLiquid.getEnergyFromLiquid(upBlock);
            if (upBlock instanceof BlockLiquid) {
                liquidLeve += BlockLiquid.getBlockLiquidHeight(upBlockState, world, upPos) * liquidRF;
            } else if (upBlock instanceof IFluidBlock) {
                liquidLeve += ((IFluidBlock) upBlock).getFilledPercentage(world, upPos) * liquidRF;
            }
            return getLiquidLeve(world, upPos, liquidLeve);
        }
        return liquidLeve;
    }

    @Override
    public void updateEnergy(int updateEnergy) {
        if (this.energyStorage.getEnergyStored() < this.energyStorage.getMaxEnergyStored()) {
            float liquidLeve = this.getLiquidLeve(this.getWorld(), this.getPos(), 0);
            this.finallyReceiveEnergy = (int) (updateEnergy * liquidLeve);
        } else {
            this.finallyReceiveEnergy = 0;
        }
        this.energyStorage.modifyEnergyStored(this.finallyReceiveEnergy);
    }

}
