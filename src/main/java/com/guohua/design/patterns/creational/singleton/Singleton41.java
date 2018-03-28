package com.guohua.design.patterns.creational.singleton;

// 单例类如何防止反射
public class Singleton41 {

    private static boolean flag = false;

    private Singleton41() {
        synchronized (Singleton41.class) {
            if (flag == false) {
                flag = !flag;
            } else {
                throw new RuntimeException("单例模式被侵犯！");
            }
        }
    }

    private static class SingletonHolder {
        private static final Singleton41 INSTANCE = new Singleton41();
    }

    public static Singleton41 getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
