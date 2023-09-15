package com.atguigu.edu.service.impl;

import com.atguigu.edu.entity.Course;
import com.atguigu.edu.entity.CourseDescription;
import com.atguigu.edu.entity.frontVo.CourseFrontVo;
import com.atguigu.edu.entity.frontVo.CourseWebVo;
import com.atguigu.edu.entity.vo.CourseInfoVo;
import com.atguigu.edu.entity.vo.CoursePublishVo;
import com.atguigu.edu.entity.vo.chapter.CourseVo;
import com.atguigu.edu.mapper.CourseDescriptionMapper;
import com.atguigu.edu.mapper.CourseMapper;
import com.atguigu.edu.service.ChapterService;
import com.atguigu.edu.service.CourseDescriptionService;
import com.atguigu.edu.service.CourseService;
import com.atguigu.edu.service.VideoService;
import com.atguigu.servicebase.exception.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-07-21
 */
@Service
@Transactional
@Slf4j
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseDescriptionMapper mapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private VideoService videoService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private CourseDescriptionService courseDescriptionService;

    //保存CourseInfoVo的信息到 Course 和 CourseDescription  一对一的关系 两者id相同
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        log.info("==================" + courseInfoVo.toString());

        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVo, course);
        log.info("==================" + course.toString());
        int insert = baseMapper.insert(course);
        log.info("==================" + insert);
        log.info("==================" + course.getId());

        if (insert <= 0) {
            throw new GuliException(20001, "添加新课程失败");
        }

        CourseDescription courseDescription = new CourseDescription();
        BeanUtils.copyProperties(courseInfoVo, courseDescription);
        courseDescription.setId(course.getId());
        int insert1 = mapper.insert(courseDescription);

        if (insert1 <= 0) {
            throw new GuliException(20001, "添加新课程简介失败");
        }
        return course.getId();
    }

    @Override
    public CourseInfoVo getCourseInfo(String id) {

        log.info("==========" + id);
        Course course = baseMapper.selectById(id);

        log.info("==========" + course);

        if (course == null){
            throw new GuliException(20001, "找不到该course信息");
        }

        CourseDescription courseDescription = mapper.selectById(id);

        if (course == null){
            throw new GuliException(20001, "找不到该courseDescription信息");
        }

        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(course,courseInfoVo);

        courseInfoVo.setDescription(courseDescription.getDescription());

        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVo, course);
        int i = baseMapper.updateById(course);

        if (i < 0) {
            throw new GuliException(20001, "修改course失败");
        }

        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setId(courseInfoVo.getId());
        courseDescription.setDescription(courseInfoVo.getDescription());

        int i1 = mapper.updateById(courseDescription);
        if (i1 < 0) {
            throw new GuliException(20001, "修改courseDescription失败");
        }

    }

    @Override
    public CoursePublishVo getCoursePublishVo(String id) {
        CoursePublishVo coursePublishVo = courseMapper.getCoursePublishVo(id);
        if (coursePublishVo == null){
            throw new GuliException(20001, "找不到coursePublishVo");
        }
        return coursePublishVo;
    }

    //删除Course ,先删除阿里云,再删video,再删chapter,再删course
    @Override
    @Transactional
    public void deleteCourse(String id) {
        if (!StringUtils.isEmpty(id)){
            //先删除阿里云,删除小节
            videoService.deleteVideo(id);
            //删除章节
            chapterService.deleteChapterByCourseId(id);
            //删除描述
            courseDescriptionService.deleteDescription(id);
            //删除课程
            int i = baseMapper.deleteById(id);
            if (i <= 0){
                throw new GuliException(20001, "删除course失败");
            }
        }
    }

    //获取首页的课程 4个
    @Cacheable(value = "course", key = "'getCourse'")
    @Override
    public List<Course> getIndex() {
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.orderByDesc("id");
        courseQueryWrapper.last("limit 8");

        List<Course> courses = baseMapper.selectList(courseQueryWrapper);
        return courses;
    }

    @Override
    public Course getCourseFrontInfoByTeacherId(String teacherId) {

        if (StringUtils.isEmpty(teacherId)){
            throw new GuliException(20001, "教师ID为空");
        }

        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", teacherId);
        Course course = baseMapper.selectOne(wrapper);

        return course;

    }

    //获取前台课程数据，按照条件以及分页 查询
    @Override
    public Map<String, Object> getCourseFrontInfoConditionalAndPage(Page<Course> coursePage, CourseFrontVo courseFrontVo) {

        QueryWrapper<Course> wrapper = new QueryWrapper<>();

        //一级目录
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())){
            wrapper.eq("subject_parent_id", courseFrontVo.getSubjectParentId());
        }
        //二级目录
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())){
            wrapper.eq("subject_id", courseFrontVo.getSubjectId());
        }
        //销量
        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())){
            wrapper.orderByDesc("buy_count");
        }
        //价格
        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())){
            wrapper.orderByDesc("price");
        }
        //日期
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())){
            wrapper.orderByDesc("gmt_create");

        }

        IPage<Course> courseIPage = baseMapper.selectPage(coursePage, wrapper);

        List<Course> records = coursePage.getRecords();
        long pages = coursePage.getPages();
        long current = coursePage.getCurrent();
        long size = coursePage.getSize();
        long total = coursePage.getTotal();
        boolean hasNext = coursePage.hasNext();
        boolean hasPrevious = coursePage.hasPrevious();

        Map<String, Object> map = new HashMap<>();
        map.put("records",records);
        map.put("current",current);
        map.put("pages",pages);
        map.put("total",total);
        map.put("size",size);
        map.put("hasNext",hasNext);
        map.put("hasPrevious",hasPrevious);

        return map;
    }

    //根据courseId查找课程详情页字段
    @Override
    public CourseWebVo getCourseFrontInfo(String courseId) {
        CourseWebVo courseWebVo = baseMapper.getCourseFrontInfo(courseId);

        if (courseWebVo == null){
            throw new GuliException(20001, "该课程详情页信息错误");
        }

        return courseWebVo;
    }


}
