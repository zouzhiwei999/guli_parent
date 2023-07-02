package com.atguigu.servicebase.exceptionhandler;


import com.atguigu.commomutils.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author AOA
 * @version 1.0
 * @description: TODO
 * @date 2023/5/28 14:39
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public R error(Exception e) {
        e.printStackTrace();
        return R.error().message("系统出现全局问题");
    }
}
