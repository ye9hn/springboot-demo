package com.henu.demo;


import com.henu.demo.entity.Area;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class SpringbootMybatisApplicationTests {
    @Autowired
    DataSource dataSource;

    @Test
    public void contextLoads() {//测试数据库连接情况
        try (Connection connection = dataSource.getConnection()) {
            System.out.println(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void testMongoDB() {
        System.out.println(mongoTemplate.toString());
        for (int i = 0; i < 1000; i++) {
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
}
