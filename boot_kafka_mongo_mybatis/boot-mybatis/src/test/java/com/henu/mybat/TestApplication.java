package com.henu.mybat;

import com.henu.mybat.service.SeckillService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApplication {
    @Autowired
    SeckillService seckillService;

    @Test
    public  void seckill(){
        seckillService.phoneSeckill("1");
    }
}
