package com.atguigu.ucenter.controller;

import com.atguigu.commomutils.JwtUtils;
import com.atguigu.servicebase.exception.GuliException;
import com.atguigu.ucenter.entity.Member;
import com.atguigu.ucenter.entity.vo.RegisterVo;
import com.atguigu.ucenter.service.MemberService;
import com.atguigu.ucenter.utils.ConstantWxUtils;
import com.atguigu.ucenter.utils.HttpClientUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author AOA
 * @version 1.0
 * @description: 微信api接口，用户点击微信登录，以及扫码后的反馈
 * @date 2023/8/31 12:53
 */
@CrossOrigin
@Controller
@RequestMapping("/api/ucenter/wx")
@Api(tags = "微信api")
@Slf4j
public class WxApiController {

    @Autowired
    private MemberService memberService;

    @ApiOperation("微信开放平台重定向回来")
    @GetMapping("callback")
    public String callback(String code, String state, HttpSession session ){

        //得到授权临时票据code
        log.info("code = " + code);
        log.info("state = " + state);

        //第二步：通过code获取access_token,向微信发送固定请求，获取access_token接口调用凭证 和 openid授权用户唯一标识
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";

        String accessTokenUrl = String.format(
                baseAccessTokenUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                ConstantWxUtils.WX_OPEN_APP_SECRET,
                code);

        //使用这个固定请求，还要接受返回值，使用httpClient,(请求参数在路径上，使用get请求)
        String accessTokenInfo = "";
        try {
            accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            log.info(accessTokenInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //将返回的数据提取accessToken和openid,接收json字符串,转成Map
        Gson gson = new Gson();
        Map map = gson.fromJson(accessTokenInfo, HashMap.class);

        String access_token = (String) map.get("access_token");
        String openid = (String) map.get("openid");//用户唯一标识

        //判断该openid是否已经注册过
        Member member = memberService.selectMemberByOpenid(openid);

        if (member == null){
            //如果数据库等于空，该微信未注册，先将数据写入数据库再登录

            //第三步：通过access_token调用接口,获取微信登录的用户信息
            String baseUserInfoUrl =  "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";

            String userInfoUrl = String.format(baseUserInfoUrl, access_token, openid);

            String userInfo = "";
            try {
                userInfo = HttpClientUtils.get(userInfoUrl);

            } catch (Exception e) {
                e.printStackTrace();
            }
            Map map1 = gson.fromJson(userInfo, HashMap.class);
            String nickname = (String) map1.get("nickname");//昵称
            String headimgurl = (String) map1.get("headimgurl");//头像

            member = new Member();
            member.setOpenid(openid);
            member.setNickname(nickname);
            member.setAvatar(headimgurl);

            boolean save = memberService.save(member);
            if (!save){
                throw new GuliException(20001, "微信第一次登录注册失败");
            }
        }
        //如果不等于空，该微信已注册，直接登录,返回token就行

        String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());


        return "redirect:http://localhost:3000?token="+jwtToken;
    }

    @ApiOperation("用户登录，向微信转发，响应二维码")
    @GetMapping("login")
    public String getCode(){

        //第一步：请求CODE
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=zzw" +
                "#wechat_redirect";

        //使用urlEncode对链接进行处理
        String redirect_uri = ConstantWxUtils.WX_OPEN_REDICRET_URL;
        try {
            redirect_uri = URLEncoder.encode(redirect_uri, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = String.format(baseUrl, ConstantWxUtils.WX_OPEN_APP_ID, redirect_uri);

        return "redirect:" + url;
    }
}
