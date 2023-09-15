package com.atguigu.edu.service;

import com.atguigu.edu.entity.Chapter;
import com.atguigu.edu.entity.vo.chapter.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-07-21
 */
public interface ChapterService extends IService<Chapter> {

    List<ChapterVo> getChapterVoList(String id);

    String saveChapter(Chapter chapter);

    void updateChapter(Chapter chapter);

    void deleteChapter(String id);

    Chapter getChapter(String id);

    void deleteChapterByCourseId(String id);
}
