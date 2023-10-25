package com.share2renew.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.share2renew.mapper.UserMapper;
import com.share2renew.pojo.User;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoginControllerTest {
    @Autowired
    private UserMapper userMapper;

    @org.junit.jupiter.api.Test
    void getCurrentUserInfo() {
        String username = "jimmy";
        User user = new User();
        user = userMapper.selectOne(new QueryWrapper<User>().eq("username", username).eq("validity", true));
        Assertions.assertNotNull(user);
        System.out.println("断言成功");
        Assertions.assertEquals(1579458566,user.getUserId());
    }
}