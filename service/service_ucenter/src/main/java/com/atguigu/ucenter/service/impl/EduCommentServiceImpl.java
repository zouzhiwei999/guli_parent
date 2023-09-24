package com.atguigu.ucenter.service.impl;

import com.atguigu.servicebase.exception.GuliException;
import com.atguigu.ucenter.entity.EduComment;
import com.atguigu.ucenter.mapper.EduCommentMapper;
import com.atguigu.ucenter.service.EduCommentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-09-20
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    //分页查询评论
    @Override
    public Map getCommentPage(Page<EduComment> page, String courseId) {

        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);

        IPage<EduComment> eduCommentIPage = baseMapper.selectPage(page, wrapper);

        if (page == null){
            throw new GuliException(20001, "暂无评论");
        }

        long current = page.getCurrent();
        List<EduComment> records = page.getRecords();
        long size = page.getSize();
        long total = page.getTotal();
        long pages = page.getPages();
        boolean hasNext = page.hasNext();
        boolean hasPrevious = page.hasPrevious();


        Map<String, Object> map = new HashMap<>();
        map.put("records",records);
        map.put("current",current);
        map.put("pages",pages);
        map.put("total",total);
        map.put("size",size);
        map.put("hasNext",hasNext);
        map.put("hasPrevious",hasPrevious);

        return map;
    }

    //添加评论
    @Override
    public void addComment(EduComment eduComment) {
        int insert = baseMapper.insert(eduComment);
        if (insert <= 0){
            throw new GuliException(20001, "添加评论失败");
        }
    }
}
