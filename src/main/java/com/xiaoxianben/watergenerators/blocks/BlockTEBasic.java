package com.xiaoxianben.watergenerators.blocks;


import com.xiaoxianben.watergenerators.Main;
import com.xiaoxianben.watergenerators.init.ModItems;
import com.xiaoxianben.watergenerators.tileEntity.*;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;


public class BlockTEBasic extends BlockBase implements ITileEntityProvider {
    protected int capacity;
    protected int basePowerGeneration;
    protected int maxExtract;

    public BlockTEBasic(String name, Material material, CreativeTabs tab,
                        int capacity, int basePowerGeneration, int maxExtract) {
        // 设置属性
        super(name, material, tab);
        // 设置能量系统
        this.capacity = capacity;
        this.basePowerGeneration = basePowerGeneration;
        this.maxExtract = maxExtract;
    }

    // 方块右击事件
    @ParametersAreNonnullByDefault
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TEEnergyBasic TEBlock;
        if(worldIn.getTileEntity(pos) instanceof TEWaterGenerator) {
            TEBlock = Objects.requireNonNull((TEWaterGenerator) worldIn.getTileEntity(pos));
        } else if(worldIn.getTileEntity(pos) instanceof TETurbineGenerator){
            TEBlock = Objects.requireNonNull((TETurbineGenerator) worldIn.getTileEntity(pos));
        } else {
            TEBlock = Objects.requireNonNull((TECreateGenerator) worldIn.getTileEntity(pos));
        }
/*
        StringBuilder message;
        if (playerIn.isSneaking()) {
            message = new StringBuilder(String.valueOf(TEBlock.writeToNBT(new NBTTagCompound())));
        } else {
            String[] rfList = TEBlock.getFinallyExtractEnergyRough();
            float rfValue = Float.parseFloat(rfList[0]);
            String rfUnit = "";
            if(Integer.parseInt(rfList[1]) == 3) {
                rfUnit = "K";
            } else if (Integer.parseInt(rfList[1]) == 6){
                rfUnit = "M";
            }
            message = new StringBuilder("能量储存: " + TEBlock.getEnergyStored(facing) + '/' + this.capacity + "\n" +
                    "能量增/减: " + TEBlock.getFinallyReceiveEnergySpecific() + "/-" + (rfValue + rfUnit));
        }
        if (TEBlock.facingList[0] != null) {
            for (EnumFacing facing1 : TEBlock.facingList) {
                if (facing1 != null) {
                    message.insert(0, facing1.getName2() + ':' + true + '\n');
                }
            }
        }
        playerIn.sendMessage(new TextComponentString(TEBlock.getWorld().isRemote + "\n" + message.toString()));
*/
        if (!(playerIn.getHeldItem(hand).getItem() == ModItems.information_finder)) {
            if(TEBlock.onBlockActivated(playerIn, hand)) {
                this.openGUI(Main.instance, worldIn, playerIn, pos);
            }
            return true;
        } else {
            StringBuilder message;
            String[] rfList = TEBlock.getFinallyExtractEnergyRough();
            float rfValue = Float.parseFloat(rfList[0]);
            String rfUnit = "";
            if(Integer.parseInt(rfList[1]) == 3) {
                rfUnit = "K";
            } else if (Integer.parseInt(rfList[1]) == 6){
                rfUnit = "M";
            }
            message = new StringBuilder("能量储存: " + TEBlock.getEnergyStored(facing) + '/' + this.capacity + "\n" +
                    "能量增/减: " + TEBlock.getFinallyReceiveEnergySpecific() + "/-" + (rfValue + rfUnit));
            playerIn.sendMessage(new TextComponentString(message.toString()));
        }

        return true;
    }
    public void openGUI(Object mod, World world, EntityPlayer player,BlockPos pos) {
    }

    @ParametersAreNonnullByDefault
    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @ParametersAreNonnullByDefault
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return null;
    }
}
