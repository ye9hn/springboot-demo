package com.henu.mq.controller;

import com.henu.mq.service.provider.RabbitMQProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProviderController {
    @Autowired
    public RabbitMQProvider workProvider;

    @GetMapping("/work")
    public String work(){
        workProvider.work();
        return "success";
    }

    @GetMapping("/fanout")
    public String fanout(){
        workProvider.fanout();
        return "success";
    }


    @GetMapping("/route")
    public String route(){
        workProvider.route();
        return "success";
    }

    @GetMapping("/topic")
    public String topic(){
        workProvider.topic();
        return "success";
    }

    @GetMapping("/manualack")
    public String ack(){
        workProvider.manualAck();
        return "success";

    }
}
