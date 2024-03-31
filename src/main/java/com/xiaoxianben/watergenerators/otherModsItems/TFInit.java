package com.xiaoxianben.watergenerators.otherModsItems;

import com.xiaoxianben.watergenerators.API.IOtherModInit;
import com.xiaoxianben.watergenerators.init.ModBlocks;
import com.xiaoxianben.watergenerators.init.ModItems;

public class TFInit implements IOtherModInit {

    @Override
    public void initBlock() {
        ModBlocks.initList.add(new TFBlocks());
    }

    @Override
    public void initItem() {
        ModItems.initList.add(new TFItems());
    }

    @Override
    public void initOre() {

    }
}
