package com.xiaoxianben.watergenerators.init;

import com.xiaoxianben.watergenerators.API.IHasInit;
import com.xiaoxianben.watergenerators.blocks.BlocksFluid;

import java.util.ArrayList;
import java.util.List;

public class ModFluid {
    /**
     * init列表
     */
    protected static List<IHasInit> initList = new ArrayList<>();

    public static void init() {
        BlocksFluid blocksFluid = new BlocksFluid();

        initList.add(blocksFluid);

        for (IHasInit i : initList) {
            i.init();
        }
    }
}
