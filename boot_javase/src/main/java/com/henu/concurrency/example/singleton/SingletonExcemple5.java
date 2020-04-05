package com.henu.concurrency.example.singleton;

import com.henu.concurrency.annoations.NotRecommend;
import com.henu.concurrency.annoations.ThreadSafe;

/**
 * 懒汉模式-》双重同步锁懒汉模式+volatile解决指令重排问题
 * 单例实例在第一次使用时进行创建
 * @since 2019/4/16 20:43
 */
@ThreadSafe
@NotRecommend
public class SingletonExcemple5 {
    private SingletonExcemple5(){}
//使用volatile关键字解决指令重排问题
    public volatile static SingletonExcemple5 instance=null;

    //静态的工厂方法
    //实现synchronized关键字从而达到线程安全
    public static synchronized SingletonExcemple5 getInstance(){
       if(instance==null){//双重检测机制
           synchronized (SingletonExcemple5.class){//同步锁
               if(instance==null){
                   instance=new SingletonExcemple5();//在多线程时这里会出现问题，会多次创建出不同的对象，从而造成数据异常
               }
           }
       }
       return instance;
    }
}
