package com.henu.concurrency.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 小陽
 * @since 2019/4/12 21:51
 */
@RestController
@Slf4j
public class TestController {
    @RequestMapping("/test")
    public String test(){
        return "test";
    }
}
