package com.atguigu.edu.service;

import com.atguigu.edu.entity.Course;
import com.atguigu.edu.entity.frontVo.CourseFrontVo;
import com.atguigu.edu.entity.frontVo.CourseWebVo;
import com.atguigu.edu.entity.vo.CourseInfoVo;
import com.atguigu.edu.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

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

    void deleteCourse(String id);

    List<Course> getIndex();

    Course getCourseFrontInfoByTeacherId(String teacherId);

    //获取前台课程数据，按照条件以及分页 查询
    Map<String, Object> getCourseFrontInfoConditionalAndPage(Page<Course> coursePage, CourseFrontVo courseFrontVo);

    CourseWebVo getCourseFrontInfo(String courseId);
}
