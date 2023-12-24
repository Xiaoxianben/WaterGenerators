package com.xiaoxianben.watergenerators.blocks;

import com.xiaoxianben.watergenerators.Main;
import com.xiaoxianben.watergenerators.util.IHasModel;
import com.xiaoxianben.watergenerators.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Objects;
import java.util.Random;

public class BlockBase extends Block implements IHasModel {
    public BlockBase(String name, Material material, CreativeTabs tab) {
        super(material);
        setUnlocalizedName(Reference.MOD_ID+'.'+name);
        setRegistryName(name);
        setCreativeTab(tab);

        Main.BLOCKS.add(this);
        Main.ITEMS.add(new ItemBlock(this).setRegistryName(Objects.requireNonNull(this.getRegistryName())));
        /*
        setHardness(5.0F);
        setResistance(1000.0F);
        setLightLevel(1f);
        setLightOpacity(1);
        */
    }

    @Deprecated
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return super.getItemDropped(state, rand, fortune);
    }

    @Override
    public int quantityDropped(Random rand) {
        return super.quantityDropped(rand);
    }

    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

}
