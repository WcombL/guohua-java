package com.guohua.design.patterns.creational.singleton;

/**
 * 多线程下单例模式
 * TODO 存在 TaskManager 类中问题,利用放射创建实例的情况
 */
public class MultithreadSingleton {

    // 创建该类的唯一实例
    private static MultithreadSingleton instance = null;

    // 禁止类的外部直接使用new来创建对象
    private MultithreadSingleton() {
    }

    // 为了成员变量的封装性,使用private进行修饰
    // 并提供静态方法对外提供访问
    // 使用懒汉式
    // TODO 效率及其低下
    public static synchronized MultithreadSingleton getInstance() {
        if (instance == null) {
            instance = new MultithreadSingleton();
        }
        return instance;
    }

    // 使用双重检查
    public static synchronized MultithreadSingleton getInstance1() {
        if (instance == null) {
            synchronized (MultithreadSingleton.class) {
                if (instance == null) {
                    // TODO 主要问题出自此处
                    // 因为new MultithreadSingleton()分为三步操作，不是原子操作。JVM的JIT编译器存在指令重排序优化
                    // 1、给 instance 分配内存
                    // 2、调用 MultithreadSingleton 的构造函数来初始化成员变量
                    // 3、将instance对象指向分配的内存空间（执行完这步 instance 就为非 null 了）
                    // 打乱上面1、2、3的执行顺序
                    // TODO 解决方法是 使用 volatile 修饰 instance
                    instance = new MultithreadSingleton();
                }
            }
        }
        return instance;
    }
}
