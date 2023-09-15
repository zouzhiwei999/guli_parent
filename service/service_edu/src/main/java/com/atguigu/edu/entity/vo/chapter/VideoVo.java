package com.atguigu.edu.entity.vo.chapter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author AOA
 * @version 1.0
 * @description: 小章节目录
 * @date 2023/7/23 23:05
 */
@Data
public class VideoVo {
    private String id;

    private String title;

    @ApiModelProperty(value = "云端视频资源")
    private String videoSourceId;
}
