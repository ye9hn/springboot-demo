package com.henu.concurrency.example.threadLocal;

/**
 * @since 2019/4/17 21:37
 */
public class RequestHolder {
    private final static ThreadLocal<Long> requestHolder=new ThreadLocal<>();
    public static void add(Long id){
        requestHolder.set(id);
    }

    public static Long getId(){
        return requestHolder.get();
    }

    public static void remove(){
        requestHolder.remove();//释放掉防止内存泄漏
    }
}
