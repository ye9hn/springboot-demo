package com.henu.controller;

import com.henu.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SkillProController {
    @Autowired
    private SkillService skillService;
    @RequestMapping("/skill")
    public String skill(){
        skillService.skillProduct("123");
        return "skill";
    }
}
