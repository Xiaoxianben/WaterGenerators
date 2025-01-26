package com.xiaoxianben.watergenerators.items.component;

import com.xiaoxianben.watergenerators.WaterGenerators;
import com.xiaoxianben.watergenerators.blocks.generator.BlockGeneratorBasic;
import com.xiaoxianben.watergenerators.items.ItemBase;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Objects;

public class ItemComponent extends ItemBase {

    public final String componentName;

    public ItemComponent(String name) {
        super("component_" + name, WaterGenerators.Item_TAB);
        this.componentName = name;
    }

    @ParametersAreNonnullByDefault
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(I18n.format("item." + WaterGenerators.MOD_ID + "-component.tooltip"));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Nonnull
    @ParametersAreNonnullByDefault
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        Block isRightBlock = worldIn.getBlockState(pos).getBlock();
        if (isRightBlock instanceof BlockGeneratorBasic) {
            TileEntity tileEntity = Objects.requireNonNull(worldIn.getTileEntity(pos));
            ItemStack playerItem = player.getHeldItem(hand);
            ItemStack component = playerItem.copy();
            if (!player.isSneaking()) {
                component.setCount(1);
            }

            int itemC = 0;
            IItemHandler itemHandler = Objects.requireNonNull(tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing));
            for (int i = 0; i < itemHandler.getSlots(); i++) {
                itemC = component.getCount() - itemHandler.insertItem(i, component.copy(), false).getCount();
                if (itemC > 0) break;
            }
            if (itemC == 0) return EnumActionResult.PASS;

            player.setHeldItem(hand, new ItemStack(this, playerItem.getCount() - itemC, playerItem.getMetadata()));

            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }

}
