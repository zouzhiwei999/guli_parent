package com.atguigu.edu.service;

import com.atguigu.edu.entity.Course;
import com.atguigu.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-05-25
 */
public interface TeacherService extends IService<Teacher> {

    List<Teacher> getIndex();

    Map<String, Object> getTeacherFrontList(Page<Teacher> teacherPage);

}
