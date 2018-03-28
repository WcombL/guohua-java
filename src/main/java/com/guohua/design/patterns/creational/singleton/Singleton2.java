package com.guohua.design.patterns.creational.singleton;

// 静态初始化
// TODO 在某些场景下无法使用，例如：创建对象依赖参数或配置文件。另外因为是在类加载的使用就已经创建了对象，这会造成创建不必要的对象(有可能为使用该对象)
// 不存在 TaskManager 类中问题
public class Singleton2 {

    //类加载时就初始化
    private static final Singleton2 instance = new Singleton2();

    private Singleton2() {
    }

    public static Singleton2 getInstance() {
        return instance;
    }
}
