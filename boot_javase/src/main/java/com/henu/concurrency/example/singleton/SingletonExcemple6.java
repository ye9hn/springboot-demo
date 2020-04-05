package com.henu.concurrency.example.singleton;

import com.henu.concurrency.annoations.NotThreadSafe;

/**
 * 懒汉模式
 * 单例实例在第一次使用时进行创建
 *
 * @since 2019/4/16 20:43
 */
@NotThreadSafe
public class SingletonExcemple6 {
    private SingletonExcemple6() {
    }



    public static SingletonExcemple6 instance = null;
    static {
        instance = new SingletonExcemple6();
    }
    //静态的工厂方法
    public static SingletonExcemple6 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        System.out.println(getInstance());
        System.out.println(getInstance());
    }
}
