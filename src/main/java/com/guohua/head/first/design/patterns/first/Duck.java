package com.guohua.head.first.design.patterns.first;

/**
 * 鸭子超类
 */
public abstract class Duck {

    public Duck() {
    }

    // 嘎嘎声
    public void quack(){
        System.out.println("发出嘎嘎声.....");
    }

    // 游泳
    public void swim(){
        System.out.println("正在游泳.....");
    }

    // 飞
    public void fly(){
        System.out.println("飞.....");
    }

    // 具体由子类实现
    public abstract void display();
}
