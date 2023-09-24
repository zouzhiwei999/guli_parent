package com.atguigu.ucenter.controller;


import com.atguigu.commomutils.JwtUtils;
import com.atguigu.commomutils.R;
import com.atguigu.servicebase.exception.GuliException;
import com.atguigu.ucenter.entity.EduComment;
import com.atguigu.ucenter.entity.Member;
import com.atguigu.ucenter.service.EduCommentService;
import com.atguigu.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-09-20
 */
@RestController
@RequestMapping("/educenter/comment")
@CrossOrigin
public class EduCommentController {

    @Autowired
    private EduCommentService eduCommentService;

    @Autowired
    private MemberService memberService;

    //根据课程id来分页查询评论
    @GetMapping("/getCommentPage/{page}/{size}")
    public R getCommentPage(@PathVariable("page")long page,@PathVariable("size")long size,@RequestParam("courseId") String courseId){
        Page<EduComment> objectPage = new Page<>(page, size);
        Map map = eduCommentService.getCommentPage(objectPage, courseId);
        return R.ok().data("map", map);
    }

    //添加评论
    @PostMapping("addComment")
    public R addComment(@RequestBody EduComment eduComment, HttpServletRequest request){

        //判断是否已经登录,获取用户ID
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //如果没登录
        if (StringUtils.isEmpty(memberId)){
            return R.error().message("请登录").code(28004);
        }

        //设置该条评论的用户ID
        eduComment.setMemberId(memberId);

        //还要设置该条评论的用户名和用户头像
        Member member = memberService.getById(memberId);

        eduComment.setNickname(member.getNickname());
        eduComment.setAvatar(member.getAvatar());


        eduCommentService.addComment(eduComment);
        return R.ok();
    }

}

