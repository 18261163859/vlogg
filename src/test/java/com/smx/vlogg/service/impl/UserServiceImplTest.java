package com.smx.vlogg.service.impl;

import com.smx.vlogg.VloggApplication;
import com.smx.vlogg.common.Gender;
import com.smx.vlogg.model.dto.LoginDto;
import com.smx.vlogg.model.entity.User;
import com.smx.vlogg.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = VloggApplication.class)
@Slf4j
class UserServiceImplTest {

    @Resource
    private UserService userService;

    @Test
    void login() {
        LoginDto loginDto=LoginDto.builder()
                .phone("18261163859")
                .password("123456")
                .build();
        boolean flag=userService.login(loginDto);
        assertTrue(flag);
    }

    @Test
    void updateUser(){
        User user= User.builder()
                .phone("18261163859")
                .password("smx19991014")
                .nickname("Moses")
                .avatar("https://s-moses.oss-cn-hangzhou.aliyuncs.com/logo/A6A6FB44-737A-4602-91D1-2F279CD5C539.jpeg")
                .gender(Gender.secret.type)
                .birthday(LocalDate.of(1999,10,14))
                .address("江苏高邮")
                .build();
        userService.updateUser(user);
    }
}