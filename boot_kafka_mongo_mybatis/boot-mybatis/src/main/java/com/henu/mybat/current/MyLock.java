package com.henu.mybat.current;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.LockSupport;

public class MyLock {


    /**
     * 当前加锁状态，记录加锁的次数
     */
    private volatile int state = 0;
    /**
     * 当前持有锁的线程
     */
    private Thread lockHolder;

    private ConcurrentLinkedQueue<Thread> waiters=new ConcurrentLinkedQueue<>();//这个队列不是基于aqs的，因为我们要自己设计一个aqs

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Thread getLockHolder() {
        return lockHolder;
    }

    public void setLockHolder(Thread lockHolder) {
        this.lockHolder = lockHolder;
    }

    public boolean acquire(){
        Thread current=Thread.currentThread();
        //初始状态
        int c=getState();
        if(c==0){//同步器还没有被持有
            //如果此时对列为0即该线程是第一个发起请求的线程，或者是current线程是队列中第一个被唤醒的线程时
            if((waiters.size()==0|| waiters.peek()==current)&&compareAndSwapState(0,1)){
                setLockHolder(current);
                return true;
            }
        }
        return false;
    }

    /**
     * 加锁
     */
    public void lock() {
        //加锁成功
        if (acquire()) {
            return;
        }
        //cas交换，原子算法
        Thread current = Thread.currentThread();
        //获取锁失败，会将线程加入阻塞对列，同时线程进入自旋状态
        waiters.add(current);
        for (; ; ) {
            //如果没获得锁，线程自旋让出CPU使用权
            if (current==waiters.peek()&&acquire()) {
                //线程被唤醒后，移除队列，走出自旋状态
                waiters.poll();
                break;
            }
            //阻塞当前线程，释放cpu资源
            LockSupport.park(current);//处处要保存线程的信息，为了其他线程释放锁时能唤醒该线程继续自旋调用if(acqure())break;
        }
    }


    /**
     * 释放锁
     */
    public void unlock() {
        if (Thread.currentThread()!=lockHolder){
            throw  new RuntimeException("LockHolder is not current");
        }
        int state=getState();
        if (compareAndSwapState(state,0)){
            setLockHolder(null);
            Thread first=waiters.peek();
            if (first!=null){
                //唤醒队列中阻塞的线程
                LockSupport.unpark(first);
            }
        }
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
            stateOffset = unsafe.objectFieldOffset(MyLock.class.getDeclaredField("state"));
        } catch (Exception e) {
            throw new Error();
        }
    }
}

class UnsafeInstance {
    public static Unsafe reflectGetUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}