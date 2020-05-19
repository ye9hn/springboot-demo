package cn.henuer.redis.dao;

import cn.henuer.redis.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {
    static List<User> users=new ArrayList<>();
    static {
        users.add(new User("1","user1","123456"));
        users.add(new User("2","user2","123456"));
    }

    public User findByUsername(String name){
        for (User user : users) {
            if (user.getUsername().equals(name)){
                return user;
            }
        }
        return null;
    }

    public User findUserById(String userId) {
        for (User user : users) {
            if (user.getId().equals(userId)){
                return user;
            }
        }
        return null;
    }
}
