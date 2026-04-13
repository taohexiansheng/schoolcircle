package com.xiaoyuan.schoolcircle.controller;

import com.xiaoyuan.schoolcircle.entity.User;
import com.xiaoyuan.schoolcircle.service.UserService;
import com.xiaoyuan.schoolcircle.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        boolean success = userService.register(user);
        return success ? "注册成功" : "用户名已存在";
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestParam String username, @RequestParam String password) {
        User user = userService.login(username, password);
        Map<String, Object> result = new HashMap<>();
        if (user != null) {
            String token = jwtUtil.generateToken(user.getId(), user.getUsername());
            result.put("success", true);
            result.put("token", token);
            result.put("username", user.getUsername());
        } else {
            result.put("success", false);
            result.put("message", "用户名或密码错误");
        }
        return result;
    }
}