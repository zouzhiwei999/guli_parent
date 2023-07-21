package com.atguigu.edu.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author AOA
 * @version 1.0
 * @description: 课程分级实体类:二级课程
 * @date 2023/7/20 20:53
 */
@Data
public class SubjectTwoVO {

    @ApiModelProperty(value = "课程类别ID")
    private String id;

    @ApiModelProperty(value = "类别名称")
    private String title;

}
