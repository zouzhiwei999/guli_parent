package com.atguigu.statistics;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author AOA
 * @version 1.0
 * @description: TODO
 * @date 2023/9/24 16:53
 */
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan({"com.atguigu"})
@SpringBootApplication
public class StatisticsSpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(StatisticsSpringApplication.class, args);
    }
}
