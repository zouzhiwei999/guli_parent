package com.atguigu.edu.service.impl;

import com.atguigu.edu.entity.Video;
import com.atguigu.edu.mapper.VideoMapper;
import com.atguigu.edu.service.VideoService;
import com.atguigu.servicebase.exception.GuliException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-07-21
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {
    @Override
    public void saveVideo(Video video) {
        int insert = baseMapper.insert(video);
        if (insert <= 0){
            throw new GuliException(20001, "保存video失败");
        }
    }
}
