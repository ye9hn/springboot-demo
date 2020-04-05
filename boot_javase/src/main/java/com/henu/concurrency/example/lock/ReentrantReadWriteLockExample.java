package com.henu.concurrency.example.lock;

import com.henu.concurrency.annoations.ThreadSafe;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 一种悲观锁
 * @since 2019/4/12 22:21
 */
@Slf4j
@ThreadSafe
public class ReentrantReadWriteLockExample {

    private final Map<String,Data> map=new TreeMap<>();
    private  final ReentrantReadWriteLock lock=new ReentrantReadWriteLock();
    private final Lock readLock=lock.readLock();
    private final Lock writeLock=lock.writeLock();

    public static void main(String[] args) throws Exception {

    }
    public Data get(String key){
        readLock.lock();
        try{
            return map.get(key);
        }finally {
            readLock.unlock();
        }
    }
    public Set<String> getAllKeys(){
        readLock.lock();
        try{
            return map.keySet();
        }finally {
            readLock.unlock();
        }
    }
    public Data put(String key,Data value){
        writeLock.lock();
        try{
            return map.put(key, value);
        }finally {
            writeLock.unlock();
        }
    }
    class Data{

    }
}
