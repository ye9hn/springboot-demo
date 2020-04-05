package com.henu.concurrency.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @since 2019/4/20 20:40
 */
@Slf4j
public class SemaphoreExample2 {
    private final static int threadCount=20;

    public static void main(String[] args) {
        ExecutorService exec= Executors.newCachedThreadPool();
        final Semaphore semaphore=new Semaphore(3);
        for (int i=0;i<threadCount;i++){
            final int threadNum=i;
            exec.execute(()->{
                try {
                    semaphore.acquire(3);//一次获取三个许可，此时相当于单线程执行
                    test(threadNum);
                    semaphore.release(3);
                } catch (Exception e) {
                    log.error("execption");
                }
            });
        }
        log.info("finish");
        exec.shutdown();
    }

    private static void test(int threadNum)throws Exception{
        log.info("{}",threadNum);
       Thread.sleep(100);
    }
}
