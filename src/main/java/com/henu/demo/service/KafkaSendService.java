package com.henu.demo.service;

import com.google.gson.Gson;
import com.henu.demo.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;
//集成kafka Producter
@Component
public class KafkaSendService {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    //没有回调
    public void send(Message message) {
        kafkaTemplate.send("HelloWorld", new Gson().toJson(message));
    }

    //发送回调
    public void sendCallBack(Message message) {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("HelloWorld", new Gson().toJson(message));
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("This message is send successful!");
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("This message is send failure!");
            }
        });
    }
}
