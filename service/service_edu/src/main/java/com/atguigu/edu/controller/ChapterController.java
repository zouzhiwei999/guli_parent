package com.atguigu.edu.controller;


import com.atguigu.commomutils.R;
import com.atguigu.edu.entity.Chapter;
import com.atguigu.edu.entity.vo.chapter.ChapterVo;
import com.atguigu.edu.service.ChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-07-21
 */
@RestController
@RequestMapping("/edu/chapter")
@CrossOrigin
@Api(tags = "Chapter模块")
public class ChapterController {

    @Autowired
    private ChapterService service;

    @ApiOperation("获取大章节以及对应的小章节")
    @GetMapping("/getChapterVoList/{id}")
    public R getChapterVoList(@PathVariable("id") String id) {
        List<ChapterVo> list = service.getChapterVoList(id);
        return R.ok().data("ChapterVoList", list);
    }

    @ApiOperation("保存大章节")
    @PostMapping()
    public R saveChapter(@RequestBody Chapter chapter){
        String chapterId = service.saveChapter(chapter);
        return R.ok().data("chapterId", chapterId);
    }

    @ApiOperation("修改大章节")
    @PutMapping()
    public R updateChapter(@RequestBody Chapter chapter){
        service.updateChapter(chapter);
        return R.ok();
    }


    @ApiOperation("获取大章节")
    @GetMapping("/getChapter/{id}")
    public R getChapter(@PathVariable("id")String id){
        Chapter chapter = service.getChapter(id);
        return R.ok().data("chapter",chapter);
    }

    @ApiOperation("删除大章节")
    @DeleteMapping("{id}")
    public R deleteChapter(@PathVariable("id")String id){
        service.deleteChapter(id);
        return R.ok();
    }

}

