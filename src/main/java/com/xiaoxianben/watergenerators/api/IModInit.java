package com.xiaoxianben.watergenerators.api;

public interface IModInit {
    /**
     * 在mod进行第一阶段的初始化时，在主类中运行。
     */
    void preInit();

    /**
     * 在mod进行第二阶段的初始化时，在主类中运行。
     */
    void init();
}
