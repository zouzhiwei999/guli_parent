package com.atguigu.edu.service.impl;

import com.atguigu.edu.entity.Course;
import com.atguigu.edu.entity.CourseDescription;
import com.atguigu.edu.entity.vo.CourseInfoVo;
import com.atguigu.edu.entity.vo.CoursePublishVo;
import com.atguigu.edu.mapper.CourseDescriptionMapper;
import com.atguigu.edu.mapper.CourseMapper;
import com.atguigu.edu.service.CourseService;
import com.atguigu.servicebase.exception.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-07-21
 */
@Service
@Transactional
@Slf4j
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseDescriptionMapper mapper;

    @Autowired
    private CourseMapper courseMapper;

    //保存CourseInfoVo的信息到 Course 和 CourseDescription  一对一的关系 两者id相同
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        log.info("==================" + courseInfoVo.toString());

        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVo, course);
        log.info("==================" + course.toString());
        int insert = baseMapper.insert(course);
        log.info("==================" + insert);
        log.info("==================" + course.getId());

        if (insert <= 0) {
            throw new GuliException(20001, "添加新课程失败");
        }

        CourseDescription courseDescription = new CourseDescription();
        BeanUtils.copyProperties(courseInfoVo, courseDescription);
        courseDescription.setId(course.getId());
        int insert1 = mapper.insert(courseDescription);

        if (insert1 <= 0) {
            throw new GuliException(20001, "添加新课程简介失败");
        }
        return course.getId();
    }

    @Override
    public CourseInfoVo getCourseInfo(String id) {

        log.info("==========" + id);
        Course course = baseMapper.selectById(id);

        log.info("==========" + course);

        if (course == null){
            throw new GuliException(20001, "找不到该course信息");
        }

        CourseDescription courseDescription = mapper.selectById(id);

        if (course == null){
            throw new GuliException(20001, "找不到该courseDescription信息");
        }

        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(course,courseInfoVo);

        courseInfoVo.setDescription(courseDescription.getDescription());

        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVo, course);
        int i = baseMapper.updateById(course);

        if (i < 0) {
            throw new GuliException(20001, "修改course失败");
        }

        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setId(courseInfoVo.getId());
        courseDescription.setDescription(courseInfoVo.getDescription());

        int i1 = mapper.updateById(courseDescription);
        if (i1 < 0) {
            throw new GuliException(20001, "修改courseDescription失败");
        }

    }

    @Override
    public CoursePublishVo getCoursePublishVo(String id) {
        CoursePublishVo coursePublishVo = courseMapper.getCoursePublishVo(id);
        if (coursePublishVo == null){
            throw new GuliException(20001, "找不到coursePublishVo");
        }
        return coursePublishVo;
    }
}
