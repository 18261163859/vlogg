package com.smx.vlogg.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.swing.text.html.FormSubmitEvent;

/**
 * @ClassName SmsUtil
 * @Description TODO
 * @Author moses
 * @Date 2020/12/6
 **/
@Component
public class SmsUtil {

    @Resource
    public AliyunResource aliyunResource;

    public boolean sendSms(String mobile,String code){
        System.out.println(aliyunResource.getAccessKeyId());
        DefaultProfile profile=DefaultProfile.getProfile("cn-hangzhou",
                aliyunResource.getAccessKeyId(),
                aliyunResource.getAccessKeySecret());
        IAcsClient client=new DefaultAcsClient(profile);
        CommonRequest request=new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId","cn-hangzhou");
        request.putQueryParameter("PhoneNumbers",mobile);
        request.putQueryParameter("SignName","明星哥");
        request.putQueryParameter("TemplateCode","SMS_206546325");
        request.putQueryParameter("TemplateParam","{\"code\":\""+code+"\"}");
        try {
            CommonResponse response=client.getCommonResponse(request);
            System.out.println(response.getData());
//            String resData=response.getData();
//            //将返回的json字符串转成json对象
//            JSONObject jsonObject=JSON.parseObject(resData);
//            //发送成功
//            if("OK".equals(jsonObject.get("Code"))){
//                return true;
//            }

        } catch (ClientException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }
}
