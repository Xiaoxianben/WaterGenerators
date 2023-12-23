package com.xiaoxianben.watergenerators.init;

import com.xiaoxianben.watergenerators.API.IHasInit;
import com.xiaoxianben.watergenerators.Main;
import com.xiaoxianben.watergenerators.items.ItemBase;
import com.xiaoxianben.watergenerators.items.ItemsMaterial;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ModItems {

    /** 列表 */
    public static List<IHasInit> initList = new ArrayList<>();

    /** 镀金铁锭 */
    public static Item GOLD_PLATED_IRON_INGOT;
    /** 导管外壳原料 */
    public static Item ductShell_bank;
    /** 导管外壳 */
    public static Item ductShell;
    public static Item information_finder;

    public static void preInit() {
        GOLD_PLATED_IRON_INGOT = new ItemBase("ingot_goldPlatedIron", Main.INGOT_BLOCK_TAB);
        ductShell_bank = new ItemBase("ductShell_1", Main.MATERIAL_TAB);
        ductShell = new ItemBase("ductShell_0", Main.MATERIAL_TAB);
        information_finder = new ItemBase("information_finder", Main.MACHINE_TAB);
        ItemsMaterial material = new ItemsMaterial();
        initList.add(material);
        for (IHasInit init : initList) {
            init.init();
        }
    }
    public static void init() {
        for (IHasInit init : initList) {
            init.initRegistry();
        }
    }

}
