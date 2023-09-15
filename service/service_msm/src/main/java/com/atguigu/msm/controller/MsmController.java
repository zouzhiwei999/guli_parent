package com.atguigu.msm.controller;

import com.atguigu.commomutils.R;
import com.atguigu.msm.service.MsmService;
import com.atguigu.msm.utils.RandomUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author AOA
 * @version 1.0
 * @description: msm控制层
 * @date 2023/8/20 15:48
 */
@RestController
@RequestMapping("/msm")
@CrossOrigin
@Slf4j
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @ApiOperation("接收手机号，发送短信验证码")
    @GetMapping("send/{phone}")
    public R sendMsm(@PathVariable("phone")String phone){

        log.info("进入msm控制层");

        //1.判断该手机号是否在Redis存在
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)){
            return R.ok().message("该手机号已经发送过验证码");
        }

        //2.该手机号不存在Redis中
        //随机验证码
        code = RandomUtil.getFourBitRandom();

        log.info("验证码" + code);

        //存入Map,以json接收
        Map<String, String> param = new HashMap<>();
        param.put("code", code);

        //服务层存入Redis
        boolean result = msmService.send(param,phone);

        if (result){
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            return R.ok();
        }else{
            return R.error().message("验证码发送失败");
        }

    }

}
