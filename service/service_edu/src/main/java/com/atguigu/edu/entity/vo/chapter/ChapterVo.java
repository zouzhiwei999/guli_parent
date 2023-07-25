package com.atguigu.edu.entity.vo.chapter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author AOA
 * @version 1.0
 * @description: 大章节目录
 * @date 2023/7/23 23:05
 */
@Data
@ApiModel
public class ChapterVo {
    @ApiModelProperty("id")
    private String id;

    private String title;
    private List<VideoVo> children;
}
