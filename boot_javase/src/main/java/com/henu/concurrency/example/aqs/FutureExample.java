package com.henu.concurrency.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class FutureExample {
    static  class MyCallable implements Callable<String> {

        @Override
        public String call() throws Exception {
            log.info("do something in callable");
            Thread.sleep(5000);
            return "Done";
        }
    }
    public static void main(String[] args) {
        ExecutorService executorService= Executors.newCachedThreadPool();
        Future<String> future= executorService.submit(new MyCallable());
        log.info("do something in main");
        try {
            Thread.sleep(1000);
            String result=future.get();
            log.info("result:{}",result);
        } catch (Exception e) {
            log.error("{}",e);
        }finally {
            executorService.shutdown();
        }

    }
}
