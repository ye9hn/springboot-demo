package com.henu.mq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDemo {

    @Autowired
    RabbitTemplate rabbitTemplate;
    @Test
    public void test() {
        CorrelationData messageId=new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("boot-topic-exchange","slow.red.ne","这是一个消息",messageId);
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
