package com.atguigu.edu.controller;


import com.atguigu.commomutils.R;
import com.atguigu.edu.entity.vo.CourseInfoVo;
import com.atguigu.edu.entity.vo.CoursePublishVo;
import com.atguigu.edu.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-07-21
 */
@RestController
@RequestMapping("/edu/course")
@Api(tags = "course模块")
@CrossOrigin
public class CourseController {

    @Autowired
    private CourseService service;

    @ApiOperation(value = "保存课程信息与简介")
    @PostMapping("saveCourseInfo")
    public R saveCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        String id = service.saveCourseInfo(courseInfoVo);
        return R.ok().data("id", id);
    }

    @ApiOperation(value = "获取课程信息与简介")
    @GetMapping("{id}")
    public R getCourseInfo(@PathVariable("id") String id) {
        CourseInfoVo course = service.getCourseInfo(id);
        return R.ok().data("course", course);
    }

    @ApiOperation(value = "修改课程信息与简介")
    @PutMapping()
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {

        service.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    @GetMapping("/getCoursePublishVo/{id}")
    public R getCoursePublishVo(@PathVariable("id") String id){
        CoursePublishVo coursePublishVo = service.getCoursePublishVo(id);
        return R.ok().data("coursePublishVo",coursePublishVo);
    }

}