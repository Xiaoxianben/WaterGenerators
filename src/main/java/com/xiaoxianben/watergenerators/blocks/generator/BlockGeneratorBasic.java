package com.xiaoxianben.watergenerators.blocks.generator;


import com.xiaoxianben.watergenerators.WaterGenerators;
import com.xiaoxianben.watergenerators.blocks.BlockBase;
import com.xiaoxianben.watergenerators.blocks.material.BlockMaterial;
import com.xiaoxianben.watergenerators.util.ModInformation;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.LinkedHashSet;


public class BlockGeneratorBasic extends BlockBase implements ITileEntityProvider {

    public String type;
    public String levelName;
    public float level;
    protected long basePowerGeneration;

    public BlockGeneratorBasic(String type, String levelName, float level, long basePowerGeneration, LinkedHashSet<Block> set) {
        // 设置属性
        super(type + "_" + levelName, BlockMaterial.generator, WaterGenerators.MACHINE_TAB, SoundType.METAL, set);
        this.setHardness(10.0f);
        this.setHarvestLevel("pickaxe", 1);
        this.setResistance(6000.0f * (level > 3 ? 1 : 0));

        this.level = level;
        this.levelName = levelName;
        this.type = type;
        this.basePowerGeneration = basePowerGeneration;
    }


    public String getLevelName() {
        return I18n.format("level." + this.levelName + ".name");
    }

    // 方块右击事件
    @ParametersAreNonnullByDefault
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return worldIn.isRemote;
    }

    @Nonnull
    public String getLocalizedName() {
        return I18n.format(this.getUnlocalizedName() + ".name") + "-" + this.getLevelName();
    }

    @Nonnull
    public String getUnlocalizedName() {
        return "tile." + ModInformation.MOD_ID + "-" + this.type;
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
