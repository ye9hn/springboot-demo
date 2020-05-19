package cn.henuer.redis.service;

import cn.henuer.redis.dao.UserMapper;
import cn.henuer.redis.entity.User;
import cn.henuer.redis.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    RedisTemplate redisTemplate;

    public String login(String name, String password) {
        String token = null;
        try {
            //校验用户是否存在
            User user = userMapper.findByUsername(name);
            if (user == null) {
                System.out.println("用户为空");
            } else {
                //检验用户密码是否正确
                if (!user.getPassword().equals(password)) {
                    System.out.println("密码不正确");
                } else {
                    // 生成token，将 user id 、userName保存到 token 里面
                    token = JwtUtil.sign(user.getUsername(), user.getId(), user.getPassword());
                    redisTemplate.opsForValue().set(user.getId(),token);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    public User findUserById(String userId) {
        User user = userMapper.findUserById(userId);
        if (user != null) {
            return user;
        }
        return null;
    }
}
