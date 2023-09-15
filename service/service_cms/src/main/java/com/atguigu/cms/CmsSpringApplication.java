package com.atguigu.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author AOA
 * @version 1.0
 * @description: banner启动类
 * @date 2023/8/12 18:44
 */
@ComponentScan({"com.atguigu"})
@SpringBootApplication
public class CmsSpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsSpringApplication.class, args);
    }
}
