package com.atguigu.statistics.service.impl;

import com.atguigu.commomutils.R;
import com.atguigu.servicebase.exception.GuliException;
import com.atguigu.statistics.client.UcenterClient;
import com.atguigu.statistics.entity.Daily;
import com.atguigu.statistics.mapper.DailyMapper;
import com.atguigu.statistics.service.DailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-09-24
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {

    //使用nacos,模块间相互调用
    @Autowired
    private UcenterClient ucenterClient;

    @Autowired
    private DailyMapper dailyMapper;

    //统计各数据人数
    @Override
    public Daily countRegister(String day) {

        //完善
        //统计时，先把之前的统计表记录删掉，避免重复记录，也可以把插入数据改位更新
        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        dailyMapper.delete(wrapper);

        //开始将数据整合，存入数据库
        R numRegister = ucenterClient.countRegister(day);
        Map<String, Object> data = numRegister.getData();
        Integer num = (Integer) data.get("num");

        Daily daily = new Daily();
        //统计日期
        daily.setDateCalculated(day);
        //该日期注册人数
        daily.setRegisterNum(num);
        //登录人数
        daily.setLoginNum(RandomUtils.nextInt(100,200));
        //每日播放视频数
        daily.setVideoViewNum(RandomUtils.nextInt(100,200));
        //每日新增课程数
        daily.setCourseNum(RandomUtils.nextInt(100,200));

        //插入数据库,完成统计
        int insert = dailyMapper.insert(daily);
        if (insert <= 0){
            throw new GuliException(20001, "插入'"+day+"'统计数据失败");
        }

        return daily;
    }
}
