package com.atguigu.oss.controller;

import com.atguigu.commomutils.R;
import com.atguigu.oss.service.impl.OssServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author AOA
 * @version 1.0
 * @description: TODO
 * @date 2023/7/18 0:59
 */
@Api(tags = "oss上传模块")
@RestController
@RequestMapping("/oss/fileUpload")
@CrossOrigin
@Slf4j
public class OssController {

    @Autowired
    private OssServiceImpl ossService;

    @ApiOperation("上传功能")
    @PostMapping()
    public R uploadOssFile(MultipartFile file) {
        log.info(new DateTime().toString("yyyy/MM/dd"));
        String url = ossService.uploadAvatar(file);
        log.info(url);

        return R.ok().data("url", url);
    }
}
