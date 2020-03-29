package com.henu.mybat.controller;

import com.henu.mybat.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SeckillController {
    @Autowired
    SeckillService seckillService;
    @GetMapping("/phoneseckiil")
    public String phoneSeckill(){
        seckillService.phoneSeckill("1");
        return "success";
    }
}
