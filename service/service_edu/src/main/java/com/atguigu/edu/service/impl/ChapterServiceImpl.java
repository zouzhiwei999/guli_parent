package com.atguigu.edu.service.impl;

import com.atguigu.edu.client.VodClient;
import com.atguigu.edu.entity.Chapter;
import com.atguigu.edu.entity.Video;
import com.atguigu.edu.entity.vo.chapter.ChapterVo;
import com.atguigu.edu.entity.vo.chapter.VideoVo;
import com.atguigu.edu.mapper.ChapterMapper;
import com.atguigu.edu.mapper.VideoMapper;
import com.atguigu.edu.service.ChapterService;
import com.atguigu.servicebase.exception.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-07-21
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
@Slf4j
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private VideoMapper mapper;

    @Autowired
    private VodClient vodClient;

    //获取List<ChapterVo>,章节和小节
    @Override
    public List<ChapterVo> getChapterVoList(String id) {

        //1.获取全部List<Chapter>
        QueryWrapper<Chapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", id);
        List<Chapter> chapters = baseMapper.selectList(wrapper);

        //2.获取全部List<Video>
        QueryWrapper<Video> wrapper1 = new QueryWrapper<>();
        wrapper.eq("course_id", id);
        List<Video> videos = mapper.selectList(wrapper1);

        //3.获取指定courseId的List<ChapterVo>
        List<ChapterVo> chapterVos = new ArrayList<>();
        for (Chapter chapter : chapters) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);
            chapterVos.add(chapterVo);

            //4.获取对应ChapterId的List<VideoVo>
            List<VideoVo> videoVos = new ArrayList<>();
            for (Video video : videos) {
                VideoVo videoVo = new VideoVo();
                if (video.getChapterId().equals(chapter.getId())){
                    BeanUtils.copyProperties(video, videoVo);
                    videoVos.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVos);
        }

        //5.返回指定courseId的List<ChapterVo>
        return chapterVos;
    }

    //保存
    @Override
    public String saveChapter(Chapter chapter) {
        int insert = baseMapper.insert(chapter);
        if (insert <= 0){
            throw new GuliException(20001, "保存chapter失败");
        }
        return chapter.getId();
    }

    //修改
    @Override
    public void updateChapter(Chapter chapter) {
        int i = baseMapper.updateById(chapter);
        if (i <= 0){
            throw new GuliException(20001, "修改chapter失败");
        }
    }

    //删除chapter ,video ,阿里云视频 接收chapter的ID
    @Override
    public void deleteChapter(String id) {

        log.info("Chapter的ID:" +  id);
        //1.删除阿里云的视频
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("chapter_id", id);
        List<Video> videos = mapper.selectList(videoQueryWrapper);

        List<String> videoSourceIdList = new ArrayList<>();
        //一个chappter有多个阿里云视频
        for (Video video : videos) {
            log.info("video:" +  video);

            String videoSourceId = video.getVideoSourceId();
            if (!StringUtils.isEmpty(videoSourceId)){
                videoSourceIdList.add(videoSourceId);
            }
        }

        if (videoSourceIdList.size()>0){
            vodClient.deleteBatch(videoSourceIdList);
        }

        //2.删除小节

        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", id);
        Integer integer = mapper.selectCount(wrapper);
        if (integer >0){

            int delete = mapper.delete(wrapper);
            if (delete <= 0){
                throw new GuliException(20001, "删除chapter中的video失败");
            }
        }


        //3.删除chapter
        QueryWrapper<Chapter> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("id", id);
        int delete1 = baseMapper.delete(wrapper1);
        if (delete1 < 0){
            throw new GuliException(20001, "删除chapter失败");
        }

    }

    @Override
    public Chapter getChapter(String id) {
        Chapter chapter = baseMapper.selectById(id);
        return chapter;
    }

    @Override
    public void deleteChapterByCourseId(String id) {
        if (!StringUtils.isEmpty(id)){
            QueryWrapper<Chapter> wrapper = new QueryWrapper<>();
            wrapper.eq("course_id", id);
            int delete = baseMapper.delete(wrapper);
        }
    }
}
