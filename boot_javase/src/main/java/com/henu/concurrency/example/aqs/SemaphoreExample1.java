package com.henu.concurrency.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @since 2019/4/20 20:40
 */
@Slf4j
public class SemaphoreExample1 {
    private final static int threadCount=20;

    public static void main(String[] args) {
        ExecutorService exec= Executors.newCachedThreadPool();
        final Semaphore semaphore=new Semaphore(5);
        for (int i=0;i<threadCount;i++){
            final int threadNum=i;
            exec.execute(()->{
                try {
                    semaphore.acquire();//获取一个许可
                    test(threadNum);
                    semaphore.release();//释放一个许可
                } catch (Exception e) {
                    log.error("exception");
                }
            });
        }
        log.info("finish");
        exec.shutdown();
    }

    private static void test(int threadNum)throws Exception{
        //log.info("{}",threadNum);
        int j=0;
        for(int i=0;i<threadNum;i++){
            j++;
        }
        log.info("{}",j);
    }
}
