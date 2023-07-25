package com.atguigu.edu.mapper;

import com.atguigu.edu.entity.Course;
import com.atguigu.edu.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2023-07-21
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {
    public CoursePublishVo getCoursePublishVo(@Param("courseId") String courseId);
}
