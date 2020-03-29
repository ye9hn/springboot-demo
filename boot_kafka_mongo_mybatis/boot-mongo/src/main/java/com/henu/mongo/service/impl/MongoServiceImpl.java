package com.henu.mongo.service.impl;

import com.henu.mongo.entity.Area;
import com.henu.mongo.service.MongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MongoServiceImpl implements MongoService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Area> queryArea() {
        //使用基础查询可以直接使用mongo的语句
        //这个查询在区间为小于5和大于995的文档，如果出现java语句不能表述，可以使用基本查询
        BasicQuery query = new BasicQuery("{$or:[{\"areaId\":{$lt:5}},{\"areaId\":{$gt:995}}]}");
        return mongoTemplate.find(query, Area.class, "area");

    }
}
