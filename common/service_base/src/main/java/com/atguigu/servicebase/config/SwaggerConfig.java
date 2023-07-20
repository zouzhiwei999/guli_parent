package com.atguigu.servicebase.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * @author AOA
 * @version 1.0
 * @description: 开启swagger
 * @date 2023/5/26 3:41
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    public Contact DEFAULT_CONTACT = new Contact("邹智威", "https://gitee.com/zou_zhiwei", "1830560034@qq.com");

    @Bean
    public Docket docket() {

     return new Docket(DocumentationType.SWAGGER_2)
     .groupName("zzw")
     .enable(true)
     .apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.basePackage("com.atguigu")).build();
    }

    public ApiInfo apiInfo() {
        return new ApiInfo("zzw Documentation",
                "Api Documentation",
                "1.0",
                "https://gitee.com/zou_zhiwei",
                DEFAULT_CONTACT,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }
}
