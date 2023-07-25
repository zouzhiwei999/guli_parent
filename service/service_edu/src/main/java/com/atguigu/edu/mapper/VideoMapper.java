package com.atguigu.edu.mapper;

import com.atguigu.edu.entity.Chapter;
import com.atguigu.edu.entity.Video;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 课程视频 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2023-07-21
 */
@Mapper
public interface VideoMapper extends BaseMapper<Video> {

}
