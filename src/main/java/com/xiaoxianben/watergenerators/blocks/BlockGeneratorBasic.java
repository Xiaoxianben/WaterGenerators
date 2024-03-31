package com.xiaoxianben.watergenerators.blocks;


import com.xiaoxianben.watergenerators.tileEntity.TEGeneratorBase;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;


public class BlockGeneratorBasic extends BlockBase implements ITileEntityProvider {

    public String levelName = "";
    public float level;
    protected long capacity;
    protected long basePowerGeneration;

    public BlockGeneratorBasic(String name, Material material, CreativeTabs tab, float level,
                               long capacity, long basePowerGeneration) {
        // 设置属性
        super(name, material, tab);
        this.setHardness(15.0f);
        this.level = level;
        // 设置能量系统
        this.capacity = capacity < 0 ? Long.MAX_VALUE : capacity;
        this.basePowerGeneration = basePowerGeneration;
    }

    public BlockGeneratorBasic setLevelName(String levelName) {
        this.levelName = levelName;
        return this;
    }

    // 方块右击事件
    @ParametersAreNonnullByDefault
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public void getDrops(@Nonnull NonNullList<ItemStack> drops, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull IBlockState state, int fortune) {
        Random rand = world instanceof World ? ((World) world).rand : RANDOM;

        Item item = this.getItemDropped(state, rand, fortune);

        TileEntity tileEntity = world.getTileEntity(pos);

        if (item != Items.AIR && tileEntity != null) {
            ItemStack itemStack = new ItemStack(item, 1, 0);
            itemStack.setTagCompound(tileEntity.writeToNBT(new NBTTagCompound()));
            drops.add(itemStack);
        }
    }

    @ParametersAreNonnullByDefault
    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        return willHarvest || super.removedByPlayer(state, world, pos, player, false);
    }

    @Override
    public void harvestBlock(@Nonnull World world, @Nonnull EntityPlayer player, @Nonnull BlockPos pos, @Nonnull IBlockState state, TileEntity te, @Nonnull ItemStack stack) {
        super.harvestBlock(world, player, pos, state, te, stack);
        world.setBlockToAir(pos);
    }

    @ParametersAreNonnullByDefault
    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TEGeneratorBase) {
            NBTTagCompound tagCompound = stack.getTagCompound();
            if (tagCompound != null) {
                tileEntity.readFromNBT(tagCompound);
            }
        }
    }

    @ParametersAreNonnullByDefault
    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return null;
    }
}
