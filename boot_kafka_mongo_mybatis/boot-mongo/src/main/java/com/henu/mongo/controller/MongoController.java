package com.henu.mongo.controller;

import com.henu.mongo.entity.Area;
import com.henu.mongo.service.MongoService;
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

    @Autowired
    private MongoService mongoService;

    @GetMapping("/hello")
    @ResponseBody
    public List<Area> test() {
        List<Area> areaList = new ArrayList<>();
        areaList = mongoService.queryArea();
        return areaList;
    }

    @PostMapping("/acceptKafka")
    @ResponseBody
    public Area acceptKafka(@RequestBody Area message) {
        Area area = mongoTemplate.insert(message, "hello");
        return area;
    }
}
