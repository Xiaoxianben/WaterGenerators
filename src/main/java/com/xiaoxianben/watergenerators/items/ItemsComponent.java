package com.xiaoxianben.watergenerators.items;

import com.xiaoxianben.watergenerators.api.IHasInit;
import com.xiaoxianben.watergenerators.init.ModRecipes;
import com.xiaoxianben.watergenerators.items.component.ItemComponent;
import net.minecraft.init.Items;

public class ItemsComponent implements IHasInit {

    public static ItemComponent component_null;
    public static ItemComponent component_extract;
    public static ItemComponent component_powerGeneration;

    @Override
    public void init() {
        component_null = new ItemComponent("null");
        component_extract = new ItemComponent("fluidExtract");
        component_powerGeneration = new ItemComponent("powerGeneration");
    }

    @Override
    public void initRecipes() {
        ModRecipes.addRecipeComponent(component_null, Items.AIR, null);
        ModRecipes.addRecipeComponent(component_extract, component_null, Items.BUCKET);
        ModRecipes.addRecipeComponent(component_powerGeneration, component_null, ItemsMaterial.turbines[4]);
    }
}
