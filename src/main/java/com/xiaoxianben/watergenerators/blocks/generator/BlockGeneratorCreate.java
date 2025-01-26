package com.xiaoxianben.watergenerators.blocks.generator;

import com.xiaoxianben.watergenerators.WaterGenerators;
import com.xiaoxianben.watergenerators.gui.GUIHandler;
import com.xiaoxianben.watergenerators.init.modRegister.MinecraftRegister;
import com.xiaoxianben.watergenerators.tileEntity.generator.TEGeneratorCreate;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class BlockGeneratorCreate extends BlockGeneratorBasic {


    public BlockGeneratorCreate() {
        super("generator", "create", 999, Long.MAX_VALUE);
    }


    @ParametersAreNonnullByDefault
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (playerIn.getHeldItem(hand).getItem() != MinecraftRegister.information_finder) {
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
