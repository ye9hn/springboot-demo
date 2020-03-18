package com.henu.kafka.controller;

import com.henu.kafka.entity.Area;
import com.henu.kafka.entity.Message;
import com.henu.kafka.service.KafkaSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class KafkaController {
    @Autowired
    KafkaSendService kafkaSendService;

    @PostMapping("/acceptMsg")
    @ResponseBody
    public Area accpetMsg(@RequestBody Area message) {
        Area area = kafkaSendService.sendCallBack(message);
        return area;
    }

    @RequestMapping("/test")
    @ResponseBody
    public String testKafka() {
//        Message message = new Message();
//        message.setId(System.currentTimeMillis()+"");
//        message.setMsg(null);
//        message.setSendTime(new Date());
        Area area = new Area();
        area.setAreaId(1);
        area.setAreaName("hhhh");
        area.setCreateTime(new Date());
        area.setLastEditTime(new Date());
        kafkaSendService.sendCallBack(area);
        return "success";
    }
}
