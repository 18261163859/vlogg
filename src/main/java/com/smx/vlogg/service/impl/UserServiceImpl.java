package com.smx.vlogg.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.smx.vlogg.mapper.UserMapper;
import com.smx.vlogg.model.BindPhoneVo;
import com.smx.vlogg.model.dto.LoginDto;
import com.smx.vlogg.model.dto.PhoneLoginDto;
import com.smx.vlogg.model.dto.WxLoginDto;
import com.smx.vlogg.model.entity.Feedback;
import com.smx.vlogg.model.entity.User;
import com.smx.vlogg.service.RedisService;
import com.smx.vlogg.service.UserService;
import com.smx.vlogg.utils.AliyunResource;
import com.smx.vlogg.utils.FileResource;
import org.apache.catalina.mapper.Mapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

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

    @Resource
    private AliyunResource aliyunResource;

    @Resource
    private FileResource fileResource;

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
        saveUser.setPassword(user.getPassword());
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

    @Override
    public boolean updatePassword(User user) {
        try {
            user.setPassword(DigestUtils.md5Hex(user.getPassword()));
            userMapper.updatePassword(user);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean insertFeedback(Feedback feedback) {
        try {
            userMapper.insertFeedback(feedback);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public String uploadFile(MultipartFile file) {
        //读入配置文件信息
        String endpoint= fileResource.getEndpoint();
        String accessKeyId=aliyunResource.getAccessKeyId();
        String accessKeySecret=aliyunResource.getAccessKeySecret();
        //创建OSSClient实例
        OSS ossClient=new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);
        String fileName=file.getOriginalFilename();
        //分割文件名，获得文件后缀名
        assert fileName!=null;
        String[] fileNameArr=fileName.split("\\.");
        String suffix=fileNameArr[fileNameArr.length-1];
        //拼接得到新的上传文件名
        String uploadFileName=fileResource.getObjectName()+ UUID.randomUUID().toString()+"."+suffix;
        //上传网络需要用的字节流
        InputStream inputStream=null;
        try {
            inputStream=file.getInputStream();
        } catch (IOException e) {
            System.err.println("上传文件出现异常");
        }
        //执行阿里云上传文件操作
        ossClient.putObject(fileResource.getBucketName(),uploadFileName,inputStream);
        //关闭OSSClient
        ossClient.shutdown();
        return uploadFileName;
    }

    @Override
    public User wxLogin(WxLoginDto wxLoginDto) {
        User user=null;
        try {
            user=userMapper.findUserByOpenId(wxLoginDto.getWxOpenId());
        } catch (SQLException throwables) {
            System.err.println("根据微信OpenId查找用户出现异常");
        }
        //新用户
        if (user == null) {
            user=User.builder()
                    .wxOpenId(wxLoginDto.getWxOpenId())
                    .nickname(wxLoginDto.getNickname())
                    .avatar(wxLoginDto.getAvatar())
                    .gender(wxLoginDto.getGender())
                    .createTime(LocalDateTime.now())
                    .build();
            try {
                userMapper.insert(user);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        //老用户
        return user;
    }

    @Override
    public boolean updatePhoneByOpenId(BindPhoneVo bindPhoneVo) {
        boolean flag=redisService.existsKey(bindPhoneVo.getPhone());
        if(flag){
            //取出redis中之前存储的验证码
            String saveCode=redisService.getValue(bindPhoneVo.getPhone(),String.class);
            //和前端传的验证码比对，比对成功
            if(saveCode!=null&&saveCode.equalsIgnoreCase(bindPhoneVo.getCode())){
                //查找数据库该手机号用户是否存在
                User user= null;
                try {
                    user = userMapper.findUserByOpenId(bindPhoneVo.getVxOpenId());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                if(user==null){
                    return false;
                }

                try {
                    userMapper.updatePhoneByOpenId(bindPhoneVo.getPhone(),bindPhoneVo.getVxOpenId());
                    return true;
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        }


        return false;
    }


}
