package com.guohua.design.patterns.creational.singleton;

// 使用静态内部类
// TODO 推荐使用 静态内部类 不存在 TaskManager 类中问题
public class Singleton3 {

    private static class SingletonHolder {
        private static final Singleton3 INSTANCE = new Singleton3();
    }

    private Singleton3() {
    }

    public static final Singleton3 getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
