package com.atguigu.edu.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author AOA
 * @version 1.0
 * @description: mybatisplus  3以下低版本
 * @date 2023/5/26 2:35
 */
@Configuration
@MapperScan({"com.atguigu.edu.mapper"})
public class EduConfig {
    //逻辑删除
    @Bean
    public ISqlInjector injector() {
        return new LogicSqlInjector();
    }

    //3.0.0以下，低版本：mybatisplus分页查询功能
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}
