package com.xiaoxianben.watergenerators.blocks;

import com.xiaoxianben.watergenerators.WaterGenerators;
import com.xiaoxianben.watergenerators.api.IHasItemNBT;
import com.xiaoxianben.watergenerators.api.IHasModel;
import com.xiaoxianben.watergenerators.util.ModInformation;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Random;

public class BlockBase extends Block implements IHasModel {


    public BlockBase(String name, Material materialIn, CreativeTabs tab, @Nullable SoundType soundType, LinkedHashSet<Block> linkedHashSet) {
        super(materialIn);
        setUnlocalizedName(ModInformation.MOD_ID + '.' + name);
        setRegistryName(ModInformation.MOD_ID, name);
        this.setCreativeTab(tab);

        this.setSoundType(soundType == null ? SoundType.METAL : soundType);

        this.setHardness(10.0F);
        this.setHarvestLevel("pickaxe", 1);

        linkedHashSet.add(this);
    }

    public BlockBase(String name, Material materialIn, CreativeTabs tab, @Nullable SoundType soundType, Byte inx) {
        this(name, materialIn, tab, soundType, Objects.requireNonNull(WaterGenerators.BLOCKS));
        Objects.requireNonNull(inx);
        Objects.requireNonNull(WaterGenerators.ITEMS).add(
                new ItemBlock(this) {
                    @Nonnull
                    public String getItemStackDisplayName(@Nonnull ItemStack stack) {
                        return this.getBlock().getLocalizedName().trim();
                    }
                }.setRegistryName(Objects.requireNonNull(this.getRegistryName()))
        );

    }


    @SuppressWarnings("deprecation")
    @Nonnull
    @Deprecated
    public EnumBlockRenderType getRenderType(@Nonnull IBlockState state) {
        return EnumBlockRenderType.MODEL;
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
        NBTTagCompound tagCompound = stack.getTagCompound();

        if (tileEntity instanceof IHasItemNBT && tagCompound != null && tagCompound.hasKey("itemNBT")) {
            ((IHasItemNBT) tileEntity).readItemNbt(Objects.requireNonNull(tagCompound).getCompoundTag("itemNBT"));
        }
    }

    @ParametersAreNonnullByDefault
    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        return willHarvest || super.removedByPlayer(state, world, pos, player, false);
    }

    @Override
    public void getDrops(@Nonnull NonNullList<ItemStack> drops, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull IBlockState state, int fortune) {
        TileEntity tileEntity = world.getTileEntity(pos);

        Random rand = world instanceof World ? ((World) world).rand : RANDOM;
        ItemStack itemStack = new ItemStack(this.getItemDropped(state, rand, fortune), 1, 0);

        if (tileEntity instanceof IHasItemNBT) {
            NBTTagCompound NBT = new NBTTagCompound();
            NBT.setTag("itemNBT", ((IHasItemNBT) tileEntity).getItemNbt());
            itemStack.setTagCompound(NBT);
        }
        if (tileEntity != null && tileEntity.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) {
            for (int i = 0; i < Objects.requireNonNull(tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)).getSlots(); i++) {
                ItemStack itemStack1 = Objects.requireNonNull(tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)).getStackInSlot(i);
                drops.add(itemStack1);
            }
        }
        drops.add(itemStack);
    }

    @Override
    public void registerModels() {
        WaterGenerators.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
    }

}
