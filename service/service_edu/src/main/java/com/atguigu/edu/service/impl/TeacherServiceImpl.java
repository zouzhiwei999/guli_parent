package com.atguigu.edu.service.impl;

import com.atguigu.edu.entity.Course;
import com.atguigu.edu.entity.Teacher;
import com.atguigu.edu.mapper.TeacherMapper;
import com.atguigu.edu.service.CourseService;
import com.atguigu.edu.service.TeacherService;
import com.atguigu.servicebase.exception.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-05-25
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    //获取首页的教师 4 个
    @Cacheable(value = "teacher", key = "'getTeacher'")
    @Override
    public List<Teacher> getIndex() {
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.orderByDesc("id");
        teacherQueryWrapper.last("limit 4");
        List<Teacher> teachers = baseMapper.selectList(teacherQueryWrapper);
        return teachers;
    }

    //前台教师课程信息
    @Override
    public Map<String, Object> getTeacherFrontList(Page<Teacher> teacherPage) {

        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("id");

        IPage<Teacher> teacherIPage = baseMapper.selectPage(teacherPage, wrapper);

        if (teacherIPage == null){
            throw new GuliException(20001, "查询教师列表失败");
        }

        //数据
        List<Teacher> records = teacherPage.getRecords();
        //当前页
        long current = teacherPage.getCurrent();
        //总页数
        long pages = teacherPage.getPages();
        //总记录数
        long total = teacherPage.getTotal();
        //页容量
        long size = teacherPage.getSize();

        //是否有下一页
        boolean hasNext = teacherPage.hasNext();
        //是否有上一页
        boolean hasPrevious = teacherPage.hasPrevious();

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





}
