package com.atguigu.servicebase.exception;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author AOA
 * @version 1.0
 * @description: 自定义的异常，常用来测试自己的代码
 * @date 2023/7/2 20:36
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuliException extends RuntimeException {

    @ApiModelProperty(name = "错误码")
    private Integer code;

    @ApiModelProperty("错误信息")
    private String msg;
}
