package com.atguigu.ucenter.service;

import com.atguigu.ucenter.entity.EduComment;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-09-20
 */
public interface EduCommentService extends IService<EduComment> {

    Map getCommentPage(Page<EduComment> objectPage,String courseId);

    void addComment(EduComment eduComment);
}
