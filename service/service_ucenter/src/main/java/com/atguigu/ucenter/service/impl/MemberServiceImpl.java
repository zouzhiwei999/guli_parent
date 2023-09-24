package com.atguigu.ucenter.service.impl;

import com.atguigu.commomutils.JwtUtils;
import com.atguigu.commomutils.MD5;
import com.atguigu.servicebase.exception.GuliException;
import com.atguigu.ucenter.entity.Member;
import com.atguigu.ucenter.entity.vo.RegisterVo;
import com.atguigu.ucenter.mapper.MemberMapper;
import com.atguigu.ucenter.service.MemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-08-21
 */
@Service
@Slf4j
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public String login(Member member) {

        //获取手机号和密码
        String mobile = member.getMobile();
        String password = member.getPassword();

        //是否为空
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new GuliException(20001, "手机号或密码为空");
        }

        //该手机号是否存在
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Member mobileMember = baseMapper.selectOne(wrapper);

        if (mobileMember == null){
            throw new GuliException(20001, "该手机号未注册");
        }
        //是否密码错误
        if (!MD5.encrypt(password).equals(mobileMember.getPassword())){
            throw new GuliException(20001, "密码错误");
        }
        //是否禁用
        if (mobileMember.getIsDisabled()){
            throw new GuliException(20001, "该账户不可用");
        }

        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());

        log.info("返回token: " + jwtToken);
        return jwtToken;
    }

    @Override
    public void register(RegisterVo registerVo) {
        //获取注册的数据
        String code = registerVo.getCode(); //验证码
        log.info("=========" + code);
        String mobile = registerVo.getMobile(); //手机号
        String nickname = registerVo.getNickname(); //昵称
        String password = registerVo.getPassword(); //密码

        //非空判断
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname)) {
            throw new GuliException(20001,"注册失败");
        }

        String redisCode = redisTemplate.opsForValue().get(mobile);
        log.info("=========" + redisCode);

        if (!code.equals(redisCode)){
            throw new GuliException(20001, "注册失败,验证码不一致");
        }

        //判断手机号是否重复
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Integer integer = baseMapper.selectCount(wrapper);

        if (integer > 0){
            throw new GuliException(20001, "该手机号已经被占用");
        }

        Member member = new Member();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);//用户不禁用
        member.setAvatar("guangzhou.aliyuncs.com/2023/07/19/15091886639546b78f2b71872d10c2a9.png");

        int insert = baseMapper.insert(member);
        if (insert <= 0){
            throw new GuliException(20001, "注册最后一步失败");
        }

    }

    @Override
    public Member selectMemberByOpenid(String openid) {
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        Member member = baseMapper.selectOne(wrapper);

        return member;
    }

    //得到该日期注册人数
    @Override
    public Integer countRegister(String day) {
        Integer num = memberMapper.countRegister(day);
        if (num < 0) {
            throw new GuliException(20001, "数据库中没有该日期的注册人数统计");
        }
        return num;
    }
}
