package com.xiaoxianben.watergenerators.init;

import com.xiaoxianben.watergenerators.api.IHasInit;
import com.xiaoxianben.watergenerators.fluid.Fluids;

import java.util.ArrayList;
import java.util.List;

public class ModFluid {
    /**
     * init列表
     */
    protected static List<IHasInit> initList = new ArrayList<>();

    public static void init() {
        Fluids Fluids = new Fluids();

        initList.add(Fluids);

        for (IHasInit i : initList) {
            i.init();
        }
    }

    public static void initRecipes() {
        for (IHasInit i : initList) {
            i.initRecipes();
        }
    }
}
