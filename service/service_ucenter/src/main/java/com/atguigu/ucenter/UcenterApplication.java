package com.atguigu.ucenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author AOA
 * @version 1.0
 * @description: TODO
 * @date 2023/8/21 17:26
 */
@ComponentScan("com.atguigu")
@SpringBootApplication
public class UcenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UcenterApplication.class, args);
    }
}
