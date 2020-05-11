package cn.henuer.redis.controller;

import cn.henuer.redis.dao.AreaMapper;
import cn.henuer.redis.entity.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AreaController {
    @Autowired
    private AreaMapper areaMapper;
    @GetMapping("/list")
    public List<Area> test(){
        return areaMapper.selectArea(10);
    }
}
