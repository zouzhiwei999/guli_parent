package com.atguigu.edu.controller.front;

import com.atguigu.edu.entity.Course;
import com.atguigu.edu.entity.Teacher;
import com.atguigu.edu.service.CourseService;
import com.atguigu.edu.service.TeacherService;
import com.atguigu.commomutils.R;
import com.atguigu.servicebase.exception.GuliException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author AOA
 * @version 1.0
 * @description: 给前台首页显示 4教师和 8课程
 * @date 2023/8/11 20:34
 */
@RestController
@RequestMapping("/edu/indexfront")
@CrossOrigin
public class IndexFrontController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;

    @ApiOperation("返回首页的4个教师和8个课程")
    @GetMapping("/index")
    public R index(){
        List<Course> course= courseService.getIndex();
        List<Teacher> teacher = teacherService.getIndex();
        if (course == null || teacher == null){
            throw new GuliException(20001, "获取首页教师与课程失误");
        }

        return R.ok().data("course",course).data("teacher", teacher);
    }
}
