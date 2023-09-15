package com.atguigu.edu.entity.vo.chapter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author AOA
 * @version 1.0
 * @description: TODO
 * @date 2023/7/27 13:06
 */
@Data
public class CourseVo {
    @ApiModelProperty(value = "课程标题")
    private String title;
    @ApiModelProperty(value = "课程状态 Draft未发布  Normal已发布")
    private String status;
}
