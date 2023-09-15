package com.atguigu.edu.service.impl;

import com.atguigu.commomutils.R;
import com.atguigu.edu.client.VodClient;
import com.atguigu.edu.entity.Video;
import com.atguigu.edu.mapper.VideoMapper;
import com.atguigu.edu.service.VideoService;
import com.atguigu.servicebase.exception.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-07-21
 */
@Service
@Slf4j
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Autowired
    private VodClient vodClient;

    @Override
    public void saveVideo(Video video) {
        int insert = baseMapper.insert(video);
        if (insert <= 0){
            throw new GuliException(20001, "保存video失败");
        }
    }

    //删除小节，course_id
    @Override
    public void deleteVideo(String id) {
        log.info("要删除的小节的course_id = " + id);
        if (!StringUtils.isEmpty(id)) {
            //删除小节中的视频
            QueryWrapper<Video> wrapper = new QueryWrapper<>();
            wrapper.eq("course_id", id);

            List<Video> videos = baseMapper.selectList(wrapper);

            List<String> videosIdList = new ArrayList<>();

            for (Video video : videos) {
                String videoSourceId = video.getVideoSourceId();
                if (!StringUtils.isEmpty(videoSourceId)){
                    videosIdList.add(videoSourceId);
                }
            }

            if (videosIdList.size()>0){
                R r = vodClient.deleteBatch(videosIdList);
                if (r.getCode() == 20000){
                    throw new GuliException(20001, "删除视频失败，熔断器执行");
                }
            }

            int delete = baseMapper.delete(wrapper);
            if (delete<0){
                throw new GuliException(20001, "删除小节失败");
            }
        }
    }
}
