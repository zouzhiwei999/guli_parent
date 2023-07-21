package com.atguigu.edu.controller;


import com.atguigu.commomutils.R;
import com.atguigu.edu.entity.vo.SubjectOneVO;
import com.atguigu.edu.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-07-19
 */
@RestController
@RequestMapping("/edu/subject")
@CrossOrigin
@Api(tags = "课程模块")
public class SubjectController {

    @Autowired
    private SubjectService service;


    @ApiOperation("读取excel信息到数据库")
    @PostMapping("addSubject")
    public R readExcel(@ApiParam(name = "file", value = "多段文件", required = true) MultipartFile file) {
        service.readExcel(file , service);
        return R.ok();
    }

    //返回分级显示的一级课程和二级课程
    @ApiOperation("获取课程分类信息")
    @GetMapping("getAllSubject")
    public R getAllSubject() {
        List<SubjectOneVO> list = service.getAllSubject();
        return R.ok().data("data2", list);
    }

}

