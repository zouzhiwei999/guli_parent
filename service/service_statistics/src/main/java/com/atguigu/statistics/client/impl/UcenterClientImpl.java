package com.atguigu.statistics.client.impl;

import com.atguigu.commomutils.R;
import com.atguigu.statistics.client.UcenterClient;
import org.springframework.stereotype.Component;

/**
 * @author AOA
 * @version 1.0
 * @description: TODO
 * @date 2023/9/24 17:25
 */
@Component
public class UcenterClientImpl implements UcenterClient {
    @Override
    public R countRegister(String day) {
        return R.error().message("该服务暂时无法连接");
    }
}
