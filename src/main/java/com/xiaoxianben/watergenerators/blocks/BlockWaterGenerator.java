package com.xiaoxianben.watergenerators.blocks;

import com.xiaoxianben.watergenerators.GUI.GUIHandler;
import com.xiaoxianben.watergenerators.Main;
import com.xiaoxianben.watergenerators.tileEntity.TEWaterGenerator;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

public class BlockWaterGenerator extends BlockTEBasic{

    public BlockWaterGenerator(String name, int basePowerGeneration, int level) {
        super(name, Material.IRON, Main.MACHINE_TAB, basePowerGeneration * 400, basePowerGeneration, 0);
        setHarvestLevel("pickaxe", 2);
        setResistance(6000.0f * level);
    }

    public void openGUI(Object mod, World world, EntityPlayer player, BlockPos pos) {
        int ID = GUIHandler.GUIWaterGenerator;
        player.openGui(mod, ID, world, pos.getX(), pos.getY(), pos.getZ());
    }


    // 方块放置
    @Override
    @ParametersAreNonnullByDefault
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TEWaterGenerator(this.capacity, this.basePowerGeneration, this.level);
    }

}
