package com.henu.mongo.controller;

import com.henu.mongo.entity.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Controller
public class MongoController {
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

    @PostMapping("/acceptKafka")
    @ResponseBody
    public  Area acceptKafka(@RequestBody Area message) {
           Area area= mongoTemplate.insert(message,"hello");
            return area;
    }
}
