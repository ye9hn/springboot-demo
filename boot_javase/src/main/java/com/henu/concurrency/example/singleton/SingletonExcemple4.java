package com.henu.concurrency.example.singleton;

import com.henu.concurrency.annoations.NotRecommend;
import com.henu.concurrency.annoations.NotThreadSafe;

/**
 * 懒汉模式-》双重同步锁懒汉模式  线程不安全，会出现指令重排的问题
 * 单例实例在第一次使用时进行创建
 * @since 2019/4/16 20:43
 */
@NotThreadSafe
@NotRecommend
public class SingletonExcemple4 {
    private SingletonExcemple4(){}

    public static SingletonExcemple4 instance=null;

    //静态的工厂方法
    //实现synchronized关键字从而达到线程安全
    public static synchronized SingletonExcemple4 getInstance(){
       if(instance==null){//双重检测机制
           synchronized (SingletonExcemple4.class){//同步锁
               if(instance==null){
                   instance=new SingletonExcemple4();//在多线程时这里会出现问题，会多次创建出不同的对象，从而造成数据异常
               }
           }
       }
       return instance;
    }
}
