package com.henu.mybat.controller;

import com.henu.mybat.dao.AreaMapper;
import com.henu.mybat.entity.Area;
import com.henu.mybat.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Controller
public class UserController {
    @Autowired
    private AreaMapper areaMapper;
//    @Autowired
////    KafkaSendService kafkaSendService;

    @GetMapping("/get")
    @ResponseBody
    public List<Area> user() {
        List<Area> areaList = areaMapper.selectArea(1101);
        System.out.println(areaMapper.selectMap(1101));
//        Message message = new Message();
//        message.setId(System.currentTimeMillis());
//        message.setMsg(areaList);
//        message.setSendTime(new Date());
//        kafkaSendService.sendCallBack(message);

        String uri = "http://localhost:9002/kafka/acceptMsg";
        RestTemplate template = new RestTemplate();
        for (int i = 0; i < areaList.size(); i++) {
            Area ret=template.postForObject(uri,areaList.get(i), Area.class);
            System.out.println(ret);
        }

        return areaList;
        // return MD5Util.MD5EncodeUtf8("12345");
    }

    @GetMapping("/update/areaname")
    @ResponseBody
    public String update() {
        int total = areaMapper.selectAll();
        Message message = new Message();
        for (int i = 5; i < total; ) {
            message.setId(System.currentTimeMillis() + "");
            message.setMsg(Arrays.asList(areaMapper.selectArea(i)));
            message.setSendTime(new Date());
            //kafkaSendService.sendCallBack(message);
            i += 20;
        }
        return "success";
    }


}
