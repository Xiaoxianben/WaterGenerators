package com.xiaoxianben.watergenerators.blocks.generator;

import com.xiaoxianben.watergenerators.WaterGenerators;
import com.xiaoxianben.watergenerators.config.ConfigValue;
import com.xiaoxianben.watergenerators.gui.GUIHandler;
import com.xiaoxianben.watergenerators.init.ModBlocks;
import com.xiaoxianben.watergenerators.init.ModItems;
import com.xiaoxianben.watergenerators.items.component.ItemComponent;
import com.xiaoxianben.watergenerators.tileEntity.generator.TEGeneratorFluid;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
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
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.LinkedHashSet;
import java.util.Objects;

public class BlockGeneratorFluid extends BlockGeneratorBasic {

    public static final PropertyDirection FACING = BlockHorizontal.FACING;


    public BlockGeneratorFluid(float level, String levelName) {
        this("fluid_generator", levelName, level, ModBlocks.allGeneratorFluid);
    }

    public BlockGeneratorFluid(String type, String levelName, float level, LinkedHashSet<Block> set) {
        super(type, levelName, level, (long) (ConfigValue.energyBasic * Math.pow(1.5, level - 1) * 1.5), set);
    }


    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @SuppressWarnings("deprecation")
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

    // 方块右键
    @ParametersAreNonnullByDefault
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        // 如果在服务端
        if (!worldIn.isRemote) {
            TileEntity tileEntity = Objects.requireNonNull(worldIn.getTileEntity(pos));
            boolean isFillFluid = FluidUtil.interactWithFluidHandler(playerIn, hand, Objects.requireNonNull(tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing)));
            if (!isFillFluid) {
                ItemStack playerItem = playerIn.getHeldItem(hand);
                if (playerItem.getItem() instanceof ItemComponent) {
                    ItemStack component = playerItem.copy();
                    component.setCount(1);
                    int itemC = Objects.requireNonNull(tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing)).insertItem(0, component, false).getCount();
                    playerIn.setHeldItem(hand, new ItemStack(playerItem.getItem(), playerItem.getCount() - (component.getCount() - itemC), playerItem.getMetadata()));
                } else if (playerItem.getItem() != ModItems.information_finder) {
                    int ID = GUIHandler.GUIFluidGenerator;
                    playerIn.openGui(WaterGenerators.instance, ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
                }
            }
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TEGeneratorFluid(this.basePowerGeneration, this.level);
    }

}