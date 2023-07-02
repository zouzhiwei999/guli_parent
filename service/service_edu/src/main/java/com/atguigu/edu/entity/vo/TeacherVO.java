package com.atguigu.edu.entity.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author AOA
 * @version 1.0
 * @description: TODO
 * @date 2023/5/27 21:41
 */
@Data
public class TeacherVO {
    @ApiModelProperty(value = "讲师姓名模糊查询")
    private String name;

    @ApiModelProperty(value = "头衔 1高级讲师 2首席讲师")
    private Integer level;

    @ApiModelProperty(value = "查询创建时间", example = "2023-06-26 20:38:40")
    private Date begin;

    @ApiModelProperty(value = "查询结束时间", example = "2023-06-26 20:38:40")
    private Date end;
}
