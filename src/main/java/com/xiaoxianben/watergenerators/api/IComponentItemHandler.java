package com.xiaoxianben.watergenerators.api;

import com.xiaoxianben.watergenerators.items.itemHandler.ItemStackHandler;


public interface IComponentItemHandler {
    /**
     * 获取物品组件的处理器
     * */
    ItemStackHandler getComponentItemHandler();
}
