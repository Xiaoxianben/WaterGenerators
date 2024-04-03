package com.xiaoxianben.watergenerators.blocks;

import com.xiaoxianben.watergenerators.Main;
import com.xiaoxianben.watergenerators.init.ModItems;
import com.xiaoxianben.watergenerators.tileEntity.TEGeneratorBase;
import com.xiaoxianben.watergenerators.tileEntity.TEMachineBase;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class BlockMachine extends BlockBase implements ITileEntityProvider {

    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    private final long capacity;
    private final float level;

    public BlockMachine(String name, long capacity, float level) {
        super(name, Material.IRON, Main.MACHINE_TAB);
        this.setSoundType(SoundType.METAL);
        this.setHardness(15.0f);

        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));

        this.capacity = capacity;
        this.level = level;
    }

    public float getLevel() {
        return this.level;
    }

    public long getCapacity() {
        return this.capacity;
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

    @ParametersAreNonnullByDefault
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return null;
    }
}
