package com.atguigu.edu.service.impl;

import com.atguigu.edu.entity.CourseDescription;
import com.atguigu.edu.mapper.CourseDescriptionMapper;
import com.atguigu.edu.service.CourseDescriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-07-21
 */
@Service
public class CourseDescriptionServiceImpl extends ServiceImpl<CourseDescriptionMapper, CourseDescription> implements CourseDescriptionService {

    @Override
    public void deleteDescription(String id) {
        if (!StringUtils.isEmpty(id)){
            baseMapper.deleteById(id);
        }
    }
}
