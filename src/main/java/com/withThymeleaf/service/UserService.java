package com.withThymeleaf.service;

import com.withThymeleaf.pojo.User;

public interface UserService {
    // 注册用户
    void register(User user);

    // 登录
    User login(String username, String password);
}