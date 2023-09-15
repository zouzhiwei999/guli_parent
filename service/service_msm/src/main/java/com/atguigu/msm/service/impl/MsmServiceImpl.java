package com.atguigu.msm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.atguigu.msm.service.MsmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author AOA
 * @version 1.0
 * @description: 服务层
 * @date 2023/8/20 15:49
 */
@Service
@Slf4j
public class MsmServiceImpl implements MsmService {

    //只负责发送验证码，并且返回是否成功
    @Override
    public boolean send(Map<String, String> map, String phone) {

        if (StringUtils.isEmpty(phone)) return false;

        DefaultProfile profile = DefaultProfile.getProfile("default","LTAI5t6s6iDmJQVQzS7oJaRC","977W5s4nX1nruNiKtjlrE7vl5zEDYK");
        DefaultAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        //设置相关固定的参数
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");

        //参数
        request.putQueryParameter("PhoneNumbers", phone); //手机号
        request.putQueryParameter("SignName","阿里云短信测试"); //申请阿里云 签名名称
        request.putQueryParameter("TemplateCode","SMS_154950909"); //申请阿里云 模板code
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(map)); //验证码数据，转换json数据传递

        try {
            CommonResponse commonResponse = client.getCommonResponse(request);
            log.info("短信已发送");
            boolean success = commonResponse.getHttpResponse().isSuccess();
            return success;
        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }
    }
}
