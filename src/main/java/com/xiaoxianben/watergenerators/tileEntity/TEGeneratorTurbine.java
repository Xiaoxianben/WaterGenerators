package com.xiaoxianben.watergenerators.tileEntity;

import com.xiaoxianben.watergenerators.enery.EnergyLiquid;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;

public class TEGeneratorTurbine extends TEGeneratorBase {

    public TEGeneratorTurbine() {
        this((int) (Math.pow(2, 31) - 1), 0, "");
    }

    public TEGeneratorTurbine(long capacity, long basePowerGeneration, String level) {
        super(capacity, basePowerGeneration, level);
    }

    protected float getLiquidLeve(World world, BlockPos pos) {
        float liquidLeve = 0;
        // 获取上方的位置
        Iterable<BlockPos> listBlockPos = BlockPos.getAllInBox(pos.add(0, 1, 0), pos.add(0, world.getActualHeight(), 0));
        for (BlockPos blockPos : listBlockPos) {
            Fluid fluid = FluidRegistry.lookupFluidForBlock(world.getBlockState(blockPos).getBlock());
            if (EnergyLiquid.containsKey(fluid)) {
                liquidLeve += EnergyLiquid.getEnergyFromLiquid(fluid) * getLiquidLevel(world, blockPos);
            } else {
                break;
            }
        }
        return liquidLeve;
    }

    protected float getLiquidLevel(World world, BlockPos pos) {
        Block block = world.getBlockState(pos).getBlock();
        if (block instanceof IFluidBlock) {
            return ((IFluidBlock) block).getFilledPercentage(world, pos);
        } else if (block instanceof BlockLiquid) {
            return BlockLiquid.getBlockLiquidHeight(world.getBlockState(pos), world, pos);
        } else {
            return 0;
        }
    }

    @Override
    public void updateEnergy() {
        if (this.energyStorage.getEnergyStoredLong() < this.energyStorage.getMaxEnergyStoredLong()) {
            float liquidLeve = this.getLiquidLeve(this.getWorld(), this.getPos());
            this.finallyReceiveEnergy = (int) (this.getBasePowerGeneration() * liquidLeve);
        } else {
            this.finallyReceiveEnergy = 0;
        }
        this.energyStorage.modifyEnergyStored(this.finallyReceiveEnergy);
    }

    @Override
    public boolean canConnectEnergy(EnumFacing enumFacing) {
        return enumFacing != EnumFacing.UP;
    }

}
