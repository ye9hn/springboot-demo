package com.henu.concurrency.example.immutable;

import com.google.common.collect.Maps;
import com.henu.concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Map;

/**
 * 通过Collections.unmodifiableMap(map);修改后的值在运行时修改map会抛出异常，编译可以通过
 * @since 2019/4/17 20:26
 */
@Slf4j
@ThreadSafe
public class ImmutableExample2 {

    private  static Map<Integer,Integer> map= Maps.newHashMap();
    static {
        map.put(1,2);
        map.put(3,4);
        map.put(5,6);
        map= Collections.unmodifiableMap(map);
    }

    public static void main(String[] args) {
      map.put(1,3);//map值可以被修改，但是不能再引用其他变量，线程不安全
      log.info("{}",map);
    }
}
