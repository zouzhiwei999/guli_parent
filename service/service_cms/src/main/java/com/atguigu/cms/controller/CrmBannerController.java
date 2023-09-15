package com.atguigu.cms.controller;


import com.atguigu.cms.entity.CrmBanner;
import com.atguigu.cms.service.CrmBannerService;
import com.atguigu.commomutils.R;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-08-12
 */
@RestController
@RequestMapping("/cms/crm-banner")
@CrossOrigin
@Slf4j
public class CrmBannerController {

    @Autowired
    private CrmBannerService crmBannerService;

    @ApiOperation("获取全部banner")
    @GetMapping("/getAllBanner")
    public R getAllBanner(){
        log.info("========获取全部banner==========");
        List<CrmBanner> crmBannerList = crmBannerService.getAllBanner();

        return R.ok().data("list", crmBannerList);
    }
}

