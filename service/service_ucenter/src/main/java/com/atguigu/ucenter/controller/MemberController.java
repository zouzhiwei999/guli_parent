package com.atguigu.ucenter.controller;


import com.atguigu.commomutils.JwtUtils;
import com.atguigu.commomutils.R;
import com.atguigu.servicebase.exception.GuliException;
import com.atguigu.ucenter.entity.Member;
import com.atguigu.ucenter.entity.vo.MemberVo;
import com.atguigu.ucenter.entity.vo.RegisterVo;
import com.atguigu.ucenter.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-08-21
 */
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
@Slf4j
public class MemberController {

    @Autowired
    private MemberService memberService;

    @ApiOperation("前台用户注册")
    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVo registerVo) {
        log.info("=========注册===========");
        memberService.register(registerVo);
        return R.ok();
    }

    @ApiOperation("前台用户提交登录,返回带有身份信息的token")
    @PostMapping("login")
    public R submitLogin(@RequestBody Member member){
        String token = memberService.login(member);
        return R.ok().data("token",token);
    }

    @ApiOperation("前台用户信息获取，利用token JWT技术 Json Web Token")
    @GetMapping("getMemberInfo")
    public R getMember(HttpServletRequest request){
          String memberId = JwtUtils.getMemberIdByJwtToken(request);
        Member member = memberService.getById(memberId);

        if (member == null){
            throw new GuliException(20001, "找不到该id的用户");
        }
        //声明memberVo
        MemberVo memberVo = new MemberVo();
        //复制信息到memberVo
        BeanUtils.copyProperties(member, memberVo);

        log.info("member: " + memberVo);
        return R.ok().data("userInfo",memberVo);
    }
}

