package com.smx.vlogg.mapper;

import com.smx.vlogg.VloggApplication;
import com.smx.vlogg.common.Gender;
import com.smx.vlogg.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = VloggApplication.class)
@Slf4j
class UserMapperTest {

    @Resource
    private UserMapper userMapper;

    @Test
    void insert() throws SQLException {
        User user= User.builder()
                .phone("18261163859")
                .password("123456")
                .nickname("盛明星")
                .avatar("1.jpg")
                .gender(Gender.male.type)
                .birthday(LocalDate.now())
                .address("江苏南京")
                .createTime(LocalDateTime.now())
                .build();
        userMapper.insert(user);
    }

    @Test
    void findUserByPhone() throws SQLException {
        User user=userMapper.findUserByPhone("18261163859");
        log.info(String.valueOf(user));
    }

    @Test
    void updateUser() throws Exception{
        User user=userMapper.findUserByPhone("15301562853");
        user.setPassword(DigestUtils.md5Hex("123123"));
        user.setNickname("张越炀");
        user.setAvatar("https://s-moses.oss-cn-hangzhou.aliyuncs.com/logo/2919fa8e-ea4c-42c3-b7ff-2c0fbcb8f859.jpg");
        user.setGender(Gender.male.type);
        user.setBirthday(LocalDate.of(1999,7,18));
        user.setAddress("江苏省江苏市姑苏区");
        userMapper.updateUser(user);

    }
}