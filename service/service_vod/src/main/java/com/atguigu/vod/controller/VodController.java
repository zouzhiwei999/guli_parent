package com.atguigu.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.commomutils.R;
import com.atguigu.servicebase.exception.GuliException;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.utils.ConstantPropertiesUtils;
import com.atguigu.vod.utils.InitVodClient;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author AOA
 * @version 1.0
 * @description: TODO
 * @date 2023/8/1 20:54
 */
@RestController
@RequestMapping("/vod/video")
@CrossOrigin
@Slf4j
public class VodController {

    @Autowired
    private VodService vodService;

    @ApiOperation("上传阿里云视频")
    @PostMapping("uploadVideo")
    public R uploadVideo(MultipartFile file){
        String videoId = vodService.uploadVideo(file);
        log.info(videoId);
        return R.ok().data("videoId",videoId);
    }

    @ApiOperation("删除阿里云视频：单个")
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable("id")String id){
        log.info("==============删除阿里云视频：单个");

        vodService.deleteVideo(id);
        return R.ok();
    }

    @ApiOperation("删除阿里云视频：多个")
    @DeleteMapping("batch")
    public R deleteBatch(@RequestParam List<String> VideoIdList){
        log.info("==============删除阿里云视频：多个");
        vodService.deleteBatch(VideoIdList);
        return R.ok();
    }

    @ApiOperation("获取视频凭证")
    @GetMapping("getVideoAuth/{videoId}")
    public R getVideoAuth(@PathVariable("videoId")String videoId){
        try {
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantPropertiesUtils.KEY_ID, ConstantPropertiesUtils.KEY_SECRET);
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();

            request.setVideoId(videoId);
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);

            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth", playAuth);
        } catch (ClientException e) {
            throw new GuliException(20001, "获取视屏凭证错误");
        }
    }
}
