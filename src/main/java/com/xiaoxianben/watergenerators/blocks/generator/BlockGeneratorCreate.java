package com.xiaoxianben.watergenerators.blocks.generator;

import com.xiaoxianben.watergenerators.WaterGenerators;
import com.xiaoxianben.watergenerators.gui.GUIHandler;
import com.xiaoxianben.watergenerators.init.ModItems;
import com.xiaoxianben.watergenerators.tileEntity.generator.TEGeneratorCreate;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

public class BlockGeneratorCreate extends BlockGeneratorBasic {
    public BlockGeneratorCreate() {
        super("generator", "create", 999, Long.MAX_VALUE, WaterGenerators.BLOCKS);
        Objects.requireNonNull(WaterGenerators.ITEMS).add(new ItemBlock(this) {
            @Nonnull
            public String getItemStackDisplayName(@Nonnull ItemStack stack) {
                return this.getBlock().getLocalizedName().trim();
            }
        }.setRegistryName(Objects.requireNonNull(this.getRegistryName())));
    }

    // 方块放置
    @Override
    @ParametersAreNonnullByDefault
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    @ParametersAreNonnullByDefault
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (playerIn.getHeldItem(hand).getItem() != ModItems.information_finder) {
            int ID = GUIHandler.GUICreateGenerator;
            playerIn.openGui(WaterGenerators.instance, ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TEGeneratorCreate(this.basePowerGeneration);
    }

}
