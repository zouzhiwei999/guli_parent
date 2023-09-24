package com.atguigu.statistics.controller;


import com.atguigu.commomutils.R;
import com.atguigu.statistics.entity.Daily;
import com.atguigu.statistics.service.DailyService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-09-24
 */
@RestController
@RequestMapping("/statistics/daily")
@CrossOrigin
@Slf4j
public class DailyController {

    @Autowired
    private DailyService dailyService;

    @ApiOperation("传送日期，得到该日期各数据")
    @GetMapping("countRegister/{day}")
    public R countRegister(@PathVariable("day")String day){
        log.info("==============Statistics调用==============");
        Daily daily  = dailyService.countRegister(day);
        return R.ok().data("daily", daily);
    }

}

