package com.atguigu.statistics.service;

import com.atguigu.statistics.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-09-24
 */
public interface DailyService extends IService<Daily> {

    Daily countRegister(String day);

}
