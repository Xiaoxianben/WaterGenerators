package com.xiaoxianben.watergenerators.blocks;

import com.xiaoxianben.watergenerators.GUI.GUIHandler;
import com.xiaoxianben.watergenerators.Main;
import com.xiaoxianben.watergenerators.init.ModItems;
import com.xiaoxianben.watergenerators.tileEntity.TEMachineVaporization;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

public class BlockMachineVaporization extends BlockMachine {
    public BlockMachineVaporization(String name, long capacity, float level) {
        super("machine_vaporization_" + name, capacity, level);
    }
    public BlockMachineVaporization(String name, float level) {
        super("machine_vaporization_" + name, (long) (level*4000L), level);
    }

    @ParametersAreNonnullByDefault
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity tileEntity = Objects.requireNonNull(worldIn.getTileEntity(pos));

        if (!FluidUtil.interactWithFluidHandler(playerIn, hand, Objects.requireNonNull(tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing))) && playerIn.getHeldItem(hand).getItem() != ModItems.information_finder) {
            int ID = GUIHandler.GUIMachineVa;
            playerIn.openGui(Main.instance, ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TEMachineVaporization(this.getCapacity(), this.getLevel());
    }

}
