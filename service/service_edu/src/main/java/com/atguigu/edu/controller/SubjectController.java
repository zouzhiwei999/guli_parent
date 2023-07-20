package com.atguigu.edu.controller;


import com.atguigu.commomutils.R;
import com.atguigu.edu.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
public class SubjectController {

    @Autowired
    private SubjectService service;


    @PostMapping("addSubject")
    public R readExcel(MultipartFile file) {
        service.readExcel(file , service);
        return R.ok();
    }

}

