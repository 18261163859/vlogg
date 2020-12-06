package com.smx.vlogg.service.impl;

import com.smx.vlogg.mapper.UserMapper;
import com.smx.vlogg.model.dto.LoginDto;
import com.smx.vlogg.model.dto.PhoneLoginDto;
import com.smx.vlogg.model.entity.User;
import com.smx.vlogg.service.RedisService;
import com.smx.vlogg.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author moses
 * @Date 2020/12/5
 **/
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisService redisService;

    @Override
    public boolean login(LoginDto loginDto) {
        User user=null;
        try {
            user= userMapper.findUserByPhone(loginDto.getPhone());
        } catch (SQLException e) {
            System.err.println("根据手机号查找用户出现异常");
        }
        if(user!=null){

            if(DigestUtils.md5Hex(loginDto.getPassword()).equals(user.getPassword())){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    @Override
    public User getUser(String phone) {
        try {
            return  userMapper.findUserByPhone(phone);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean phoneLogin(PhoneLoginDto phoneLoginDto) {
        boolean flag=redisService.existsKey(phoneLoginDto.getPhone());
        if(flag){
            //取出redis中之前存储的验证码
            String saveCode=redisService.getValue(phoneLoginDto.getPhone(),String.class);
            //和前端传的验证码比对，比对成功
            if(saveCode.equalsIgnoreCase(phoneLoginDto.getCode())){
                //查找数据库该手机号用户是否存在
                User user=getUser(phoneLoginDto.getPhone());
                //存在就登录成功
                if(user!=null){
                    return true;
                }else{
                    //不存在该手机号，就构建新用户记录，补充必备字段写入数据库，一键注册并登录（密码留空）
                    User user1=User.builder()
                            .phone(phoneLoginDto.getPhone())
                            .nickname("新用户")
                            .avatar("/static/default.jpg")
                            .createTime(LocalDateTime.now())
                            .build();
                    try {
                        userMapper.insert(user1);
                        return true;
                    } catch (SQLException throwables) {
                        System.err.println("新增用户出现异常");
                    }
                }
                return false;
            }
        }
        return false;
    }

    @Override
    public User updateUser(User user) {
        //先查出数据库原用户信息
        User saveUser = getUser(user.getPhone());
        //相应字段修改
        saveUser.setPassword(DigestUtils.md5Hex(user.getPassword()));
        saveUser.setNickname(user.getNickname());
        saveUser.setAvatar(user.getAvatar());
        saveUser.setGender(user.getGender());
        saveUser.setBirthday(user.getBirthday());
        saveUser.setAddress(user.getAddress());
        //更新数据
        try {
            userMapper.updateUser(saveUser);
        } catch (SQLException throwables) {
            System.err.println("修改用户信息出异常");
        }
        return saveUser;
    }
}
