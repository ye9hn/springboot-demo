package com.henu.mongo;

import com.henu.mongo.entity.Area;
import com.henu.mongo.entity.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
@RunWith(SpringRunner.class)
@SpringBootTest
public class BootMongoApplicationTest {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void testMongoDB() {
        for (int i = 0; i < 10; i++) {
            Area area = new Area();
            area.setAreaId(i);
            area.setAreaName("hello" + i);
            area.setPriority(5);
            area.setCreateTime(new Date());
            area.setLastEditTime(new Date());
            mongoTemplate.insert(area);
        }
    }

    @Test
    public void testPage() {
        List<Area> areaList = new ArrayList<>();
        Query query = new Query();
        query.limit(10);
        query.skip(5);

        areaList = mongoTemplate.find(query, Area.class, "area");
        for (Area area : areaList) {
            System.out.println(area.toString());
        }
    }


    @Test
    public void test() {
        List<Area> areaList = new ArrayList<>();
        //检索出areaId在5和10之间的元素
        Query query = new Query(Criteria.where("areaId").lt(10).andOperator(Criteria.where("areaId").gt(5)));
        areaList = mongoTemplate.find(query, Area.class, "area");
        for (Area area : areaList) {
            System.out.println(area.toString());
        }
    }

    @Test
    public void test01() {
        List<Object> areaList = new ArrayList<>();
        //检索出所有的areaId
        areaList = mongoTemplate.query(Area.class).distinct("areaId").all();
        for (Object area : areaList) {
            System.out.println(area.toString());
        }
    }
    @Test
    public void test02() {
        List<Area> areaList = new ArrayList<>();
        //使用基础查询可以直接使用mongo的语句
        //这个查询在区间为小于5和大于995的文档，如果出现java语句不能表述，可以使用基本查询
        BasicQuery query = new BasicQuery("{$or:[{\"areaId\":{$lt:5}},{\"areaId\":{$gt:995}}]}");
        areaList = mongoTemplate.find(query,Area.class,"area");
        for (Object area : areaList) {
            System.out.println(area.toString());
        }
    }

    @Test
    public void test03() {
        List<Area> areaList = new ArrayList<>();
        //使用基础查询可以直接使用mongo的语句
        //这个查询在区间为小于5和大于995的文档，如果出现java语句不能表述，可以使用基本查询
        BasicQuery query = new BasicQuery("{$or:[{\"areaId\":{$lt:5}},{\"areaId\":{$gt:995}}]}");
        areaList = mongoTemplate.find(query,Area.class,"area");
        Message message=new Message();
        message.setId(UUID.randomUUID().toString().replaceAll("-",""));
        message.setMsg(areaList);
        message.setSendTime(new Date());
        System.out.println(mongoTemplate.insert(message));
    }

    @Test
    public void test04() {
        List<Area> areaList = new ArrayList<>();
        //检索出areaId在5和10之间的元素
        Query query = new Query(Criteria.where("areaId").lt(10).andOperator(Criteria.where("areaId").gt(5)));
        areaList = mongoTemplate.find(query, Area.class, "area");
        Message message=new Message();
        message.setId(UUID.randomUUID().toString().replaceAll("-",""));
        message.setMsg(areaList);
        message.setSendTime(new Date());
        mongoTemplate.insert(message,"message");
    }

    @Test
    public void test05() {
        List<Area> areaList = new ArrayList<>();
        Query query = new Query(Criteria.where("areaId").lt(2210).andOperator(Criteria.where("areaId").gt(2195)));
        //这里使用fields实现查询某些字段
        query.fields().include("areaId");
        query.fields().include("areaName");
        areaList = mongoTemplate.find(query, Area.class, "area");
        for (Object area : areaList) {
            System.out.println(area.toString());
        }
    }

    @Test
    public void test06() {
        List<Area> areaList = new ArrayList<>();
        //使用基础查询可以直接使用mongo的语句,实现test05中的功能，实现投影功能
        //这个查询在区间为小于5和大于995的文档，如果出现java语句不能表述，可以使用基本查询
        BasicQuery query = new BasicQuery("{$or:[{\"areaId\":{$lt:5}},{\"areaId\":{$gt:995}}]}","{_id:0,areaId:1,areaName:1}");
        areaList = mongoTemplate.find(query,Area.class,"area");
        System.out.println(areaList.toString());
    }
}
