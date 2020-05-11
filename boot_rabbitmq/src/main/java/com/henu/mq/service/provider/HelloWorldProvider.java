package com.henu.mq.service.provider;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloWorldProvider {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void test(){
        rabbitTemplate.convertAndSend("hello","helloworld");
    }
}
