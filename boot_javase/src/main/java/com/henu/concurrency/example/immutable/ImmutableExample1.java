package com.henu.concurrency.example.immutable;

import com.google.common.collect.Maps;
import com.henu.concurrency.annoations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * final定义不可变变量
 * @since 2019/4/17 20:26
 */
@Slf4j
@NotThreadSafe
public class ImmutableExample1 {

    private final static Integer a=1;
    private final static String b="2";
    private final static Map<Integer,Integer> map= Maps.newHashMap();
    static {
        map.put(1,2);
        map.put(3,4);
        map.put(5,6);
    }

    public static void main(String[] args) {
      map.put(1,3);//map值可以被修改，但是不能再引用其他变量，线程不安全
      log.info("{}",map);
    }
}
