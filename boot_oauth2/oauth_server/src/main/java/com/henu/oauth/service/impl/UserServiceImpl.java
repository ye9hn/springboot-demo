package com.henu.oauth.service.impl;


import com.henu.oauth.mapper.UserMapper;
import com.henu.oauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserDetails userDetails=userMapper.findByName(s);
        System.out.println(userDetails.toString());
        //return userMapper.findByName(s);
        return userDetails;
    }

}
