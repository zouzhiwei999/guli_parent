package com.atguigu.ucenter.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author AOA
 * @version 1.0
 * @description: 微信常量类
 * @date 2023/8/30 16:17
 */
@Component
public class ConstantWxUtils implements InitializingBean {

    public static String WX_OPEN_APP_ID;
    public static String WX_OPEN_APP_SECRET;
    public static String WX_OPEN_REDICRET_URL;

    @Value("${wx.open.app_id}")
    private String wx_open_app_id;
    @Value("${wx.opoen.app_secret}")
    private String wx_open_app_secret;
    @Value("${wx.open.redicret_url}")
    private String wx_open_redicret_url;


    @Override
    public void afterPropertiesSet() throws Exception {
        WX_OPEN_APP_ID = wx_open_app_id;
        WX_OPEN_APP_SECRET = wx_open_app_secret;
        WX_OPEN_REDICRET_URL =wx_open_redicret_url;
    }
}
