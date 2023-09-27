package com.atguigu.statistics.scheduling;

import com.atguigu.commomutils.DateUtil;
import com.atguigu.statistics.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author AOA
 * @version 1.0
 * @description: TODO
 * @date 2023/9/27 17:58
 */
@Component
public class ScheduleTask {

    @Autowired
    private DailyService dailyService;

    @Scheduled(cron = "0 0 1 * * ?")
    public void task(){
        dailyService.countRegister(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }
}
