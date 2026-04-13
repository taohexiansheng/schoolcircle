package com.xiaoyuan.schoolcircle.service;

import com.xiaoyuan.schoolcircle.entity.User;
import com.xiaoyuan.schoolcircle.mapper.UserMapper;
import com.xiaoyuan.schoolcircle.util.PasswordEncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public boolean register(User user) {
        if (userMapper.findByUsername(user.getUsername()) != null) {
            return false;
        }
        // 加密密码
        user.setPassword(PasswordEncoderUtil.encode(user.getPassword()));
        return userMapper.insert(user) > 0;
    }

    public User login(String username, String password) {
        User user = userMapper.findByUsername(username);
        if (user != null && PasswordEncoderUtil.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }
}