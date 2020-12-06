package com.smx.vlogg.service;

import com.smx.vlogg.model.dto.LoginDto;
import com.smx.vlogg.model.dto.PhoneLoginDto;
import com.smx.vlogg.model.entity.User;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author moses
 * @Date 2020/12/5
 **/
public interface UserService {

    /**
     * 登录
     * @param loginDto 登录dto对象
     * @return
     */
    boolean login(LoginDto loginDto);

    /**
     * 根据手机号找到用户
     * @param phone
     * @return
     */
    User getUser(String phone);

    /**
     * 手机短信验证码登录
     * @param phoneLoginDto
     * @return
     */
    boolean phoneLogin(PhoneLoginDto phoneLoginDto);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    User updateUser(User user);
}
