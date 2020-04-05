package com.henu.concurrency.example.singleton;

import com.henu.concurrency.annoations.Recommend;
import com.henu.concurrency.annoations.ThreadSafe;

/**
 * 枚举模式
 * 不需要特殊处理实现线程安全
 *
 * @since 2019/4/16 20:43
 */
@ThreadSafe
@Recommend
//
public class SingletonExcemple7 {
    private SingletonExcemple7() {
    }

    //静态的工厂方法
    public static SingletonExcemple7 getInstance() {
        return Singleton.INSTANCE.getSingleton();
    }
private enum Singleton{
        INSTANCE;
        private SingletonExcemple7 singleton;
        //JVM来保证这个方法绝对只被实例化一次
        Singleton(){
            singleton=new SingletonExcemple7();
        }
        public SingletonExcemple7 getSingleton(){
            return singleton;
        }
}
    public static void main(String[] args) {
        System.out.println(getInstance());
        System.out.println(getInstance());
    }
}
