package com.xiaoxianben.watergenerators.tileEntity.generator;

import com.xiaoxianben.watergenerators.jsonRecipe.ModJsonRecipe;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;

public class TEGeneratorTurbine extends TEGeneratorBase {

    protected float fluidHeight = 0;


    public TEGeneratorTurbine() {
        super();
    }

    public float getLiquidHeight() {
        float liquidHeight = 0;
        // 获取上方的位置
//        Iterable<BlockPos> listBlockPos = BlockPos.getAllInBox(this.getPos().add(0, 1, 0), new BlockPos(getPos().getX(), getWorld().getActualHeight(), getPos().getZ()));
//        for (BlockPos blockPos : listBlockPos) {
//            Fluid fluid = FluidRegistry.lookupFluidForBlock(this.getWorld().getBlockState(blockPos).getBlock());
//            if (ModJsonRecipe.recipeFluidGenerator.getInputs().contains(fluid) && fluid.getDensity() > 0) {
//                liquidHeight += ModJsonRecipe.recipeFluidGenerator.getOutput(fluid) * getLiquidLevel(this.getWorld(), blockPos);
//            } else {
//                break;
//            }
//        }
        BlockPos blockPos = this.getPos().up();
        Fluid fluid = FluidRegistry.lookupFluidForBlock(this.getWorld().getBlockState(blockPos).getBlock());
        while (ModJsonRecipe.recipeFluidGenerator.containsKay(fluid) && fluid.getDensity() > 0) {
            liquidHeight += ModJsonRecipe.recipeFluidGenerator.getOutput(fluid) * getLiquidLevel(this.getWorld(), blockPos);
            blockPos = blockPos.up();
            fluid = FluidRegistry.lookupFluidForBlock(this.getWorld().getBlockState(blockPos).getBlock());
        }
        return liquidHeight;
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
            return _level / 10.0f;
        } else {
            return 0;
        }
    }

    @Override
    public long updateEnergy() {
        long receiveEnergy = 0;
        if (this.getEnergyStoredLong() < this.getMaxEnergyStoredLong()) {
            receiveEnergy = (int) (this.getFinallyPowerGeneration() * fluidHeight);
        }
        return this.modifyEnergyStored(receiveEnergy);
    }

    @Override
    public void updateStateInSever() {
        fluidHeight = this.getLiquidHeight();
        this.energyStorage.setCapacity(Math.max((long) Math.ceil(this.getFinallyPowerGeneration() * fluidHeight * 2), this.getEnergyStoredLong()));

        super.updateStateInSever();
    }

}
