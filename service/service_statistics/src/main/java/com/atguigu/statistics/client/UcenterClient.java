package com.atguigu.statistics.client;

import com.atguigu.commomutils.R;
import com.atguigu.statistics.client.impl.UcenterClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author AOA
 * @version 1.0
 * @description: TODO
 * @date 2023/9/24 17:22
 */
@FeignClient(value = "service-ucenter",fallback = UcenterClientImpl.class)
@Component
public interface UcenterClient {

    @GetMapping("/educenter/member/countRegister/{day}")
    public R countRegister(@PathVariable("day") String day);
}
