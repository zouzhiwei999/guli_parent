package com.atguigu.edu.controller.front;

import com.atguigu.commomutils.R;
import com.atguigu.edu.entity.Course;
import com.atguigu.edu.entity.Teacher;
import com.atguigu.edu.service.CourseService;
import com.atguigu.edu.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.xml.bind.v2.model.core.ID;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author AOA
 * @version 1.0
 * @description: TODO
 * @date 2023/9/4 13:47
 */
@RestController
@RequestMapping("/edu/teacherfront")
@CrossOrigin
public class TeacherFrontController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CourseService courseService;

    @ApiOperation("获取前台教师信息集合")
    @GetMapping("/getTeacherFrontList/{page}/{size}")
    public R getTeacherFrontList(
            @ApiParam(name = "page",required = true,value = "页码") @PathVariable(value = "page")long page,
            @ApiParam(name = "size",required = true,value = "单页显示") @PathVariable(value = "size")long size){

        Page<Teacher> teacherPage = new Page<Teacher>(page,size);
        Map<String,Object> map = teacherService.getTeacherFrontList(teacherPage);


        return R.ok().data(map);
    }

    @ApiOperation("根据教师ID，获取教师信息和对应的课程信息")
    @GetMapping("/getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable(value = "teacherId",required = true)String teacherId){

        Teacher teacher = teacherService.getById(teacherId);

        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", teacherId);
        List<Course> courseList = courseService.list(wrapper);

        return R.ok().data("teacher",teacher).data("courseList",courseList);
    }



}
