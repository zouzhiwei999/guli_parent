package com.atguigu.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


/**
 * @author AOA
 * @version 1.0
 * @description: TODO
 * @date 2023/5/26 2:33
 */
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan({"com.atguigu"})
@SpringBootApplication
public class EduSpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduSpringApplication.class, args);
    }
}

