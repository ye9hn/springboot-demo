package com.henu.demo.controller;

import com.henu.demo.dao.AreaMapper;
import com.henu.demo.entity.Area;
import com.henu.demo.entity.Message;
import com.henu.demo.service.KafkaSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Controller
public class UserController {
    @Autowired
    private AreaMapper areaMapper;
    @Autowired
    KafkaSendService kafkaSendService;

    @GetMapping("/get")
    @ResponseBody
    public List<Area> user() {
        List<Area> areaList = areaMapper.selectArea(5);
        System.out.println(areaMapper.selectMap(1101));
//        Message message = new Message();
//        message.setId(System.currentTimeMillis());
//        message.setMsg(areaList);
//        message.setSendTime(new Date());
//        kafkaSendService.sendCallBack(message);
        return areaList;
        // return MD5Util.MD5EncodeUtf8("12345");
    }

    @GetMapping("/update/areaname")
    @ResponseBody
    public String update() {
        int total = areaMapper.selectAll();
        Message message = new Message();
        for (int i = 5; i < total; ) {
            message.setId(System.currentTimeMillis());
            message.setMsg(Arrays.asList(areaMapper.selectArea(i)));
            message.setSendTime(new Date());
            kafkaSendService.sendCallBack(message);
            i += 20;
        }
        return "success";
    }

    @Autowired
    private MongoTemplate mongoTemplate;
    @GetMapping("/hello")
    @ResponseBody
    public String test() {
        List<Area> areaList = new ArrayList<>();
        //使用基础查询可以直接使用mongo的语句
        //这个查询在区间为小于5和大于995的文档，如果出现java语句不能表述，可以使用基本查询
        BasicQuery query = new BasicQuery("{$or:[{\"areaId\":{$lt:5}},{\"areaId\":{$gt:995}}]}");
        areaList = mongoTemplate.find(query,Area.class,"area");
        for (Object area : areaList) {
            System.out.println(area.toString());
        }
        return "success";
    }
}
