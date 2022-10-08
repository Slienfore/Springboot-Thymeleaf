package com.withThymeleaf.service.serviceImpl;

import com.withThymeleaf.mapper.UserMapper;
import com.withThymeleaf.pojo.User;
import com.withThymeleaf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import java.nio.charset.StandardCharsets;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void register(User user) {
        // 查找数据库是否存在同名用户
        User userObj = userMapper.findByUserName(user.username());

        // 判断用户是否存在
        if (!ObjectUtils.isEmpty(userObj)) throw new RuntimeException("当前用户名已经注册");

        // 明文加密
        String md5Password = DigestUtils.md5DigestAsHex(user.password().getBytes(StandardCharsets.UTF_8));

        user.password(md5Password);

        userMapper.save(user);
    }

    @Override
    public User login(String username, String password) {
        User user = userMapper.findByUserName(username);

        if (ObjectUtils.isEmpty(user)) throw new RuntimeException("用户名不正确!");

        // 将用户输入的密码进行 MD5 加密 然后 将加密后的密码进行比较
        String md5DigestAsHex = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));

        if (!user.password().equals(md5DigestAsHex)) throw new RuntimeException("密码输入错误!");

        return user;
    }
}