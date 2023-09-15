package com.atguigu.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author AOA
 * @version 1.0
 * @description: TODO
 * @date 2023/7/17 22:02
 */
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.atguigu"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class OssApplication {
    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class, args);
    }
}
