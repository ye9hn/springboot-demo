package com.henu.concurrency.example.singleton;

import com.henu.concurrency.annoations.ThreadSafe;

/**
 * 饿汉模式
 * 单例实例在装载时进行创建
 * @since 2019/4/16 20:43
 */
@ThreadSafe
public class SingletonExcemple2 {
    private SingletonExcemple2(){}

    public static SingletonExcemple2 instance=new SingletonExcemple2();

    //静态的工厂方法
    public static SingletonExcemple2 getInstance(){
       return instance;
    }
}
