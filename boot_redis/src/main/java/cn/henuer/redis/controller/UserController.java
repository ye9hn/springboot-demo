package cn.henuer.redis.controller;

import cn.henuer.redis.service.UserService;
import cn.henuer.redis.annotation.TokenRequired;
import cn.henuer.redis.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 用户登录
     * @param user
     * @return
     */
    @PostMapping("/login")
    public Map<String, String> login(User user){
        String token = userService.login(user.getUsername(), user.getPassword());
        if (token == null) {
            System.out.println("token null");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        return tokenMap;
    }

    @TokenRequired
    @GetMapping("/hello")
    public String getMessage(){
        return "你好哇，我是小码仔";
    }
}