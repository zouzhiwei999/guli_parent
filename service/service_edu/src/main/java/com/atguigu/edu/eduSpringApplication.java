package com.atguigu.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author AOA
 * @version 1.0
 * @description: TODO
 * @date 2023/5/26 2:33
 */
@ComponentScan(basePackages = {"com.atguigu"})
@SpringBootApplication
public class eduSpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(eduSpringApplication.class, args);
    }
}

