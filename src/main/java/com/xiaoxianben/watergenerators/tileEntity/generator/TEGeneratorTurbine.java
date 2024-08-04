package com.xiaoxianben.watergenerators.tileEntity.generator;

import com.xiaoxianben.watergenerators.recipe.RecipeList;
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
        this(Long.MAX_VALUE);
    }

    public TEGeneratorTurbine(long basePowerGeneration) {
        super(basePowerGeneration);
    }

    public float getLiquidHeight() {
        float liquidLeve = 0;
        // 获取上方的位置
        Iterable<BlockPos> listBlockPos = BlockPos.getAllInBox(this.getPos().add(0, 1, 0), this.getPos().add(0, this.getWorld().getActualHeight() - this.getPos().getY(), 0));
        for (BlockPos blockPos : listBlockPos) {
            Fluid fluid = FluidRegistry.lookupFluidForBlock(this.getWorld().getBlockState(blockPos).getBlock());
            if (RecipeList.recipeFluidGenerator.getInputs().contains(fluid) && fluid.getDensity() > 0) {
                liquidLeve += RecipeList.recipeFluidGenerator.getOutput(fluid) * getLiquidLevel(this.getWorld(), blockPos);
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
            return _level / 10.0f;
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

    @Override
    public void updateStateInSever() {
        this.energyStorage.setCapacity((int) (this.getRealPowerGeneration() * this.getLiquidHeight()) * 2);
        super.updateStateInSever();
    }

}
