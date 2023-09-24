package com.atguigu.servicebase.exceptionhandler;


import com.atguigu.commomutils.R;
import com.atguigu.servicebase.exception.GuliException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author AOA
 * @version 1.0
 * @description: 统一异常处理
 * @date 2023/5/28 14:39
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //全局异常处理
    @ExceptionHandler(Exception.class)
    public R error(Exception e) {
        e.printStackTrace();
        return R.error().message("系统出现全局问题");
    }

    //特定异常处理
    @ExceptionHandler(ArithmeticException.class)
    public R error(ArithmeticException e) {
        e.printStackTrace();
        return R.error().message("执行了ArithmeticException异常处理");
    }

    //自定义异常处理
    @ExceptionHandler(GuliException.class)
    public R error(GuliException e) {
        log.error(e.getMsg());
//        e.printStackTrace();
        return R.error().message(e.getMsg()).code(e.getCode());
    }

}
