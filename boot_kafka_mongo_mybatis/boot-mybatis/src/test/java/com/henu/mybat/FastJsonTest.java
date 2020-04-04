package com.henu.mybat;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.henu.mybat.entity.Area;
import com.henu.mybat.entity.Person;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class FastJsonTest {
private List<Person> personList=new ArrayList<>();
    @Before
    public void test01(){
        List<Area> areas= Arrays.asList(new Area(1,"hello",2,new Date(),new Date()),new Area(2,"hello,hello",3,new Date(),new Date()));
        personList.add(new Person(15, "John Doe", new Date(),areas));
        personList.add(new Person(20, "Janette Doe", new Date(),areas));
    }
    @Test
    public void convertToJsonCorrect() {
        String jsonOutput= JSON.toJSONString(personList);
        System.out.println(jsonOutput);
        List<Person> jsonArray=JSON.parseArray(jsonOutput,Person.class);
        for (int i = 0; i < jsonArray.size(); i++) {
            System.out.println(jsonArray.get(i));
            JSONObject jsonObject=JSON.parseObject(JSON.toJSONString(jsonArray.get(i)));
            System.out.println(jsonObject.get("AREA LIST"));
        }

    }
}
