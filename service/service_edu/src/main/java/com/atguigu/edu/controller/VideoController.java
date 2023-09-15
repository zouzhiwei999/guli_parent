package com.atguigu.edu.controller;


import com.atguigu.commomutils.R;
import com.atguigu.edu.entity.Video;
import com.atguigu.edu.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class VideoController {

    @Autowired
    private VideoService service;

    @PostMapping
    public R saveVideo(@RequestBody Video video){
        log.info("=========" + video);
        service.saveVideo(video);
        return R.ok();
    }

    @ApiOperation("删除小节")
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable("id")String id){
        service.deleteVideo(id);
        return R.ok();
    }
}

