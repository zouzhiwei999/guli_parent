package com.atguigu.edu.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author AOA
 * @version 1.0
 * @description: 课程分级实体类:一级课程
 * @date 2023/7/20 20:53
 */
@Data
public class SubjectOneVO {

    @ApiModelProperty(value = "课程类别ID")
    private String id;

    @ApiModelProperty(value = "类别名称")
    private String title;

    @ApiModelProperty(value = "二级课程集合")
    private List<SubjectTwoVO> children;
}
