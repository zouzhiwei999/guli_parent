package com.atguigu.edu.controller;


import com.atguigu.commomutils.R;
import com.atguigu.edu.entity.Teacher;

import com.atguigu.edu.entity.vo.TeacherVO;
import com.atguigu.edu.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-05-25
 */
@Api(tags = "teacher模块")
@RestController
@RequestMapping("/edu/teacher")
@CrossOrigin
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    //返回全部teacher
    @ApiOperation("查找teacher")
    @GetMapping("/findAll")
    public R findAll() {
        return R.ok().data("items", teacherService.list(null));
    }

    //逻辑删除teacher
    @ApiOperation("删除teacher")
    @DeleteMapping("/{id}")
    public R removeById(
            @ApiParam(name="id",value = "删除的teacherid",required = true)
            @PathVariable("id")String id){
        if (teacherService.removeById(id)) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //分页显示teacher
    @ApiOperation("分页显示teacher")
    @GetMapping("/pageTeacher/{current}/{size}")
    public R pageTeacher(
            @PathVariable Long current,
            @PathVariable Long size
    ) {
        Page<Teacher> teacherPage = new Page<>(current, size);
        IPage<Teacher> page = teacherService.page(teacherPage, null);

        List<Teacher> records = page.getRecords();
        long total = page.getTotal();

        return R.ok().data("records", records).data("total", total);
    }

    //有条件的分页显示teacher
    //因为多个查询条件，可以把查询条件变为实体类，更容易接收数据
    //使用前端发送json数据到后端,使用@RequestBody注解接收，
    //@RequestBody注解接受的是请求体，所以要用post请求
    @ApiOperation("条件查询teacher")
    @PostMapping("/pageTeacherConditionol/{current}/{size}")
    public R pageTeacherConditionol(
            @PathVariable Long current,
            @PathVariable Long size,
            @ApiParam(name = "teacherVO",required = false, value = "用来查询teacher的条件")@RequestBody(required = false)TeacherVO teacherVO
            ) {

        //创建Page
        Page<Teacher> teacherPage = new Page<>(current, size);
        //构建条件
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();

        String name = teacherVO.getName();
        Integer level = teacherVO.getLevel();
        Date begin = teacherVO.getBegin();
        Date end = teacherVO.getEnd();

        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_modified", end);
        }

//        try {
//            int i = 10/0;
//        } catch (Exception e) {
//            throw new GuliException(10086, "你触发了异常，抛出自定义异常");
//        }

        wrapper.orderByDesc("gmt_modified");

        IPage<Teacher> page = teacherService.page(teacherPage, wrapper);

        List<Teacher> records = page.getRecords();
        long total = page.getTotal();

        return R.ok().data("records", records).data("total", total);
    }

    //修改teacher第一步,查询某一个teacher
    @GetMapping("/getTeacher/{id}")
    @ApiOperation("查询某一个teacher")
    public R getTeacher(
            @ApiParam(name = "id",required = true, value = "用来查询teacher的id") @PathVariable("id") String id
    ) {

        Teacher teacher = teacherService.getById(id);

        return R.ok().data("teacher", teacher);
    }

    //修改teacher第二步,提交一个teacher
    @ApiOperation("修改某一个teacher")
    @PutMapping("/update")
    public R updataTeacher(
            @RequestBody Teacher teacher
    ) {
        if (teacherService.updateById(teacher)) {
            return R.ok();
        } else {
            return R.error().message("升级teacher失败");
        }
    }

    //新增一个teacher
    @ApiOperation("保存一个teacher")
    @PostMapping("/save")
    public R save(
            @ApiParam(name = "teacher", required = false, value = "新增的teacher")
            @RequestBody Teacher teacher
    ){
        boolean save = teacherService.save(teacher);
        if (save) {
            return R.ok();
        } else {
            return R.error().message("新增teacher失败");
        }
    }

}

