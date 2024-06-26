package com.xiaoxianben.watergenerators.init;

import com.xiaoxianben.watergenerators.api.IHasInit;
import com.xiaoxianben.watergenerators.api.IModInit;
import com.xiaoxianben.watergenerators.fluid.Fluids;

import java.util.ArrayList;
import java.util.List;

public class ModFluid implements IModInit {
    /**
     * init列表
     */
    protected static List<IHasInit> initList = new ArrayList<>();

    public void preInit() {
        Fluids fluids = new Fluids();

        initList.add(fluids);

        for (IHasInit i : initList) {
            i.init();
        }
    }

    public void init() {
        for (IHasInit i : initList) {
            i.initRecipes();
        }
    }
}
