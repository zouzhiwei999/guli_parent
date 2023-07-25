package com.atguigu.edu.controller;


import com.atguigu.commomutils.R;
import com.atguigu.edu.entity.Video;
import com.atguigu.edu.service.VideoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-07-21
 */
@RestController
@RequestMapping("/edu/video")
@CrossOrigin
@Api(tags = "video模块")
public class VideoController {

    @Autowired
    private VideoService service;

    @PostMapping
    public R saveVideo(@RequestBody Video video){
        service.saveVideo(video);
        return R.ok();
    }
}

