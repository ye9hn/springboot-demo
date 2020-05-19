package cn.henuer.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    public void test() {
        long key=155555L;
        for (long i = 0; i <50 ; i++) {
            redisTemplate.opsForValue().setBit(UUID.randomUUID().toString().substring(0,5),key+i/5,true);
        }
    }

    @Test
    public void test01() {
        String string=UUID.randomUUID().toString().substring(0,5);
        for (int i = 0; i < 100; i++) {
            redisTemplate.opsForValue().setBit(string,i,true);
        }
        System.out.println(redisTemplate.opsForValue().getBit(string,2));
    }
}
