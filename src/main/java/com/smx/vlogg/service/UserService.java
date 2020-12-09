package com.smx.vlogg.service;

import com.smx.vlogg.model.BindPhoneVo;
import com.smx.vlogg.model.dto.LoginDto;
import com.smx.vlogg.model.dto.PhoneLoginDto;
import com.smx.vlogg.model.dto.WxLoginDto;
import com.smx.vlogg.model.entity.Feedback;
import com.smx.vlogg.model.entity.User;
import org.springframework.web.multipart.MultipartFile;

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

    /**
     * 修改用户密码
     * @param user
     * @return
     */
    boolean updatePassword(User user);

    /**
     * 上传用户反馈信息
     * @param feedback
     * @return
     */
    boolean insertFeedback(Feedback feedback);

    /**
     * 上传文件到OSS
     * @param file
     * @return
     */
    String uploadFile(MultipartFile file);


    /**
     * 微信登录
     * @param wxLoginDto
     * @return
     */
    User wxLogin(WxLoginDto wxLoginDto);


    /**
     * 根据openID添加手机号
     * @param openId
     * @param phone
     * @return
     */
    boolean updatePhoneByOpenId(BindPhoneVo bindPhoneVo);

}
