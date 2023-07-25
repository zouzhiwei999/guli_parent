package com.atguigu.edu.service;

import com.atguigu.edu.entity.Course;
import com.atguigu.edu.entity.vo.CourseInfoVo;
import com.atguigu.edu.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-07-21
 */
public interface CourseService extends IService<Course> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String id);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo getCoursePublishVo(String id);
}
