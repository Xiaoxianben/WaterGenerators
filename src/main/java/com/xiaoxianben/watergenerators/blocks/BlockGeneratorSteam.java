package com.xiaoxianben.watergenerators.blocks;

import com.xiaoxianben.watergenerators.GUI.GUIHandler;
import com.xiaoxianben.watergenerators.Main;
import com.xiaoxianben.watergenerators.init.ModItems;
import com.xiaoxianben.watergenerators.tileEntity.TEGeneratorSteam;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

public class BlockGeneratorSteam extends BlockGeneratorBasic {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public BlockGeneratorSteam(String name, long basePowerGeneration, float level) {
        super("steam_generator_" + name, Material.IRON, Main.MACHINE_TAB, level, basePowerGeneration * 500, basePowerGeneration);

        setHarvestLevel("pickaxe", 1);
        setResistance(6000.0f * level > 3 ? 1 : 0);
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
            enumfacing = EnumFacing.NORTH;
        }
        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    // 方块放置
    @Override
    @ParametersAreNonnullByDefault
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()));
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public boolean onBlockActivated(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity tileEntity = Objects.requireNonNull(worldIn.getTileEntity(pos));

        if (!FluidUtil.interactWithFluidHandler(playerIn, hand, Objects.requireNonNull(tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing))) && playerIn.getHeldItem(hand).getItem() != ModItems.information_finder) {
            int ID = GUIHandler.GUISteamGenerator;
            playerIn.openGui(Main.instance, ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }

        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TEGeneratorSteam(this.capacity, this.basePowerGeneration, this.levelName);
    }
}
