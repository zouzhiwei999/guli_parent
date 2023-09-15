package com.atguigu.edu.controller.front;

import com.atguigu.commomutils.R;
import com.atguigu.edu.entity.Course;
import com.atguigu.edu.entity.frontVo.CourseFrontVo;
import com.atguigu.edu.entity.frontVo.CourseWebVo;
import com.atguigu.edu.entity.vo.chapter.ChapterVo;
import com.atguigu.edu.service.ChapterService;
import com.atguigu.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author AOA
 * @version 1.0
 * @description: TODO
 * @date 2023/9/10 19:14
 */
@RestController
@RequestMapping("/edu/coursefront")
@CrossOrigin
@Api(tags = "前台课程模块")
public class CourseFrontController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ChapterService chapterService;

    @ApiOperation("获取前台课程数据，按照条件以及分页 查询")
    @PostMapping("/getCourseFrontInfoConditionalAndPage/{page}/{size}")
    public R getCourseFrontInfoConditionalAndPage(@ApiParam("当前页") @PathVariable("page")long page,
                                                  @ApiParam("页容量")@PathVariable("size")long size,
                                                  @ApiParam("查询条件")@RequestBody CourseFrontVo courseFrontVo){
        Page<Course> coursePage = new Page<>(page, size);

        Map<String, Object> map = courseService.getCourseFrontInfoConditionalAndPage(coursePage, courseFrontVo);

        return R.ok().data("map", map);
    }

    @ApiOperation("根据courseId获取课程详情页信息")
    @GetMapping("/getCourseFrontInfo/{courseId}")
    public R getCourseFrontInfo(@PathVariable("courseId")String courseId){

        CourseWebVo courseWebVo = courseService.getCourseFrontInfo(courseId);

        List<ChapterVo> chapterVoList = chapterService.getChapterVoList(courseId);

        return R.ok().data("courseWebVo", courseWebVo).data("chapterVoList", chapterVoList);
    }

}
