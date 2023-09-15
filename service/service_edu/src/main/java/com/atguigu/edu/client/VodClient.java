package com.atguigu.edu.client;

import com.atguigu.commomutils.R;
import com.atguigu.edu.client.clientImpl.VodClientImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "service-vod",fallback = VodClientImpl.class)
@Component
public interface VodClient {

    @ApiOperation("删除阿里云视频：单个")
    @DeleteMapping("/vod/video/{id}")
    public R deleteVideo(@PathVariable("id")String id);

    @ApiOperation("删除阿里云视频：多个")
    @DeleteMapping("/vod/video/batch")
    public R deleteBatch(@RequestParam List<String> VideoIdList);
}
