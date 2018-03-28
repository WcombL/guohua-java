package com.guohua.head.first.design.patterns.first;

/**
 * 橡胶鸭子
 */
public class RubberDuck extends Duck {

    @Override
    public void quack() {
    }

    @Override
    public void fly() {
    }

    @Override
    public void display() {
        System.out.println("look like a rubber duck");
    }
}
