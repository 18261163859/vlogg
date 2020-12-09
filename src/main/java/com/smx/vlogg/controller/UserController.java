package com.smx.vlogg.controller;

import com.aliyuncs.kms.transform.v20160120.TagResourceResponseUnmarshaller;
import com.smx.vlogg.common.ResponseResult;
import com.smx.vlogg.common.ResultCode;
import com.smx.vlogg.mapper.UserMapper;
import com.smx.vlogg.model.BindPhoneVo;
import com.smx.vlogg.model.dto.LoginDto;
import com.smx.vlogg.model.dto.PhoneLoginDto;
import com.smx.vlogg.model.dto.WxLoginDto;
import com.smx.vlogg.model.entity.Feedback;
import com.smx.vlogg.model.entity.User;
import com.smx.vlogg.service.RedisService;
import com.smx.vlogg.service.UserService;
import com.smx.vlogg.utils.FileResource;
import com.smx.vlogg.utils.SmsUtil;
import com.smx.vlogg.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.sql.SQLException;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author moses
 * @Date 2020/12/5
 **/
@RestController
@RequestMapping(value = "/api/user")
@Slf4j
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private SmsUtil smsUtil;
    @Resource
    private RedisService redisService;
    @Resource
    private FileResource fileResource;

    @PostMapping(value = "/login")
    ResponseResult login(@RequestBody LoginDto loginDto){
        log.info("loginDto:{loginDto}");
        boolean flag=userService.login(loginDto);
        if(flag){
            return ResponseResult.success(userService.getUser(loginDto.getPhone()));
        }
        else{
            return ResponseResult.failure(ResultCode.USER_SIGN_IN_FAIL, false);
        }
    }

    @PostMapping(value = "/sendcode")
    public ResponseResult sendCode(@RequestParam String phone){
        //随机验证码
        String code= StringUtil.getVerifyCode();
        //给入参手机号发送短信
        boolean flag=smsUtil.sendSms(phone,code);
        if(flag){
            //验证码存入redis，1分钟有效
            redisService.set(phone,code,1L);
            return ResponseResult.success(code);
        }
        else{
            //验证码存入redis，1分钟有效
            redisService.set(phone,code,1L);
            return ResponseResult.failure(ResultCode.SMS_ERROR);
        }
    }

    @PostMapping(value = "/phonelogin")
    public ResponseResult login(@RequestBody PhoneLoginDto phoneLoginDto){
        log.info("phoneLoginDto:"+phoneLoginDto);
        boolean flag=userService.phoneLogin(phoneLoginDto);
        if(flag){
            return ResponseResult.success(userService.getUser(phoneLoginDto.getPhone()));
        }else{
            return ResponseResult.failure(ResultCode.USER_VERIFY_CODE_ERROR);
        }
    }

    @PostMapping(value = "/update")
    public ResponseResult update(@RequestBody User user){
        log.info("user:"+user);
        User newUser = userService.updateUser(user);
        return ResponseResult.success(newUser);
    }

    @PostMapping(value = "/updatepassword")
    public ResponseResult updatePassword(@RequestBody User user){
        log.info("user:"+user);
        boolean flag=userService.updatePassword(user);
        if(flag){
            return ResponseResult.success("修改成功");
        }else{
            return ResponseResult.failure(ResultCode.SYSTEM_INNER_ERROR);
        }
    }

    @PostMapping(value = "/upload")
    public ResponseResult uploadFile(MultipartFile file){
        //声明图片的地址路径，返回到前端
        String path=null;
        //判断文件不能为空
        if(file!=null){
            //获得文件上传的名称
            String fileName=file.getOriginalFilename();
            System.out.println(fileName);
            //调用上传服务，得到上传后的新文件名
            path = userService.uploadFile(file);

        }
        if(StringUtils.isNotBlank(path)){
            //拼接上服务器地址前缀，得到最终返回给前端的url
            path=fileResource.getOssHost()+path;
        }
        return ResponseResult.success(path);
    }


    @PostMapping(value = "/insertfeedback")
    public ResponseResult insertFeedback(@RequestBody Feedback feedback){
        log.info("feedback:"+feedback);
        boolean flag=userService.insertFeedback(feedback);
        if(flag){
            return ResponseResult.success("反馈成功");
        }
        else{
            return  ResponseResult.failure(ResultCode.SYSTEM_INNER_ERROR);
        }
    }

    @PostMapping(value = "/wxLogin")
    public ResponseResult wxLogin(@RequestBody WxLoginDto wxLoginDto) {
        log.info("wxLoginDto:"+wxLoginDto);
        User user = userService.wxLogin(wxLoginDto);
        if (user != null) {
            return ResponseResult.success(user);
        }
        return ResponseResult.failure(ResultCode.USER_SIGN_IN_FAIL);
    }

    @PostMapping(value = "/updatephone")
    public ResponseResult updatePhoneByOpenId(@RequestBody BindPhoneVo bindPhoneVo){
        log.info("BindPhoneVo:"+bindPhoneVo);
        boolean flag=userService.updatePhoneByOpenId(bindPhoneVo);
        if(flag){
            return ResponseResult.success("绑定成功！");
        }
        return ResponseResult.failure(ResultCode.SYSTEM_INNER_ERROR);
    }


}
