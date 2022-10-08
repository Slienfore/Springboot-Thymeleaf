package com.withThymeleaf.mapper;

import com.withThymeleaf.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    // 根据用户名查询用户
    User findByUserName(String username);

    // 保存用户信息
    void save(User user);

}