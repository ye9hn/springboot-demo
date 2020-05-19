package cn.henuer.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
public class SessionController {
    @Value("${server.port}")
    String serverPort;
    @Autowired
    RedisTemplate redisTemplate;

    @GetMapping("/login")
    public String login(String name, HttpServletRequest request) {
        String session=request.getSession().getId();
        System.out.println(session);
        redisTemplate.opsForValue().set(name,session);
        return "OK";
    }

    @GetMapping("/getMsg")
    public String msg(HttpServletRequest request) {
        System.out.println(serverPort);
        return "fail";
    }
}
