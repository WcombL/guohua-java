package com.guohua.design.patterns.creational.singleton;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


public class TaskManagerTest {

    // 多运行几次
    //
    @Test
    public void multithread() {
        new Thread(() -> {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            TaskManager tm = TaskManager.getInstance();
            System.out.println(tm);
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            TaskManager tm = TaskManager.getInstance();
            System.out.println(tm);
        }).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void reflex() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        TaskManager tm = TaskManager.getInstance();
        System.out.println(tm);


        Constructor constructor =TaskManager.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        System.out.println(constructor.newInstance());
    }
}
