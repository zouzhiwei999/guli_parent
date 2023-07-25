package com.atguigu.edu.service;

import com.atguigu.edu.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-07-21
 */
public interface VideoService extends IService<Video> {

    void saveVideo(Video video);
}
