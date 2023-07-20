package com.atguigu.oss.controller;

import com.atguigu.commomutils.R;
import com.atguigu.oss.service.impl.OssServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
public class OssController {

    @Autowired
    private OssServiceImpl ossService;

    @ApiOperation("上传功能")
    @PostMapping()
    public R uploadOssFile(@ApiParam(name="multipartFile" ,value = "多段文件", required = true) MultipartFile file) {
        String url = ossService.uploadAvatar(file);
        return R.ok().data("url", url);
    }
}
