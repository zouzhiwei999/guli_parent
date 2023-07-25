package com.atguigu.edu.service.impl;

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

    //获取List<ChapterVo>
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

    @Override
    public void deleteChapter(String id) {

        log.info("===========" + id);
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", id);
        Integer integer = mapper.selectCount(wrapper);
        if (integer >0){

            int delete = mapper.delete(wrapper);
            if (delete <= 0){
                throw new GuliException(20001, "删除chapter中的video失败");
            }
        }


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
}
