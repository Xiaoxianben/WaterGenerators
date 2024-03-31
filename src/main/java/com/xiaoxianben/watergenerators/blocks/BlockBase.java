package com.xiaoxianben.watergenerators.blocks;

import com.xiaoxianben.watergenerators.API.IHasModel;
import com.xiaoxianben.watergenerators.Main;
import com.xiaoxianben.watergenerators.tileEntity.TEEnergyBasic;
import com.xiaoxianben.watergenerators.util.ModInformation;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nonnull;
import java.util.Objects;

public class BlockBase extends Block implements IHasModel {
    public BlockBase(String name, Material material, CreativeTabs tab) {
        super(material);
        setUnlocalizedName(ModInformation.MOD_ID + '.' + name);
        setRegistryName(name);
        setCreativeTab(tab);

        Main.BLOCKS.add(this);
        Main.ITEMS.add(new ItemBlock(this).setRegistryName(Objects.requireNonNull(this.getRegistryName())));
    }

    @Nonnull
    @Deprecated
    public EnumBlockRenderType getRenderType(@Nonnull IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public int getLightValue(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if(tileEntity instanceof TEEnergyBasic) {
            this.lightValue = ((TEEnergyBasic) tileEntity).getLightValue();
        }
        return this.lightValue;
    }

    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

}
