package com.henu.concurrency.example.singleton;

import com.henu.concurrency.annoations.NotThreadSafe;

/**
 * 懒汉模式
 * 单例实例在第一次使用时进行创建
 * @since 2019/4/16 20:43
 */
@NotThreadSafe
public class SingletonExcemple1 {
    private SingletonExcemple1(){}

    public static SingletonExcemple1 instance=null;

    //静态的工厂方法
    public static SingletonExcemple1 getInstance(){
       if(instance==null){
           instance=new SingletonExcemple1();//在多线程时这里会出现问题，会多次创建出不同的对象，从而造成数据异常
       }
       return instance;
    }
}
