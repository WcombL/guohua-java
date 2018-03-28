package com.guohua.design.patterns.creational.singleton;

/**
 * TODO 该类存在问题：
 * 1、在多线程的情况下不能保证存在唯一实例。因为创建对象这个动作不是一个原子操作，是一个可拆分操作
 * 2、在反射的情况下也不能保证存在唯一实例。
 *
 */
public class TaskManager {

    // 创建该类的唯一实例
    private static TaskManager tm = null;

    // 禁止类的外部直接使用new来创建对象
    private TaskManager() {
    }

    public void displayProcesses() {
    }

    public void displayServices() {
    }

    // 为了成员变量的封装性,使用private进行修饰
    // 并提供静态方法对外提供访问
    public static TaskManager getInstance() {
        if (tm == null) {
            tm = new TaskManager();
        }
        return tm;
    }
}
