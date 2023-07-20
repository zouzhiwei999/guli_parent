package com.atguigu.edu.controller;

import com.atguigu.commomutils.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

/**
 * @author AOA
 * @version 1.0
 * @description: TODO
 * @date 2023/7/9 20:50
 */
@Api(tags = "登录模块")
@RestController
@RequestMapping("/user")
@CrossOrigin
public class LoginController {

    @PostMapping("/login")
    public R login() {

        return R.ok().data("token", "123456");
    }

    @GetMapping("/info")
    public R info() {
        return R.ok().data("roles", "教师").data("name", "张三").data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
