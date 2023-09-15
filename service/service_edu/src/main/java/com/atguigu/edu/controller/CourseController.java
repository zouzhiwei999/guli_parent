package com.atguigu.edu.controller;


import com.atguigu.commomutils.R;
import com.atguigu.edu.entity.Course;
import com.atguigu.edu.entity.vo.CourseInfoVo;
import com.atguigu.edu.entity.vo.CoursePublishVo;
import com.atguigu.edu.entity.vo.chapter.CourseVo;
import com.atguigu.edu.service.CourseService;
import com.atguigu.servicebase.exception.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @ApiOperation(value = "获取全部课程信息与简介")
    @PostMapping("/{current}/{size}")
    public R getCourseInfoList(
            @PathVariable("current")Long current,
            @PathVariable("size")Long size,
            @RequestBody(required = false) CourseVo course){

        String title = course.getTitle();
        String status = course.getStatus();

        QueryWrapper<Course> wrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(title)){
            wrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(status)){
            wrapper.eq("status", status);
        }

        Page<Course> page = new Page<>(current, size);

        IPage<Course> page1 = service.page(page, wrapper);

        List<Course> records = page1.getRecords();
        long total = page1.getTotal();

        return R.ok().data("list", records).data("total",total);
    }

    @ApiOperation(value = "修改课程信息与简介")
    @PutMapping()
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {

        service.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    @ApiOperation(value = "获取课程发布信息")
    @GetMapping("/getCoursePublishVo/{id}")
    public R getCoursePublishVo(@PathVariable("id") String id){
        CoursePublishVo coursePublishVo = service.getCoursePublishVo(id);
        return R.ok().data("coursePublishVo",coursePublishVo);
    }

    @ApiOperation(value = "课程发布，改变status    ")
    @PostMapping("/publishCourse/{id}")
    public R publishCourse(@PathVariable("id")String id){
        Course course = new Course();
        course.setId(id);
        course.setStatus("Normal");
        boolean b = service.saveOrUpdate(course);
        if (!b){
            throw new GuliException(20001, "课程未能发布");
        }
        return R.ok();
    }

    @ApiOperation(value = "删除课程及相关信息")
    @DeleteMapping("{id}")
    public R deleteCourse(@PathVariable("id")String id){
        service.deleteCourse(id);
        return R.ok();
    }

}