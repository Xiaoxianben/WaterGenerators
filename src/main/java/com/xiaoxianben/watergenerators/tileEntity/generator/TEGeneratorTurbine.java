package com.xiaoxianben.watergenerators.tileEntity.generator;

import com.xiaoxianben.watergenerators.enery.EnergyLiquid;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;

public class TEGeneratorTurbine extends TEGeneratorBase {

    @SuppressWarnings("unused")
    public TEGeneratorTurbine() {
        this(0, 0);
    }

    public TEGeneratorTurbine(long basePowerGeneration, float level) {
        super(basePowerGeneration, level);
    }

    public float getLiquidHeight() {
        float liquidLeve = 0;
        // 获取上方的位置
        Iterable<BlockPos> listBlockPos = BlockPos.getAllInBox(this.getPos().add(0, 1, 0), this.getPos().add(0, this.getWorld().getActualHeight() - this.getPos().getY(), 0));
        for (BlockPos blockPos : listBlockPos) {
            Fluid fluid = FluidRegistry.lookupFluidForBlock(this.getWorld().getBlockState(blockPos).getBlock());
            if (EnergyLiquid.containsKey(fluid) && fluid.getDensity() > 0) {
                liquidLeve += EnergyLiquid.getEnergyFromLiquid(fluid) * getLiquidLevel(this.getWorld(), blockPos);
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
            int _level = (int) (BlockLiquid.getBlockLiquidHeight(world.getBlockState(pos), world, pos) * 10);
            if (_level <= 8) {
                return _level / 8.0f;
            }
            return _level;
        } else {
            return 0;
        }
    }

    @Override
    public long updateEnergy() {
        long receiveEnergy = 0;
        if (this.getEnergyStoredLong() < this.getMaxEnergyStoredLong()) {
            float liquidLeve = this.getLiquidHeight();
            receiveEnergy = (int) (this.getRealPowerGeneration() * liquidLeve);
        }
        return this.modifyEnergyStored(receiveEnergy);
    }

}
