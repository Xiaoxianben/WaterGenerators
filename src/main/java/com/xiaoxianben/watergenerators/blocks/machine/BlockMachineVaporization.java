package com.xiaoxianben.watergenerators.blocks.machine;

import com.xiaoxianben.watergenerators.gui.GUIHandler;
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

public class BlockMachineVaporization extends BlockMachineBase {
    public BlockMachineVaporization(String levelName, float level) {
        super("machine_vaporization", levelName, level, (long) (level * 4000L));
    }

    @ParametersAreNonnullByDefault
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity tileEntity = Objects.requireNonNull(worldIn.getTileEntity(pos));

        // 如果在服务器端
        if (!worldIn.isRemote) {
            boolean isFillFluid = FluidUtil.interactWithFluidHandler(playerIn, hand, Objects.requireNonNull(tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing)));

            if (!isFillFluid) {
                if (playerIn.getHeldItem(hand).getItem() != ModItems.information_finder) {
                    int ID = GUIHandler.GUIMachineVa;
                    playerIn.openGui(Main.instance, ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
                    return true;
                }
                return false;
            }
            return true;
        }
        return true;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TEMachineVaporization(this.getCapacity(), this.getLevel());
    }

}
