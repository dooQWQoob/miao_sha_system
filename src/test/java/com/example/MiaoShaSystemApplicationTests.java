package com.example;

import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MiaoShaSystemApplicationTests {
    @Autowired
    private IUserService userService;

    @Test
    void contextLoads() {

    }

}
