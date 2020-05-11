package com.henu.mq.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProviderService {
    @Autowired
    private AmqpTemplate amqpTemplate;
    public void sendMsg(){
        amqpTemplate.convertAndSend("test","这是一条测试消息");
    }
}
