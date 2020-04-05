package com.henu.concurrency.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @since 2019/4/20 20:40
 */
@Slf4j
public class CountDownLatchExample1 {
    private final static int threadCount=20;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec= Executors.newCachedThreadPool();
        final CountDownLatch countDownLatch=new CountDownLatch(threadCount);

        for (int i=0;i<threadCount;i++){
            final int threadNum=i;
            exec.execute(()->{
                try {
                    test(threadNum);
                } catch (Exception e) {
                    log.error("execption");
                }finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();//从此处开始阻塞主线程，知道线程池执行结束
        log.info("finish");
        for(int i=0;i<10;i++){
            log.info("{}",i);
        }

        exec.shutdown();
    }

    private static void test(int threadNum)throws Exception{
        Thread.sleep(100);
        log.info("{}",threadNum);
        Thread.sleep(100);
    }
}
