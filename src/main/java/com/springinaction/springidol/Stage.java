package com.springinaction.springidol;

/**
 * Created by xuezhangying on 3/13/16.
 */
public class Stage {
    private Stage() {}

    private static class StageSingletonHolder{
        static Stage instance = new Stage();
    }

    public static Stage getInstance() {
        return StageSingletonHolder.instance;
    }
}
