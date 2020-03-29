package com.henu.mybat.current;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class MyLock {


    /**
     * 当前加锁状态，记录加锁的次数
     */
    private volatile int state = 0;
    /**
     * 当前持有锁的线程
     */
    private Thread lockHolder;

    /**
     * 加锁
     */
    public void lock() {

    }

    /**
     * 释放锁
     */
    public void unlock() {

    }

    /**
     * 原子操作
     */

    public final boolean compareAndSwapState(int except, int update) {
        return unsafe.compareAndSwapInt(this, stateOffset, except, update);
    }

    private static final Unsafe unsafe = UnsafeInstance.reflectGetUnsafe();
    private static final long stateOffset;

    static {
        try {
            stateOffset = unsafe.objectFieldOffset(MyLock.class.getDeclaredField("state "));
        } catch (Exception e) {
            throw new Error();
        }
    }
}

class UnsafeInstance {
    public static Unsafe reflectGetUnsafe() {
        try {
            Field field=Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}