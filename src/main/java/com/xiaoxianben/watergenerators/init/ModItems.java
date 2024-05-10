package com.xiaoxianben.watergenerators.init;

import com.xiaoxianben.watergenerators.api.IHasInit;
import com.xiaoxianben.watergenerators.items.ItemBase;
import com.xiaoxianben.watergenerators.items.ItemsMaterial;
import com.xiaoxianben.watergenerators.items.component.ItemsComponent;
import com.xiaoxianben.watergenerators.tileEntity.TEEnergyBasic;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

public class ModItems {
    /**
     * init列表
     */
    public static List<IHasInit> initList = new ArrayList<>();

    /**
     * 镀金铁锭
     */
    public static Item GOLD_PLATED_IRON_INGOT;
    /**
     * 导管外壳原料
     */
    public static Item ductShell_bank;
    /**
     * 导管外壳
     */
    public static Item ductShell;
    /**
     * 信息查找器
     */
    public static Item information_finder;

    public static void preInit() {
        GOLD_PLATED_IRON_INGOT = new ItemBase("ingot_goldPlatedIron");
        ductShell_bank = new ItemBase("ductShell_1");
        ductShell = new ItemBase("ductShell_0");
        information_finder = new ItemBase("information_finder") {
            @ParametersAreNonnullByDefault
            @Nonnull
            public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
                TileEntity TEBlock = world.getTileEntity(pos);

                if (!world.isRemote && TEBlock instanceof TEEnergyBasic) {
                    player.sendMessage(new TextComponentString(String.valueOf(TEBlock.writeToNBT(new NBTTagCompound()))));
                }

                return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
            }

            @ParametersAreNonnullByDefault
            @SideOnly(Side.CLIENT)
            public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
                tooltip.add("用于查看机器的NBT");
            }
        }.setMaxStackSize(1);

        ItemsMaterial material = new ItemsMaterial();
        ItemsComponent component = new ItemsComponent();

        initList.add(material);
        initList.add(component);

        for (IHasInit init : initList) {
            init.init();
        }
    }

    public static void init() {
        for (IHasInit init : initList) {
            init.initRecipes();
        }
    }

}
