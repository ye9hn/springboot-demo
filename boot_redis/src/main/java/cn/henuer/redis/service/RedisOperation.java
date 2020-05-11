package cn.henuer.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Component
public class RedisOperation {
    @Autowired
    private RedisTemplate redisTemplate;

   @PostConstruct
    public void set(){
           redisTemplate.opsForValue().set("hello", UUID.randomUUID().toString());
           redisTemplate.opsForValue().setBit("hi",10086L,true);
    }
}
